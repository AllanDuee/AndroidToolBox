package fr.isen.duee.androidtoolbox.security

import android.content.SharedPreferences
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class EncryptData {
    val CRYPTO_METHOD = "RSA"
    val CRYPTO_BITS = 2048
    val CRYPTO_TRANSFORM = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

    // Generates the key pair
    fun generateKeyPair(sharedPreference: SharedPreferences) {
        val kp: KeyPair
        val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(CRYPTO_METHOD)

        kpg.initialize(CRYPTO_BITS)
        kp = kpg.genKeyPair()

        val editor =  sharedPreference.edit()

        editor.putString("publicKey",kp.public.key())
        editor.putString("privateKey",kp.private.key())
        editor.apply()
        //editor.commit()
    }

    fun PublicKey.key() = android.util.Base64.encodeToString(this.encoded, android.util.Base64.DEFAULT)!!
    fun PrivateKey.key() = android.util.Base64.encodeToString(this.encoded, android.util.Base64.DEFAULT)!!

    // Converts a string to a PublicKey object
    fun String.toPublicKey(): PublicKey {
        val keyBytes: ByteArray = android.util.Base64.decode(this, android.util.Base64.DEFAULT)
        val spec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(CRYPTO_METHOD)

        return keyFactory.generatePublic(spec)
    }

    // Converts a string to a PrivateKey object
    fun String.toPrivateKey(): PrivateKey {
        val keyBytes: ByteArray = android.util.Base64.decode(this, android.util.Base64.DEFAULT)
        val spec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(CRYPTO_METHOD)

        return keyFactory.generatePrivate(spec)
    }

    // Encrypts a string
    fun encrypt(message: String, sharedPreference: SharedPreferences): String {
        val encryptedBytes: ByteArray
        val pubKey: PublicKey? = sharedPreference.getString("publicKey","")?.toPublicKey()
        val cipher: Cipher = Cipher.getInstance(CRYPTO_TRANSFORM)

        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        encryptedBytes = cipher.doFinal(message.toByteArray(StandardCharsets.UTF_8))

        return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT)
    }

    // Decrypts a message
    fun decrypt(message: String, sharedPreference: SharedPreferences): String {
        val decryptedBytes: ByteArray
        val key: PrivateKey? = sharedPreference.getString("privateKey","")?.toPrivateKey()
        val cipher: Cipher = Cipher.getInstance(CRYPTO_TRANSFORM)

        cipher.init(Cipher.DECRYPT_MODE, key)
        decryptedBytes = cipher.doFinal(android.util.Base64.decode(message, android.util.Base64.DEFAULT))

        return String(decryptedBytes)
    }
}