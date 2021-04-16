package iot.empiaurhouse.triage.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Explode
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivitySetupBinding
import iot.empiaurhouse.triage.utils.SetupVerify
import iot.empiaurhouse.triage.utils.TypeWriterTextView

class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding
    private lateinit var fadeInAnimation : Animation
    private lateinit var typeText : TypeWriterTextView
    private lateinit var chironVerify : SetupVerify


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
        chironVerify = SetupVerify()
    }


    private fun typeWriterText(typeText: String, charDelay: Int, targetTextView: TypeWriterTextView) {
        targetTextView.setCharacterDelay(charDelay.toLong())
        targetTextView.displayTextWithAnimation(typeText)
    }


    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.setupChironTitle.startAnimation(fadeInAnimation)
            binding.setupChironTitle.visibility = View.VISIBLE
            typeWriterText(getString(R.string.let_s_get_wired_up), 190, binding.wiredUpText)
            binding.wiredUpText.visibility = View.VISIBLE
        }, 1111)    }


    override fun onPause() {
        super.onPause()
        binding.wiredUpText.visibility = View.INVISIBLE
        binding.setupChironTitle.visibility = View.INVISIBLE
        binding.setupChironTitle.clearAnimation()
    }

    fun learnMore(view: View) {
        val url = "https://github.com/emeraldemperaur/Triage"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    fun copyrightLink(view: View) {
        val url = "https://www.mekaegwim.ca/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    fun proceedButton(view: View) {
        if (chironVerify.verifyID(binding.setupUsername, binding.chironIDNote)
            && (chironVerify.verifyURL(binding.setupServerUrl, binding.chironURLNote)
                    || chironVerify.verifyIP(binding.setupServerUrl, binding.chironURLNote))){
                        // TODO ping Chiron URL before intent
                        startActivity(Intent(this@SetupActivity, HubActivity::class.java))

        }

    }

    override fun onBackPressed()
    {
        moveTaskToBack(true)
        binding.wiredUpText.visibility = View.INVISIBLE
        binding.setupChironTitle.visibility = View.INVISIBLE
        binding.setupChironTitle.clearAnimation()
    }


}