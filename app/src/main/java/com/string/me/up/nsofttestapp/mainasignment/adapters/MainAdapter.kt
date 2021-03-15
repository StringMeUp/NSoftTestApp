package com.string.me.up.nsofttestapp.mainasignment.adapters

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.string.me.up.nsofttestapp.R
import com.string.me.up.nsofttestapp.databinding.ButtonCardBinding
import com.string.me.up.nsofttestapp.mainasignment.util.Task
import java.util.concurrent.LinkedBlockingDeque

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var adapterList = LinkedBlockingDeque<Task>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    class MainViewHolder(private val binding: ButtonCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentText: Task) {
            binding.buttonText.setText(currentText.name!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = DataBindingUtil.inflate<ButtonCardBinding>(
            from(parent.context),
            R.layout.button_card, parent, false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(adapterList.toArray()[position] as Task)

    override fun getItemCount(): Int = adapterList.toArray().size
}