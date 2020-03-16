package com.github.protocolik.mojang.api.auth

import com.github.protocolik.mojang.api.Profile
import java.util.*

data class AuthResponse(
        val accessToken: UUID,
        val selectedProfile: Profile
)