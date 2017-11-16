package no.tornado.keyboard.compiler.service

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
open class CompilerExceptionMapper : ExceptionMapper<CompilerException> {
    override fun toResponse(exception: CompilerException) = Response.serverError()
            .header("Content-Type", "application/json")
            .entity(exception.toJSON()).build()
}