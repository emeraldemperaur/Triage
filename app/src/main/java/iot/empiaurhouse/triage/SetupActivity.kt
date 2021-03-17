package iot.empiaurhouse.triage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Explode
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.databinding.ActivitySetupBinding

class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding
    private lateinit var fadeInAnimation : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        val viewSetup = binding.root
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fader)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Explode()
            enterTransition.duration = 1000


        }
        setContentView(viewSetup)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.setupChironTitle.startAnimation(fadeInAnimation)
            binding.setupChironTitle.visibility = View.VISIBLE
        }, 1111)

    }
}