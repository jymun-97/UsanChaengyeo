package com.usanchaengyeo.usanchaengyeo.data.model.forecast

data class Info(
    val start: String,
    val end: String,
    val description: String,
    val recommendText: String
) {
    override fun toString(): String =
        "$start ~ ${end}mm : $description"
}