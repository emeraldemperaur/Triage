package iot.empiaurhouse.triage.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivityLaunchBinding
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import iot.empiaurhouse.triage.viewmodel.InitViewModel

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private lateinit var fadeInAnimation : Animation
    private lateinit var rotationAnimation : Animation
    private lateinit var initViewModel: InitViewModel
    private lateinit var userManager: UserPreferenceManager
    private lateinit var serverUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        initViewModel = ViewModelProvider(this).get(InitViewModel::class.java)
        initViewModel.pingServer(this)
        val viewLaunch = binding.root
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        rotationAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        setContentView(viewLaunch)
        urlPrep()
        binding.devIntroLogo.visibility = View.INVISIBLE
        binding.devIntroLogo.startAnimation(fadeInAnimation)
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.devIntroLogo.startAnimation(rotationAnimation)
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@LaunchActivity, InitActivity::class.java),
                        ActivityOptions.makeSceneTransitionAnimation(this@LaunchActivity).toBundle())
                    finish()
                }, 2696)
            }

            override fun onAnimationStart(animation: Animation?) {
                binding.devIntroLogo.visibility = View.VISIBLE
            }
        })


    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun urlPrep(){
        userManager = UserPreferenceManager(this)
        serverUrl = userManager.getServerUrl().toString()
        println("\t\nThis is the init server Url found: " + serverUrl)
        if (serverUrl != null) {
            if (!serverUrl.endsWith("/")) {
                //serverUrl = serverUrl.plus("/")
            }
            if (!serverUrl.startsWith("http://") && !serverUrl.startsWith("https://")) {
                serverUrl = "https://$serverUrl"
            }
            println("\t\nThis is the prepped server Url found: " + serverUrl)
        }
    }




    override fun onBackPressed()
    {
        moveTaskToBack(true)
    }

}