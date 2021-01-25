package com.bedroomcomputing.tobecomethebest.ui.result

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedroomcomputing.tobecomethebest.MainActivity
import com.bedroomcomputing.tobecomethebest.R
import com.bedroomcomputing.tobecomethebest.databinding.ResultFragmentBinding
import com.bedroomcomputing.tobecomethebest.databinding.ResultItemBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private lateinit var viewModel: ResultViewModel
    private lateinit var binding: ResultFragmentBinding
    val args: ResultFragmentArgs by navArgs()
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mInterstitialAd = InterstitialAd(requireContext())
        // test ads
//        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        // real ads
        mInterstitialAd.adUnitId = "ca-app-pub-2164726777115826/2118823306"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        binding = ResultFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)

        val settings = MainActivity.settings
        viewModel.settings = settings
        binding.textViewTitle.setText(settings.rankingTitle)
        binding.textViewName.setText(settings.userName)

        val point = args.Point

        viewModel.userPoint = point
        viewModel.generateRanking()
        binding.textViewPoint.text = String.format("%,d",point)


        val recyclerView = binding.recyclerView
        val adapter = RankingAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.rankingItemListLive.observe(viewLifecycleOwner, Observer {
            Log.i("ResultFragment", "${it.count()}")
            adapter.submitList(it)
        })

        viewModel.showFirst.observe(viewLifecycleOwner, Observer{
            if(it){
                binding.layoutFirst.apply{
                    val listner = VisibleLister(binding)
                    animate().alpha(1f).setDuration(3000L).setListener(listner)
                    alpha = 1f
                }
            }
        })

        binding.buttonBackHome.setOnClickListener {
            mInterstitialAd.show()
            val action = ResultFragmentDirections.actionResultFragmentToMainFragment()
            findNavController().navigate(action)
        }

        binding.buttonShare.setOnClickListener{
            onClickShare()
        }
        return binding.root
    }

    class VisibleLister(val binding:ResultFragmentBinding):AnimatorListener{
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            binding.buttonBackHome.visibility = View.VISIBLE
            binding.buttonShare.visibility = View.VISIBLE
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

    fun onClickShare(){

        val message = getString(R.string.resultShareMessage)
                                .replace("{Player}", viewModel.settings.userName)
                                .replace("{Title}", viewModel.settings.rankingTitle)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}