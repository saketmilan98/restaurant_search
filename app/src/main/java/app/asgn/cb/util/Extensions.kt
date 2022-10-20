package app.asgn.cb.util

import android.widget.ImageView
import app.asgn.cb.R
import com.bumptech.glide.Glide
import java.text.NumberFormat

fun String.toDecimalFormat() : String{
    return NumberFormat.getInstance().format(this.toDouble())
}
fun ImageView.loadImage(path: String?) {
    try {
        if (!path.isNullOrEmpty()) {
            if (path.toIntOrNull() != null) {
                Glide.with(context).load(path.toInt()).placeholder(R.drawable.placeholder1).into(this)
            } else {
                Glide.with(context).load(path).placeholder(R.drawable.placeholder1).into(this)
            }
        } else {
            setImageResource(R.drawable.placeholder1)
        }
    } catch (ex: Exception) {

    }
}