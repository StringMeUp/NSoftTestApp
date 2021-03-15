package com.string.me.up.nsofttestapp.mainasignment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.string.me.up.nsofttestapp.R
import com.string.me.up.nsofttestapp.databinding.FragmentMainBinding
import com.string.me.up.nsofttestapp.mainasignment.adapters.MainAdapter
import com.string.me.up.nsofttestapp.mainasignment.util.Task
import java.util.*
import java.util.concurrent.*

class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var blockingDeque: LinkedBlockingDeque<Task>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this@MainFragment)[MainViewModel::class.java]
        blockingDeque = LinkedBlockingDeque<Task>()
        setLifeCycle(binding, viewModel)
        val mainAdapter = MainAdapter()
        setRecyclerViewInitList(mainAdapter, binding)
        setListeners(
            binding.buttonOne,
            binding.buttonTwo,
            binding.buttonThree,
            binding.buttonFour,
            binding.buttonFive,
            binding.buttonSix,
            binding.buttonSeven,
            binding.buttonEight,
            binding.buttonNine
        )
        val singleThreadedExecutor: ExecutorService = Executors.newSingleThreadScheduledExecutor()
        viewModel.currentList.observe(viewLifecycleOwner, {
            it?.let {
                it.forEach { task ->
                    singleThreadedExecutor.submit(task)
                }
                if (it.first.name == R.string.button_reset_text)
                    mainAdapter.adapterList = LinkedBlockingDeque()
                else mainAdapter.adapterList = it
                it.clear()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.tag) {
            binding.buttonOne.tag -> {
                val t1 =
                    Task(R.string.button_one_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t1)
                viewModel.updateCurrentList(blockingDeque)
            }
            binding.buttonTwo.tag -> {
                val t2 =
                    Task(R.string.button_two_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t2)
                viewModel.updateCurrentList(blockingDeque)
            }
            binding.buttonThree.tag -> {
                val t3 =
                    Task(R.string.button_three_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t3)
                viewModel.updateCurrentList(blockingDeque)
            }
            binding.buttonFour.tag -> {
                val t4 =
                    Task(R.string.button_four_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t4)
                viewModel.updateCurrentList(blockingDeque)
            }
            binding.buttonFive.tag -> {
                val t5 =
                    Task(R.string.button_five_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t5)
                viewModel.updateCurrentList(blockingDeque)
            }
            binding.buttonSix.tag -> {
                val t6 =
                    Task(R.string.button_six_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t6)
                viewModel.updateCurrentList(blockingDeque)
            }
            binding.buttonSeven.tag -> {
                val t7 =
                    Task(R.string.button_seven_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t7)
                viewModel.updateCurrentList(blockingDeque)
            }
            binding.buttonEight.tag -> {
                val t8 =
                    Task(R.string.button_eight_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t8)
                viewModel.updateCurrentList(blockingDeque)
            }
            binding.buttonNine.tag -> {
                val t9 =
                    Task(R.string.button_reset_text, viewModel.setRandomDelay(), requireContext())
                blockingDeque.offer(t9)
                viewModel.updateCurrentList(blockingDeque, t9)
            }
        }
    }

    private fun setListeners(vararg button: Button) {
        button.forEach { it.setOnClickListener(this) }
    }

    private fun setLifeCycle(binding: FragmentMainBinding, viewModel: MainViewModel) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    private fun setRecyclerViewInitList(
        mainAdapter: MainAdapter,
        binding: FragmentMainBinding
    ) {
        binding.buttonRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
    }
}