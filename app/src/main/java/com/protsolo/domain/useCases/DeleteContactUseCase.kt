package com.protsolo.domain.useCases

import com.protsolo.data.IRepository

class DeleteContactUseCase(private val repository: IRepository) {

    fun deleteContact(contactId: Int) =
        repository.addContact(contactId)
}