package com.protsolo.domain.useCases

import com.protsolo.data.IRepository
import com.protsolo.data.remote.requests.LoginRequest

class LoginUseCase(private val repository: IRepository) {
    fun login(request: LoginRequest) =
        repository.login(request)
}