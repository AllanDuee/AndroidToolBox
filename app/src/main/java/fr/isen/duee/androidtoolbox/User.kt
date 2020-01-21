package fr.isen.duee.androidtoolbox

class User {
    var lastName: String = ""
    var firstName: String = ""
    var dateOfBirth: String = ""

    constructor(lastName: String, firstName: String, dateOfBirth: String) {
        this.lastName = lastName
        this.firstName = firstName
        this.dateOfBirth = dateOfBirth
    }
}