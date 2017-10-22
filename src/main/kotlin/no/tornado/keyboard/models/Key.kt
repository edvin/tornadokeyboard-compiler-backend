package no.tornado.keyboard.models

import no.tornado.keyboard.compiler.tools.array
import no.tornado.keyboard.compiler.tools.int
import no.tornado.keyboard.compiler.tools.keycode
import no.tornado.keyboard.compiler.tools.layertoggletype
import javax.json.JsonObject
import javax.json.JsonString

data class Key(
        val code: KeyCode? = null,
        val layer: Int? = null,
        val layerToggleType: LayerToggleType? = null,
        val modifiers: List<KeyCode>? = null

) {
    constructor(json: JsonObject) : this(
            json.keycode("code"),
            json.int("layer"),
            json.layertoggletype("layertoggletype"),
            json.array("modifiers")
                    ?.map { it as JsonString }
                    ?.map { KeyCode.valueOf(it.string) }
    )
}