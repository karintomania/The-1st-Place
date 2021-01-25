package com.bedroomcomputing.tobecomethebest.ui.result

import android.os.CountDownTimer
import android.service.notification.NotificationListenerService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bedroomcomputing.tobecomethebest.db.Settings

class ResultViewModel : ViewModel() {
    var userPoint = 0
    val rankingItemListLive = MutableLiveData<List<RankingItem>>()
    var rankingItemList = mutableListOf<RankingItem>()
    var settings = Settings()

    val showFirst = MutableLiveData<Boolean>()

    val list = mutableListOf<RankingItem>()
    var count = 0

    val nameList = mutableListOf("Sandy","Jim","Shota","BJ","Sam","James","Taro","Sky","pipipit", "Raja", "Tim", "Ben", "Harry", "Ike", "Ian", "Andrea", "Molly", "Jodie", "Jen", "Sarah", "Duke")
    val pointList = mutableListOf<Int>()

    val timer = object : CountDownTimer(6000, 600) {
        override fun onTick(millisUntilFinished: Long) {
            if(list.size > count){
                rankingItemList.add(0, list.get(list.size - count -1))
                rankingItemListLive.value = rankingItemList.toList()
                count ++
            }
        }

        override fun onFinish() {
            showFirst.value = true
        }
    }

    init{
        timer.start()
    }


    fun generateRanking(){

        generatePointList()
        pointList.sortDescending()

        nameList.shuffle()

        for(i in 0..8){
            list.add(RankingItem(rank = i + 2, name = nameList[i], score = pointList[i]))
        }
    }

    private fun generatePointList(){
        // outstandingPercent is in 100 - 10100%
        val outstandingPercent = (settings.outstandingRate * settings.outstandingRate + 100)/100
        val limit = userPoint / outstandingPercent -1

        //極端に少ない数を減らすために多めに生成する
        for(i in 0..12) {
            pointList.add((0..limit).random())
        }
    }

}