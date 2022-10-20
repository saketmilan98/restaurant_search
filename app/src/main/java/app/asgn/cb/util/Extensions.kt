package app.asgn.cb.util

import java.text.NumberFormat

fun String.toDecimalFormat() : String{
    return NumberFormat.getInstance().format(this.toDouble())
}