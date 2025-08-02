package com.mysiupysiu.genmcscript

import java.io.File

fun main() {
    val name = readLine() ?: ""

    File("generated/blockstates").mkdirs()
    File("generated/models/block").mkdirs()
    File("generated/models/item").mkdirs()

    File("generated/blockstates/${name}_slab.json").writeText(readFile("/blockstates/slab.json").replace("::", name))
    File("generated/models/block/${name}_slab.json").writeText(readFile("/models/block/slab.json").replace("::", name))
    File("generated/models/block/${name}_slab_top.json").writeText(readFile("/models/block/slab_top.json").replace("::", name))
    File("generated/models/item/${name}_slab.json").writeText(readFile("/models/item/slab.json").replace("::", name))
}

fun readFile(filename: String): String {
    val stream = object {}.javaClass.getResourceAsStream(filename) ?: throw IllegalArgumentException("Exception: File not found.")
    return stream.bufferedReader().use { it.readText() }
}
