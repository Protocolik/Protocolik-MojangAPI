package com.github.protocolik.mojang.api.skin

import java.net.URL

data class Texture(
        val type: Type = Type.SKIN,
        val skinModel: Model = Model.SQUARE,
        val url: URL
) {
    val metadata: Map<String, Model>? = if (skinModel == Model.SLIM) mapOf("model" to Model.SLIM) else null

    enum class Type {
        SKIN,
        CAPE,
        ELYTRA
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Texture

        if (type != other.type) return false
        if (skinModel != other.skinModel) return false
        if (url != other.url) return false
        if (metadata != other.metadata) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + skinModel.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + (metadata?.hashCode() ?: 0)
        return result
    }

    companion object {
        private const val URL_PREFIX = "http://textures.minecraft.net/texture/"
    }
}