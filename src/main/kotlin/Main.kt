
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) {

    if (!args.isEmpty()) {
        GenMc().subcommands(GenCli(), SettingsCli()).main(args)
        return
    }

}
