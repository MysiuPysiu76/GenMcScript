package com.mysiupysiu.genmcscript

import java.io.File

fun main() {
    val name = readLine() ?: ""
    File("$name.json").writeText(readFile().replace("::", name))
}

fun readFile(): String {
    val stream = object {}.javaClass.getResourceAsStream("/blockstates/slab.json") ?: throw IllegalArgumentException("Exception: File not found.")
    return stream.bufferedReader().use { it.readText() }
}
