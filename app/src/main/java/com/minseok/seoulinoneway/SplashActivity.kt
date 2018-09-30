package com.minseok.seoulinoneway

import android.content.Intent
import android.os.Bundle
import com.minseok.seoulinoneway.common.ExtendedActivity
import java.util.*

class SplashActivity : ExtendedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timerTask = object: TimerTask() {
            override fun run() {
                runOnUiThread {
                    val setting = SeoulInOneWayApplication.getSettingManager()

                    Intent(this@SplashActivity, when {
                        setting.first -> IntroActivity::class.java
                        else -> MainCardBoardActivity::class.java
                    }).also {
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        startActivity(it)
                    }
                    finish()
                }
            }
        }

        Timer().also {
            it.schedule(timerTask, 2000)
        }
    }
}
