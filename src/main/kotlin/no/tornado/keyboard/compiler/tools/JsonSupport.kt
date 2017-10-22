package no.tornado.keyboard.compiler.tools

import no.tornado.keyboard.models.KeyCode
import no.tornado.keyboard.models.LayerToggleType
import javax.json.JsonObject

inline fun JsonObject.array(key: String) = if (containsKey(key)) getJsonArray(key) else null
inline fun JsonObject.int(key: String) = if (containsKey(key)) getInt(key) else null
inline fun JsonObject.string(key: String) = if (containsKey(key)) getInt(key) else null
inline fun JsonObject.keycode(key: String) = if (containsKey(key)) KeyCode.valueOf(getString(key)) else null
inline fun JsonObject.layertoggletype(key: String) = if (containsKey(key)) LayerToggleType.valueOf(getString(key)) else null
