package fr.isen.duee.androidtoolbox

class User {
    var lastName: String = ""
    var firstName: String = ""
    var dateOfBirth: String = ""
    var number: String = ""
    var email: String = ""

    constructor(lastName: String, firstName: String, dateOfBirth: String) {
        this.lastName = lastName
        this.firstName = firstName
        this.dateOfBirth = dateOfBirth
    }

    constructor(lastName: String, firstName: String, dateOfBirth: String, number: String,  email: String) {
        this.lastName = lastName
        this.firstName = firstName
        this.dateOfBirth = dateOfBirth
        this.number = number
        this.email = email
    }
}