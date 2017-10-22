package no.tornado.keyboard.models

import javax.json.JsonObject

data class Keymap(val name: String, val layers: List<Layer>) {
    constructor(json: JsonObject) : this(
            json.getString("name"),
            json.getJsonArray("layers").map { Layer(it as JsonObject) }
    )
}