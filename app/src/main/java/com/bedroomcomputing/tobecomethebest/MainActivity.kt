package com.bedroomcomputing.tobecomethebest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.bedroomcomputing.tobecomethebest.db.Settings
import com.bedroomcomputing.tobecomethebest.ui.main.MainFragment
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        var settings = Settings(userName = "Player")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val navController = findNavController(R.id.nav_host_fragment)

        MobileAds.initialize(this) {}
        val testDeviceIds = Arrays.asList("6ADCE07152849E36FF16E4004F15D374")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
    }
}