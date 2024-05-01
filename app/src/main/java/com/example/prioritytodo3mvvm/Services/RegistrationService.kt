package com.example.prioritytodo3mvvm.Services

import com.example.prioritytodo3mvvm.Models.RegisterModel

interface RegistrationService {

    fun registerUser(user: RegisterModel): Boolean
}

class RegistrationServiceImpl:RegistrationService {
    override fun registerUser(user: RegisterModel): Boolean {
        return true
    }
}