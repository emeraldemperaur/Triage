package iot.empiaurhouse.triage

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.databinding.ActivityLaunchBinding

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private lateinit var fadeInAnimation : Animation
    private lateinit var rotationAnimation : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        val viewLaunch = binding.root
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        rotationAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        setContentView(viewLaunch)
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

    override fun onBackPressed()
    {
        moveTaskToBack(true)
    }

}