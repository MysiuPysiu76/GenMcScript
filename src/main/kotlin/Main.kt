package com.mysiupysiu.genmcscript

import java.io.File

fun main() {
    val values = readLine() ?: ""
    val parts = values.trim().split(" ")

    val type = parts[0]
    val name = parts[1]

    File("generated/blockstates").mkdirs()
    File("generated/models/block").mkdirs()
    File("generated/models/item").mkdirs()

    if (type == "slab") {
        File("generated/blockstates/${name}_slab.json").writeText(readFile("/blockstates/slab.json").replace("::", name))
        File("generated/models/block/${name}_slab.json").writeText(readFile("/models/block/slab.json").replace("::", name))
        File("generated/models/block/${name}_slab_top.json").writeText(readFile("/models/block/slab_top.json").replace("::", name))
        File("generated/models/item/${name}_slab.json").writeText(readFile("/models/item/slab.json").replace("::", name))
    } else {
        File("generated/blockstates/${name}_stairs.json").writeText(readFile("/blockstates/stairs.json").replace("::", name))
        File("generated/models/block/${name}_stairs.json").writeText(readFile("/models/block/stairs.json").replace("::", name))
        File("generated/models/block/${name}_stairs_inner.json").writeText(readFile("/models/block/stairs_inner.json").replace("::", name))
        File("generated/models/block/${name}_stairs_outer.json").writeText(readFile("/models/block/stairs_outer.json").replace("::", name))
        File("generated/models/item/${name}_stairs.json").writeText(readFile("/models/item/stairs.json").replace("::", name))
    }

}

fun readFile(filename: String): String {
    val stream = object {}.javaClass.getResourceAsStream(filename) ?: throw IllegalArgumentException("Exception: File not found.")
    return stream.bufferedReader().use { it.readText() }
}
