package com.protsolo.domain.useCases

import com.protsolo.data.IRepository

class GetProfileUseCase(private val repository: IRepository) {

    fun getProfile() = repository.getProfile()

    fun refreshToken(refreshToken: String) =
        repository.refreshToken(refreshToken)
}