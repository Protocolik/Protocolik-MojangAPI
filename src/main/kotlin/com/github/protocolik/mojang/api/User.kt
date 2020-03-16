package com.github.protocolik.mojang.api

import com.github.protocolik.mojang.api.skin.SkinProperty

data class User(
        val profile: Profile,
        val nameHistory: List<NameHistoryEntry>,
        val skinProperty: SkinProperty
)