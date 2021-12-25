package com.protsolo.domain.useCases

import com.protsolo.data.IRepository

class AddContactUseCase(private val repository: IRepository) {
    fun addContact(contactId: Int) =
        repository.addContact(contactId)
}