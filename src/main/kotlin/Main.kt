
import com.github.ajalt.clikt.core.subcommands
import gen.GenCli
import info.InfoCli
import settings.SettingsCli
import update.UpdateCli

fun main(args: Array<String>) {

    if (!args.isEmpty()) {
        GenMc().subcommands(GenCli(), SettingsCli(), UpdateCli(), InfoCli()).main(args)
        return
    }

}
