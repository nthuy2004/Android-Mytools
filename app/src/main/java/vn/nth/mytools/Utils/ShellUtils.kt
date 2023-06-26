package vn.nth.mytools.Utils

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import kotlin.system.exitProcess

class ShellUtils {
    companion object {
        private val TAG = "NTH_MYTOOLS_SHELL"
        private val SU = "su"
        private val SH = "sh"
        private val END_LINE = "\n"
        private val COMMAND_EXIT = "exit\n"

        public fun exec(cmd: String, root: Boolean): ShellResult {
            var process : Process? = null
            var os : DataOutputStream? = null
            var res : Int = -1
            val output : StringBuilder = StringBuilder()
            val errOutput : StringBuilder = StringBuilder()
            var buffer : BufferedReader? = null
            try {
                val start = System.currentTimeMillis()
                process = Runtime.getRuntime().exec(if(root) SU else SH)
                os = DataOutputStream(process.outputStream)
                os.write(cmd.toByteArray())
                os.writeBytes(END_LINE);
                os.writeBytes(COMMAND_EXIT);
                os.flush()
                res = process.waitFor()
                buffer = process.inputStream.bufferedReader()
                val errBuf = process.errorStream.bufferedReader()

                var s: String?
                while (buffer.readLine().also { s = it } != null) {
                    output.append(s)
                }
                while (errBuf.readLine().also { s = it } != null) {
                    errOutput.append(s)
                }
                val end = System.currentTimeMillis()
                return ShellResult(output.toString(), errOutput.toString(), end-start)
            }
            catch (ex : Exception) {
                return ShellResult()
            }
            finally {
                os?.let {
                    it.close()
                }
                process?.let {
                    it.destroy()
                }
                buffer?.let {
                    it.close()
                }
            }
        }
    }

    class ShellResult {
        public var successOutput: String? = null
        public var errorOutput: String? = null
        public var execTime: Long = 0L
        constructor() {

        }
        constructor(a : String?, b : String?, c : Long) {
            successOutput = a
            errorOutput = b
            execTime = c
        }
        public fun isSuccess() : Boolean {
            return !successOutput.isEmpty() && errorOutput.isEmpty()
        }
        public fun getOutput() : String? {
            return if(isSuccess()) successOutput else errorOutput
        }

    }
}
