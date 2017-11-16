package no.tornado.keyboard.compiler.service

import no.tornado.keyboard.models.Keymap
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.ejb.Lock
import javax.ejb.LockType
import javax.ejb.Singleton
import kotlin.concurrent.thread

@Singleton
@Lock(LockType.WRITE)
open class KeyboardCompiler {
    companion object {
        val firmwareBase: Path = Paths.get(System.getProperty("user.home"))
                .resolve("Projects")
                .resolve("qmk_firmware")

        val keymapPath: Path = firmwareBase
                .resolve("keyboards")
                .resolve("tornado")
                .resolve("keymaps")
                .resolve("default")
                .resolve("keymap.c")

        val firmwareBinary: Path = firmwareBase
                .resolve(".build")
                .resolve("tornado_default.hex")
    }

    @Throws(CompilerException::class)
    open fun compile(keymap: Keymap): ByteArray {
        val renderingContext = RenderingContext(keymap)
        val keymapDotC = renderingContext.render()

        Files.write(keymapPath, keymapDotC.toByteArray())

        val process = ProcessBuilder("make", "tornado")
                .directory(firmwareBase.toFile())
                .inheritIO()
                .start()

        var stdout = ""
        var stderr = ""

        val stdoutReader = thread(start = true) {
            stdout = process.inputStream.bufferedReader().use { it.readText() }
        }

        val stderrReader = thread(start = true) {
            stderr = process.errorStream.bufferedReader().use { it.readText() }
        }

        stdoutReader.join()
        stderrReader.join()

        val retval = process.waitFor()

        if (retval != 0)
            throw CompilerException(retval, stdout, stderr)

        return Files.readAllBytes(firmwareBinary)
    }
}