package com.github.protocolik.mojang

import com.github.protocolik.mojang.api.NameHistoryEntry
import com.github.protocolik.mojang.api.Profile
import com.github.protocolik.mojang.api.User
import com.github.protocolik.mojang.api.skin.SkinProperty
import com.google.common.cache.CacheBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Suppress("DEPRECATION")
object Mojang {
    val uuidCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build<String, UUID>()
    val userCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(60)
            .build<UUID, User>()
    val textureCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(25)
            .build<URL, ByteArray>()

    suspend fun getUser(uuid: UUID): User? = suspendCoroutine {
        val user = userCache.getIfPresent(uuid)

        if (user != null) {
            it.resume(user)
        } else {
            it.resume(loadUser(uuid.toString()))
        }
    }

    suspend fun getUser(name: String): User? = suspendCoroutine {
        val uuid = uuidCache.getIfPresent(name)

        if (uuid != null) {
            runBlocking {
                it.resume(getUser(uuid))
            }
        } else {
            it.resume(loadUser(name))
        }
    }

    private fun loadUser(value: String): User? {
        val response = URL("https://api.ashcon.app/mojang/v2/user/$value").readText()
        val json = JsonParser().parse(response).asJsonObject

        if (json.has("error")) {
            return null
        } else {
            val profile = Profile(UUID.fromString(json.get("uuid").asString), json.get("username").asString)
            val nameHistory = json.getAsJsonArray("username_history").map { entry ->
                entry as JsonObject
                val username = entry.get("username").asString
                val time = if (entry.has("changed_at")) {
                    Instant.parse(entry.get("changed_at").asString)
                } else null

                NameHistoryEntry(username, time)
            }
            val texturesJson = json.getAsJsonObject("textures")
            val texturesRaw = texturesJson.getAsJsonObject("raw")
            val skinProperty = SkinProperty(
                    texturesRaw.get("value").asString,
                    texturesRaw.get("signature").asString
            )
            val user = User(profile, nameHistory, skinProperty)
            val skinJson = texturesJson.getAsJsonObject("skin")
            val skinUrl = URL(skinJson.get("url").asString.replace("http://", "https://"))
            val skinData = Base64.getDecoder().decode(skinJson.get("data").asString)

            textureCache.put(skinUrl, skinData)
            uuidCache.put(profile.name.toLowerCase(), profile.id)
            userCache.put(profile.id, user)

            return user
        }
    }

    suspend fun downloadTexture(url: URL): ByteArray = suspendCoroutine {
        textureCache.get(url) {
            url.readBytes()
        }
    }
}