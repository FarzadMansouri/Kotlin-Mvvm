package com.example.mydi.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import java.util.logging.Filter


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {

    Intent(this, activity::class.java).also {

        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

}

fun View.visible(isVisible: Boolean) {

    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enable: Boolean) {
    isEnabled = enable
    alpha = if (enable) 1f else .5f
}
