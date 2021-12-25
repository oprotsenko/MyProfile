package com.protsolo.domain.useCases

import com.protsolo.data.IRepository
import com.protsolo.data.remote.requests.EditProfileRequest

class EditProfileUseCase(private val repository: IRepository) {
    fun editProfile(request: EditProfileRequest) =
        repository.editProfile(request)
}