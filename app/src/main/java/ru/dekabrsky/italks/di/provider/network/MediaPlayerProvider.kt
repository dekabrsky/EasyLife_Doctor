package ru.dekabrsky.italks.di.provider.network

import android.content.Context
import android.media.MediaPlayer
import javax.inject.Inject
import javax.inject.Provider

class MediaPlayerProvider @Inject constructor(private val context: Context): Provider<MediaPlayer> {
    override fun get(): MediaPlayer {
        return MediaPlayer.create(context, ru.dekabrsky.italks.game.R.raw.soundtrack).apply { isLooping = true }
    }
}