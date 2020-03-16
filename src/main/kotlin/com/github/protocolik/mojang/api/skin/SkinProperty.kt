package com.github.protocolik.mojang.api.skin

import com.github.protocolik.mojang.util.gson
import java.security.PublicKey
import java.security.Signature
import java.util.*

data class SkinProperty(
        val value: String,
        val signature: String
) {
    fun isValid(publicKey: PublicKey): Boolean {
        SIGNATURE.initVerify(publicKey)
        SIGNATURE.update(value.toByteArray())

        val decodedSignature = Base64.getDecoder().decode(signature)
        return SIGNATURE.verify(decodedSignature)
    }

    fun decodeSkin(): Skin {
        val data = Base64.getDecoder().decode(value)
        val json = data.toString(Charsets.UTF_8)
        val skinModel = gson.fromJson(json, Skin::class.java)
        skinModel.signature = Base64.getDecoder().decode(signature)
        return skinModel
    }

    companion object {
        private val SIGNATURE = Signature.getInstance("SHA1withRSA")
    }
}