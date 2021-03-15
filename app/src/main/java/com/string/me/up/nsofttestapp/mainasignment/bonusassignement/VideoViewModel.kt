package com.string.me.up.nsofttestapp.mainasignment.bonusassignement

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.common.images.WebImage
import com.string.me.up.nsofttestapp.mainasignment.util.Constants
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo

class VideoViewModel : ViewModel() {

    val hdVideoStream = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val mediaInfo = MutableLiveData<MediaInfo>()

    fun setVideoPath(videoId: String) {
        isLoading.postValue(true)
        VimeoExtractor.getInstance()
            .fetchVideoWithURL(videoId, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    mediaInfo.postValue(
                        buildMediaInfo(
                            video.title,
                            video.title,
                            videoId,
                            video.duration
                        )
                    )
                    val hdStream = video.streams[Constants.VIDEO_QUALITY.id]
                    hdStream?.let { hdVideoStream.postValue(it) }
                    isLoading.postValue(false)
                }

                override fun onFailure(t: Throwable) {
                    t.printStackTrace()
                    isLoading.postValue(false)
                }
            })
    }

    private fun buildMediaInfo(
        title: String,
        keyTitle: String,
        url: String,
        streamDuration: Long
    ): MediaInfo? {
        val movieMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
        movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, keyTitle)
        movieMetadata.putString(MediaMetadata.KEY_TITLE, title)
        movieMetadata.addImage(WebImage(Uri.parse(url)))
        movieMetadata.addImage(WebImage(Uri.parse(url)))
        return MediaInfo.Builder(url)
            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
            .setContentType("videos/mp4")
            .setMetadata(movieMetadata)
            .setStreamDuration(streamDuration * 1000)
            .build()
    }
}
