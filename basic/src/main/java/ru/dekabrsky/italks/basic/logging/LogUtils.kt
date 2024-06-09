package ru.dekabrsky.easylife.basic.logging

import android.util.Log
import androidx.fragment.app.Fragment

fun Fragment.log(e: Throwable, msg: String? = "") =
    e.message?.let { Log.d(this.tag, "$it : $msg") }