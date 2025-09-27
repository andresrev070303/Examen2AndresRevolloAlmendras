package com.calyrsoft.ucbp1.features.profile.domain.usecase

import com.calyrsoft.ucbp1.features.profile.domain.model.*
import com.calyrsoft.ucbp1.features.profile.domain.repo.ProfileRepository

/**
 * Mapea el DTO a ProfileModel pasando por los Value Objects (validaciones).
 */
class GetProfileUseCase(private val repo: ProfileRepository) {
    suspend operator fun invoke(userId: Long): Result<ProfileModel> = runCatching {
        val dto = repo.getProfile(userId)
        val name = UserName.of(dto.name).getOrThrow()
        val email = Email.of(dto.email).getOrThrow()
        val phone = dto.phone?.let { Phone.of(it).getOrThrow() }
        val avatar = dto.avatar?.let { ImageUrl.of(it).getOrThrow() }
        ProfileModel(dto.id, name, email, phone, avatar)
    }
}
