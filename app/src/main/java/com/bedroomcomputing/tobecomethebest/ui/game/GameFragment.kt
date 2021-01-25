package com.bedroomcomputing.tobecomethebest.ui.game

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bedroomcomputing.tobecomethebest.MainActivity
import com.bedroomcomputing.tobecomethebest.R
import com.bedroomcomputing.tobecomethebest.databinding.GameFragmentBinding
import com.bedroomcomputing.tobecomethebest.databinding.ResultFragmentBinding


class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Game", "GameOncreate")
        val binding = GameFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.setLifecycleOwner(this)

        viewModel.settings = MainActivity.settings


        binding.switchGameAuto.isChecked = viewModel.settings.autoPlay
        val listener = CompoundButton.OnCheckedChangeListener{ compoundButton: CompoundButton, checked: Boolean ->
            viewModel.settings.autoPlay = checked
        }
        binding.switchGameAuto.setOnCheckedChangeListener(listener)

        var lastEncourage = ""
        var count = 0
        // detect change of encourage
        viewModel.encourage.observe(viewLifecycleOwner, Observer{
            if(it != lastEncourage){
                Log.i("Game", "${count}")
                binding.textEncourage.text = it
                val id = when(count){
                    0 -> R.drawable.red
                    1 -> R.drawable.yellow
                    2 -> R.drawable.purple
                    else -> R.drawable.blue
                }
                binding.imageViewEncourageBack.setImageResource(id)
                binding.frameEncourage.apply{
                    alpha = 1f
                    animate().alpha(0f).setDuration(1500L).setListener(null)
                }
                lastEncourage = it
                count ++
            }
        })





        viewModel.isGameFinished.observe(viewLifecycleOwner, Observer{
            if(it){
                binding.textEncourage.text = "Finish"
                val finishListener = FinishListener {onGameFinish()}
                binding.frameEncourage.apply{
                    alpha = 1f
                    animate().alpha(0f).setDuration(3000L).setListener(finishListener)
                }
                binding.textGamePoint.apply{
                    alpha = 1f
                    animate().alpha(0f).setDuration(3000L).setListener(null)
                }

                viewModel.isGameFinished.value = false
            }
        })

        binding.viewModel = viewModel

        binding.buttonResult.setOnClickListener {
            // start game on first tap
            if(viewModel.point.value == 0){
                viewModel.onGameStart()
            }

            viewModel.increasePoint()
        }

        return binding.root
    }

    fun onGameFinish(){
        val point = viewModel.point
        val action = GameFragmentDirections.actionGameFragmentToResultFragment(point.value?:0)
        findNavController().navigate(action)

    }


    class FinishListener(val onGameFinish:()->Unit): Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            onGameFinish()
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}