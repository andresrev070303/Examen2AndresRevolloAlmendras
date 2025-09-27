package com.calyrsoft.ucbp1.features.login.domain.usecase

import com.calyrsoft.ucbp1.features.login.domain.model.LoginUserModel
import com.calyrsoft.ucbp1.features.login.domain.repository.ILoginRepository

class FindByNameAndPasswordUseCase(
    val repository: ILoginRepository,
) {
    fun invoke(name: String, password: String): Result<LoginUserModel> {
        return repository.findByNameAndPassword(name, password)
    }
}
