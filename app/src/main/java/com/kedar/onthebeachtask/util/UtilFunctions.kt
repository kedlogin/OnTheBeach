package com.kedar.onthebeachtask.util

import java.text.SimpleDateFormat
import java.util.*

val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

fun parseDate(dateStr:String): Date = simpleDateFormat.parse(dateStr)