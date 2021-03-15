package com.string.me.up.nsofttestapp.mainasignment.bonusassignement

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.framework.*
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.string.me.up.nsofttestapp.R
import com.string.me.up.nsofttestapp.databinding.FragmentBonusBinding
import com.string.me.up.nsofttestapp.mainasignment.adapters.BonusAdapter
import com.string.me.up.nsofttestapp.mainasignment.adapters.OnVideoClick
import com.string.me.up.nsofttestapp.mainasignment.util.PublicVideos
import java.util.*
import kotlin.collections.ArrayList


class VideoActivity : AppCompatActivity(), OnVideoClick {

    private lateinit var binding: FragmentBonusBinding
    private lateinit var viewModel: VideoViewModel
    private lateinit var castContext: CastContext
    private var mVideoId: String? = null
    private var vCastSession: CastSession? = null
    private var mSessionManagerListener: SessionManagerListener<CastSession>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_bonus)
        viewModel = ViewModelProvider(this@VideoActivity)[VideoViewModel::class.java]
        castContext = CastContext.getSharedInstance(this)
        vCastSession = castContext.sessionManager.currentCastSession
        setupCastListener()
        setLifeCycle(binding, viewModel)
        val videoAdapter = BonusAdapter(populateVideoList(), this).also {
            binding.videoRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@VideoActivity)
                adapter = it
            }
        }

        binding.playImage.setOnClickListener {
            val userInfoDialog = AlertDialog.Builder(this)
            userInfoDialog.setTitle(getString(R.string.dialog_title))
            userInfoDialog.setMessage(getString(R.string.dialog_message))
            userInfoDialog.setCancelable(true)
            userInfoDialog.setPositiveButton(
                getString(R.string.dialog_positive_button)
            ) { _, _ -> }.create().show()
        }

        viewModel.hdVideoStream.observe(
            this, { stream ->
                stream?.let {
                    playVideo(it)
                }
            })

        viewModel.mediaInfo.observe(this, {
            it?.let {
                loadRemoteMedia(it)
            }
        })
    }

    override fun getVideoId(videoId: String) {
        binding.playImage.visibility = View.GONE
        viewModel.setVideoPath(videoId)
        mVideoId = videoId
    }

    override fun onPause() {
        super.onPause()
        castContext.sessionManager.removeSessionManagerListener(
            mSessionManagerListener, CastSession::class.java
        )
    }

    override fun onResume() {
        super.onResume()
        castContext.sessionManager.addSessionManagerListener(
            mSessionManagerListener, CastSession::class.java
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.browse, menu)
        val mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(
            applicationContext,
            menu,
            R.id.media_route_menu_item
        )
        return true
    }

    private fun setLifeCycle(binding: FragmentBonusBinding, viewModel: VideoViewModel) {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun playVideo(videoStream: String) {
        this.runOnUiThread {
            binding.videoView.setVideoPath(videoStream)
            binding.videoView.requestFocus()
            binding.videoView.setOnPreparedListener { mp ->
                val mediaController = object : android.widget.MediaController(this) {
                    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
                        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
                            super.hide()
                            mp.release()
                            finish()
                            return true
                        }
                        return super.dispatchKeyEvent(event)
                    }
                }
                setControls(mediaController)
                mp.isLooping = true
                binding.videoView.start()
            }
        }
    }

    private fun setControls(mediaController: android.widget.MediaController) {
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(mediaController)
    }

    private fun populateVideoList(): ArrayList<PublicVideos> {
        val videoList = ArrayList<PublicVideos>()
        videoList.add(
            PublicVideos(
                getString(R.string.video_one),
                "https://vimeo.com/513971042", R.drawable.video
            )
        )
        videoList.add(
            PublicVideos(
                getString(R.string.video_two),
                "https://vimeo.com/380157225", R.drawable.video
            )
        )
        videoList.add(
            PublicVideos(
                getString(R.string.video_three),
                "https://vimeo.com/521629477", R.drawable.video
            )
        )
        videoList.add(
            PublicVideos(
                getString(R.string.video_four),
                "https://vimeo.com/512321683", R.drawable.video
            )
        )
        return videoList
    }

    private fun setupCastListener() {
        mSessionManagerListener = object : SessionManagerListener<CastSession> {
            override fun onSessionEnded(session: CastSession, error: Int) {}
            override fun onSessionResumed(session: CastSession, wasSuspended: Boolean) {
                onApplicationConnected(session)
            }

            override fun onSessionResumeFailed(session: CastSession, error: Int) {}
            override fun onSessionStarted(session: CastSession, sessionId: String) {
                onApplicationConnected(session)
            }

            override fun onSessionStartFailed(session: CastSession, error: Int) {}
            override fun onSessionStarting(session: CastSession) {}
            override fun onSessionEnding(session: CastSession) {}
            override fun onSessionResuming(session: CastSession, sessionId: String) {}
            override fun onSessionSuspended(session: CastSession, reason: Int) {}
            private fun onApplicationConnected(castSession: CastSession) {
                vCastSession = castSession
            }
        }
    }

    private fun loadRemoteMedia(mediaInfo: MediaInfo, position: Int = 0, autoPlay: Boolean = true) {
        vCastSession?.let {
            val remoteMediaClient: RemoteMediaClient = vCastSession!!.remoteMediaClient
            remoteMediaClient.load(
                MediaLoadRequestData.Builder()
                    .setMediaInfo(mediaInfo)
                    .setAutoplay(autoPlay)
                    .setCurrentTime(position.toLong()).build()
            )
        }
    }
}