package no.tornado.keyboard.compiler.service

import javax.json.Json

class CompilerException(val retval: Int, val stdout: String, val stderr: String) : Exception() {
    fun toJSON() = Json.createObjectBuilder()
            .add("retval", retval)
            .add("stdout", stdout)
            .add("stderr", stderr)
            .build()
}

