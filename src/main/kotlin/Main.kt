
import com.github.ajalt.clikt.core.subcommands
import gen.GenCli
import settings.SettingsCli

fun main(args: Array<String>) {

    if (!args.isEmpty()) {
        GenMc().subcommands(GenCli(), SettingsCli()).main(args)
        return
    }

}
