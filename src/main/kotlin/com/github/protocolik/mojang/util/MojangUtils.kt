package com.github.protocolik.mojang.util

import java.util.*
import java.util.regex.Pattern

object MojangUtils {
    private val UUID_PATTERN = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})")
    private val NAME_PATTERN = Pattern.compile("^\\w{2,16}$")

    @JvmStatic
    fun fromMojang(withoutDashes: String): UUID =
            UUID.fromString(UUID_PATTERN.matcher(withoutDashes).replaceAll("$1-$2-$3-$4-$5"))

    @JvmStatic
    fun toMojang(uniqueId: UUID): String =
            uniqueId.toString().replace("-", "")

    @JvmStatic
    fun getOfflineId(playerName: String): UUID =
            UUID.nameUUIDFromBytes("OfflinePlayer:$playerName".toByteArray())

    @JvmStatic
    fun isValidName(playerName: String): Boolean =
            NAME_PATTERN.matcher(playerName).matches()
}