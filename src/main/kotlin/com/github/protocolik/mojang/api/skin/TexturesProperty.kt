package com.github.protocolik.mojang.api.skin

import java.util.*

data class TexturesProperty(
        val id: UUID,
        val name: String,
        val properties: Set<SkinProperty>
)