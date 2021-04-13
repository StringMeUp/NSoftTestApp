package com.string.me.up.nsofttestapp.mainasignment.adapters

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.string.me.up.nsofttestapp.R
import com.string.me.up.nsofttestapp.databinding.VideoCardBinding
import com.string.me.up.nsofttestapp.mainasignment.util.PublicVideos

class BonusAdapter(
    private val videosList: ArrayList<PublicVideos>,
    private val videoClickListener: OnVideoClick
) :
    RecyclerView.Adapter<BonusAdapter.BonusViewHolder>() {

    class BonusViewHolder(
        private val binding: VideoCardBinding,

        private val videoClickListener: OnVideoClick
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentVideo: PublicVideos) {
            binding.run {
                videoTitle.text = currentVideo.videoName
                videoImage.setImageResource(currentVideo.imgResource)
                videoImage.setOnClickListener {
                    videoClickListener.getVideoId(currentVideo.url)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val binding = DataBindingUtil.inflate<VideoCardBinding>(
            from(parent.context),
            R.layout.video_card, parent, false
        )
        return BonusViewHolder(binding, videoClickListener)
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) =
        holder.bind(videosList[position])

    override fun getItemCount(): Int = videosList.size
}

interface OnVideoClick {
    fun getVideoId(videoId: String)
}