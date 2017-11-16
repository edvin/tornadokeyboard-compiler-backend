package no.tornado.keyboard.compiler.backend.api

import no.tornado.keyboard.compiler.service.CompilerException
import no.tornado.keyboard.compiler.service.KeyboardCompiler
import no.tornado.keyboard.models.Keymap
import javax.ejb.EJB
import javax.json.JsonObject
import javax.ws.rs.ApplicationPath
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Application
import javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM
import javax.ws.rs.core.Response

@ApplicationPath("api")
class RestApplication : Application()

@Path("/tk62")
class TK62CompilerEndpoint {
    @EJB
    private lateinit var compiler: KeyboardCompiler

    @POST
    @Path("v1")
    @Produces(APPLICATION_OCTET_STREAM)
    @Throws(CompilerException::class)
    fun compile(json: JsonObject): Response {
        val keymap = Keymap(json)

        val firmware = compiler.compile(keymap)

        return Response.ok(firmware)
                .header("Content-Disposition", """attachment; filename="tornado_tk62.hex"""")
                .header("Content-Length", firmware.size)
                .build()

    }
}