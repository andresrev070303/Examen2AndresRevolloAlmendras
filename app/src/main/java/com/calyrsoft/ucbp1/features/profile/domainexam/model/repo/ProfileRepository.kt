package com.calyrsoft.ucbp1.features.profile.domain.repo

data class ProfileDto(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String?,
    val avatar: String?
)

interface ProfileRepository {
    suspend fun getProfile(userId: Long): ProfileDto
}
