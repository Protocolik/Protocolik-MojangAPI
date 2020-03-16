package com.github.protocolik.mojang.api

import java.time.Instant

data class NameHistoryEntry(
        val name: String,
        val changeToAt: Instant? = null
)