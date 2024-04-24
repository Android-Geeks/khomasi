package com.company.khomasi.utils

object CheckInputValidation {
    fun isFirstNameValid(firstName: String) =
        firstName.length in 2..20 && !firstName.any { it.isDigit() }

    fun isLastNameValid(lastName: String) =
        lastName.length in 2..20 && !lastName.any { it.isDigit() }

    fun isPhoneNumberValid(phoneNumber: String) =
        phoneNumber.matches(Regex("^(010|011|012|015)\\d{8}\$"))

    fun isEmailValid(email: String) =
        email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

    fun isPasswordValid(password: String) =
        password.matches(Regex("^(?=.*[0-9])(?=.*[!@#$%^&*_])(?=\\S+\$).{8,}\$"))
}