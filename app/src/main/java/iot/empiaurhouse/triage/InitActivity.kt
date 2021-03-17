package iot.empiaurhouse.triage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    private lateinit var fadeInAnimation : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        val viewInit = binding.root
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fader)
        setContentView(viewInit)
        binding.title.startAnimation(fadeInAnimation)
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.subtitle.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@InitActivity, SetupActivity::class.java))
                    finish()
                }, 3000)
            }

            override fun onAnimationStart(animation: Animation?) {
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


}