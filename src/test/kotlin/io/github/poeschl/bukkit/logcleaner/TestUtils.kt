package io.github.poeschl.bukkit.logcleaner

import java.util.*

/**
 * Created on 29.11.2016.
 */

fun Calendar.createDate(year: Int, month: Int, day: Int): Date {
    this.set(year, month, day)
    return this.time
}