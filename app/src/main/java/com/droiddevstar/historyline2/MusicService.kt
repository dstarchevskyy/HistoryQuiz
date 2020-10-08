package com.droiddevstar.historyline2


import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        cleanMediaPlayer()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.let{
            setDataSource()
            it.prepare()
            it.setVolume(1f, 1f)
            it.isLooping = true
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStart(intent: Intent?,
                         startId: Int) {
        cleanMediaPlayer()
        mediaPlayer?.start()
    }

    override fun onStartCommand(intent: Intent?,
                                flags: Int,
                                startId: Int): Int {
        mediaPlayer?.start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        cleanMediaPlayer()
        stopSelf()
        super.onDestroy()
    }

    private fun cleanMediaPlayer() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.release()
            }
        }
    }

    private fun setDataSource() {
        val descriptor = assets.openFd("birthofahero.mp3")
        descriptor.let {
            mediaPlayer?.setDataSource(
                it.fileDescriptor,
                it.startOffset,
                it.length)
            it.close()
        }
    }

}
