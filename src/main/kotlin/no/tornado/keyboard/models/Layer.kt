package no.tornado.keyboard.models

import no.tornado.keyboard.compiler.tools.array
import javax.json.JsonObject

enum class LayerToggleType(val actionName: String) {
    Toggle("ACTION_LAYER_TOGGLE"),
    Momentary("ACTION_LAYER_MOMENTARY")
}

data class Layer(val name: String, val keys: List<Key>) {
    constructor(json: JsonObject) : this (
            json.getString("name"),
            json.array("keys")?.map { Key(it as JsonObject) } ?: ArrayList()
    )
}