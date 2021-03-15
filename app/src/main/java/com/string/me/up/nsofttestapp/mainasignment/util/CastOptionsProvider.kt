package com.string.me.up.nsofttestapp.mainasignment.util

import android.content.Context
import com.google.android.gms.cast.LaunchOptions
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.framework.CastOptions
import com.google.android.gms.cast.framework.OptionsProvider
import com.google.android.gms.cast.framework.SessionProvider
import com.string.me.up.nsofttestapp.R

class CastOptionsProvider : OptionsProvider {
    override fun getCastOptions(mContext: Context?): CastOptions {
        val launchOptions = LaunchOptions.Builder()
            .setAndroidReceiverCompatible(true)
            .build()
        return CastOptions.Builder()
            .setLaunchOptions(launchOptions)
            .setReceiverApplicationId(mContext!!.getString(R.string.app_id))
            .build()
    }

    override fun getAdditionalSessionProviders(mContext: Context?): MutableList<SessionProvider>? {
        return null
    }
}
