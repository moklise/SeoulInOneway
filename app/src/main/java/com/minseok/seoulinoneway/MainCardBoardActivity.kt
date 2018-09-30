package com.minseok.seoulinoneway

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.FrameLayout
import android.widget.Toast
import com.minseok.seoulinoneway.common.BottomNavigationHelper
import com.minseok.seoulinoneway.common.ExtendedActivity
import com.minseok.seoulinoneway.fragment.CardBoardFragment
import com.minseok.seoulinoneway.fragment.CultureBoardFragment
import com.minseok.seoulinoneway.fragment.DetailBoardFragment
import com.minseok.seoulinoneway.setting.MainMenuBottomDialog

class MainCardBoardActivity : ExtendedActivity(), MainMenuBottomDialog.BottomSheetListener {
    val bottomNavigation: BottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }
    val layoutContent by lazy { findViewById<FrameLayout>(R.id.container_main_content) }

    companion object {
        @JvmStatic
        lateinit var sBottomNavigation: BottomNavigationView
    }

    val mCompanion = Companion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main_card_board)

        mCompanion.sBottomNavigation = bottomNavigation
        BottomNavigationHelper.removeShiftMode(bottomNavigation)

        bottomNavigation.setOnNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.action_home -> {
                    supportFragmentManager
                            .beginTransaction()
                            .replace(layoutContent.id, CardBoardFragment())
                            .commit()
                }
                R.id.action_detail -> {
                    supportFragmentManager
                            .beginTransaction()
                            .replace(layoutContent.id, DetailBoardFragment())
                            .commit()
                }
                R.id.action_culture -> {
                    supportFragmentManager
                            .beginTransaction()
                            .replace(layoutContent.id, CultureBoardFragment())
                            .commit()
                }
                R.id.action_setting -> {
                    MainMenuBottomDialog().apply {
                    }.also { it.show(supportFragmentManager, "mainBottomSheetDialog") }

                    bottomNavigation.getChildAt(0).performClick()
                    return@setOnNavigationItemSelectedListener false
                }
                else -> {}
            }

            true
        }

        supportFragmentManager
                .beginTransaction()
                .replace(layoutContent.id, CardBoardFragment())
                .commit()
    }

    override fun onButtonClicked(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun resetLocation() {
        Intent(this, IntroActivity::class.java).also { startActivity(it) }
    }
}
