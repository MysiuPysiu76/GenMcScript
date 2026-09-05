
import com.github.ajalt.clikt.core.subcommands
import gen.GenCli
import models.ModelsCli
import info.InfoCli
import settings.SettingsCli
import update.UpdateCli

fun main(args: Array<String>) {

    GenMc().subcommands(GenCli(), ModelsCli(), SettingsCli(), UpdateCli(), InfoCli()).main(args)

}
