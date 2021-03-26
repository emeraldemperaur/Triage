package iot.empiaurhouse.triage.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Fade
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivityInitBinding
import iot.empiaurhouse.triage.utils.TypeWriterTextView
import java.util.*


class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    private lateinit var fadeInAnimation : Animation
    private lateinit var typeText : TypeWriterTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        val viewInit = binding.root
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fader)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Fade()
            enterTransition.duration = 2000
            exitTransition = Fade()
            exitTransition.duration = 2000
        }
        setContentView(viewInit)
        typeText = binding.subtitle
        binding.title.startAnimation(fadeInAnimation)
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.subtitle.visibility = View.VISIBLE
                typeWriterText(getString(R.string.chiron_data_analyst),131, typeText)
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@InitActivity, SetupActivity::class.java),
                            ActivityOptions.makeSceneTransitionAnimation(this@InitActivity).toBundle())
                    finish()
                }, 3200)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })



    }


    private fun typeWriterText(typeText: String, charDelay: Int, targetTextView: TypeWriterTextView) {
        targetTextView.setCharacterDelay(charDelay.toLong())
        targetTextView.displayTextWithAnimation(typeText)
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