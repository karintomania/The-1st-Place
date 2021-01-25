package com.bedroomcomputing.tobecomethebest.ui.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bedroomcomputing.tobecomethebest.db.Settings


class GameViewModel : ViewModel() {
    val point = MutableLiveData<Int>()
    var pointPerTap = 1
    val pointLetter = Transformations.map(point) {
        String.format("%,d", it)
    }
    val isGameFinished = MutableLiveData<Boolean>()
    val encourage = MutableLiveData<String>()
    var lastEncourage = ""
    var settings = Settings()

    val timer = object : CountDownTimer(20000, 100) {
        override fun onTick(millisUntilFinished: Long) {
            when(millisUntilFinished){
                in 0L..5000L ->  {
                    pointPerTap = 10000
                    encourage.value = "Wonderful!!"
                }
                in 5001L..8000L ->  {
                    pointPerTap = 1000
                    encourage.value = "Amazing!!"
                }
                in 8001L..13000L -> {
                    pointPerTap =  100
                    encourage.value = "Great!!"
                }
                in 13001L..18000L ->  {
                    pointPerTap = 10
                    encourage.value = "Good!!"
                }
                else -> {
                    pointPerTap = 1
                }
            }

            // if auto play = true, auto increase
            if(settings.autoPlay){
                increasePoint()
            }
        }

        override fun onFinish() {
            isGameFinished.value = true
        }
    }

    init{
        point.value = 0
        isGameFinished.value = false
    }

    fun onGameStart(){
            timer.start()
    }

    fun increasePoint(){
        point.value = point.value?.plus(pointPerTap)
    }
}