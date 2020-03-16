package com.github.protocolik.mojang.api.skin

import com.github.protocolik.mojang.util.gson
import java.time.Instant
import java.util.*

data class Skin(
        val timestamp: Instant,
        val profileId: UUID,
        val profileName: String,
        val signatureRequired: Boolean = true,
        val skinTexture: Texture? = null,
        val capeTexture: Texture? = null,
        var signature: ByteArray = byteArrayOf()
) {
    fun encodeSkin(): SkinProperty {
        val json = gson.toJson(this)
        val encodedValue = Base64.getEncoder().encodeToString(json.toByteArray(Charsets.UTF_8))
        val encodedSignature = Base64.getEncoder().encodeToString(signature)
        return SkinProperty(encodedValue, encodedSignature)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Skin

        if (timestamp != other.timestamp) return false
        if (profileId != other.profileId) return false
        if (profileName != other.profileName) return false
        if (signatureRequired != other.signatureRequired) return false
        if (skinTexture != other.skinTexture) return false
        if (capeTexture != other.capeTexture) return false
        if (!signature.contentEquals(other.signature)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = timestamp.hashCode()
        result = 31 * result + profileId.hashCode()
        result = 31 * result + profileName.hashCode()
        result = 31 * result + signatureRequired.hashCode()
        result = 31 * result + (skinTexture?.hashCode() ?: 0)
        result = 31 * result + (capeTexture?.hashCode() ?: 0)
        result = 31 * result + signature.contentHashCode()
        return result
    }
}