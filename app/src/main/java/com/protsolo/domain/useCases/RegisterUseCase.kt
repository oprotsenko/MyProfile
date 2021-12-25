package com.protsolo.domain.useCases

import com.protsolo.data.IRepository
import com.protsolo.data.remote.requests.RegisterUserRequest

class RegisterUseCase(private val repository: IRepository) {
    fun register(request: RegisterUserRequest) =
        repository.register(request)
}