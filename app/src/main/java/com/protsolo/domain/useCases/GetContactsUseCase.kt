package com.protsolo.domain.useCases

import com.protsolo.data.IRepository

class GetContactsUseCase(private val repository: IRepository) {
    suspend fun getContacts() =
        repository.getContacts()
}