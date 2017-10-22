package no.tornado.keyboard.compiler.backend.api

import no.tornado.keyboard.compiler.service.KeyboardCompiler
import no.tornado.keyboard.compiler.service.RenderingContext
import no.tornado.keyboard.models.Keymap
import javax.inject.Inject
import javax.json.JsonObject
import javax.ws.rs.ApplicationPath
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Application
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response

@ApplicationPath("api")
class RestApplication : Application()

@Path("/")
class Entrypoint {
    @Inject
    private lateinit var compiler: KeyboardCompiler

    @POST
    @Produces(APPLICATION_JSON)
    fun compile(json: JsonObject): Response {
        val keymap = Keymap(json)
        val context = RenderingContext(keymap)
        return Response.ok(context.render()).build()
    }
}