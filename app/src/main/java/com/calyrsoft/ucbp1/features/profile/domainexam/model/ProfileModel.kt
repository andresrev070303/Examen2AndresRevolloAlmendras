package com.calyrsoft.ucbp1.features.profile.domain.model

/**
 * Value Objects (VO) para asegurar inmutabilidad y validaciones
 * de los campos de perfil del usuario.
 */

// Nombre de usuario
@JvmInline
value class UserName private constructor(val value: String) {
    companion object {
        fun of(raw: String): Result<UserName> =
            if (raw.trim().length in 3..40) Result.success(UserName(raw.trim()))
            else Result.failure(IllegalArgumentException("El nombre debe tener entre 3 y 40 caracteres"))
    }
}

// Email
@JvmInline
value class Email private constructor(val value: String) {
    companion object {
        private val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        fun of(raw: String): Result<Email> =
            if (regex.matches(raw)) Result.success(Email(raw))
            else Result.failure(IllegalArgumentException("Email inválido"))
    }
}

// Teléfono (opcional)
@JvmInline
value class Phone private constructor(val value: String) {
    companion object {
        private val regex = Regex("^\\+?[0-9]{7,15}$")
        fun of(raw: String): Result<Phone> =
            if (regex.matches(raw)) Result.success(Phone(raw))
            else Result.failure(IllegalArgumentException("Número de teléfono inválido"))
    }
}

// URL de imagen/avatar (opcional)
@JvmInline
value class ImageUrl private constructor(val value: String) {
    companion object {
        private val regex = Regex("^https?://.+")
        fun of(raw: String): Result<ImageUrl> =
            if (regex.matches(raw)) Result.success(ImageUrl(raw))
            else Result.failure(IllegalArgumentException("URL inválida"))
    }
}

/**
 * ProfileModel: entidad de dominio que representa el perfil del usuario.
 * Sus campos son Value Objects que garantizan datos válidos.
 */
data class ProfileModel(
    val id: Long,
    val name: UserName,
    val email: Email,
    val phone: Phone?,
    val avatar: ImageUrl?
)
