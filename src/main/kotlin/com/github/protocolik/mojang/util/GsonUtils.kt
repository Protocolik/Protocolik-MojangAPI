@file:JvmName("GsonUtils")

package com.github.protocolik.mojang.util

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.Instant
import java.util.*

val gson = GsonBuilder()
        .registerTypeAdapter(UUID::class.java, UUIDAdapter)
        .registerTypeAdapter(Instant::class.java, InstantAdapter)
        .create()

internal object UUIDAdapter : TypeAdapter<UUID>() {
    override fun write(output: JsonWriter, value: UUID) {
        output.value(MojangUtils.toMojang(value))
    }

    override fun read(input: JsonReader): UUID {
        return MojangUtils.fromMojang(input.nextString())
    }
}

internal object InstantAdapter : TypeAdapter<Instant>() {
    override fun write(output: JsonWriter, value: Instant) {
        output.value(value.toEpochMilli())
    }

    override fun read(input: JsonReader): Instant {
        return Instant.ofEpochMilli(input.nextLong())
    }
}