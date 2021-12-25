package com.protsolo.domain.useCases

import com.protsolo.data.IRepository

class GetContactsUseCase(private val repository: IRepository) {
    fun getContacts() =
        repository.getContacts()
}