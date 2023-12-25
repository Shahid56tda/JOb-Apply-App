package com.example.applyononeclickjob

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class RegistrationData(
    val fullName: String = "",
    val email: String = "",
    val dob: String = "",
    val mobile: String = "",
    val jobTitle: String = "",
    val summary: String = "",
    val location: String = "",
    val salary: String = "",
    val education: String = "",
    val yearOfExperience: String = "",
    val gender: String = "",
    val jobType: String = "",
    var imageUrl: String = "",
    var skill: String = ""// New field for image URL
)