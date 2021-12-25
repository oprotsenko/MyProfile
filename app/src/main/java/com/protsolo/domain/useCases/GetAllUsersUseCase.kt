package com.protsolo.domain.useCases

import com.protsolo.data.IRepository

class GetAllUsersUseCase(private val repository: IRepository) {
    fun getAllUsers() = repository.getAllUsers()
}