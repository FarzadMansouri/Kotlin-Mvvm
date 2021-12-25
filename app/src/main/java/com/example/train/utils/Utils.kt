package com.example.train.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.train.UserStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


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

object UserSerializer:Serializer<UserStore>{
    override val defaultValue: UserStore = UserStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserStore {
        try {
                return UserStore.parseFrom(input)
        }catch (ex:InvalidProtocolBufferException){
            throw CorruptionException("Cannot Read Proto",ex)
        }
    }

    override suspend fun writeTo(t: UserStore, output: OutputStream) =t.writeTo(output)

}
