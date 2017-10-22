package no.tornado.keyboard.compiler.service

import no.tornado.keyboard.models.Key
import no.tornado.keyboard.models.KeyCode
import no.tornado.keyboard.models.Keymap
import no.tornado.keyboard.models.Layer

class RenderingContext(val keymap: Keymap) {
    private val fnActions = mutableListOf<Key>()

    private fun render(layer: Layer): String {
        val s = StringBuilder("")
        s.append("\t/* ${keymap.layers.indexOf(layer)}: ${layer.name} */\n")
        s.append("\tKEYMAP_TORNADO(")
        s.append(layer.keys.joinToString(", ", transform = this::render) + "")
        s.append(")")
        return s.toString()
    }

    private fun render(key: Key): String {
        val s = StringBuilder()
        val fnActionIndex = fnActions.indexOf(key)
        if (fnActionIndex > -1) {
            s.append("F($fnActionIndex)")
        } else {
            if (key.modifiers?.isNotEmpty() == true) {
                for (mod in key.modifiers)
                    s.append("${mod.name}(")
                s.append(key.code)
                s.append(key.modifiers.joinToString("") { ")" })
            } else {
                s.append(key.code ?: KeyCode.KC_TRNS)
            }
        }
        return s.toString()
    }

    fun render(): String {
        fnActions.addAll(keymap.layers.flatMap { it.keys }.filter { it.layerToggleType != null && it.layer != null })

        val s = StringBuilder()

        s.append("const uint16_t PROGMEM keymaps[][MATRIX_ROWS][MATRIX_COLS] = {\n")

        s.append(keymap.layers.joinToString(",\n\n", transform = this::render))

        s.append("\n};\n\n")

        s.append("const uint16_t PROGMEM fn_actions[] = {\n")

        fnActions.forEach { key ->
            s.append("\t[${fnActions.indexOf(key)}] = ${key.layerToggleType?.actionName}(${key.layer}),")
        }

        s.append("\n};\n")
        return s.toString()
    }

}
