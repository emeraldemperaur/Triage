package iot.empiaurhouse.triage.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivityMeactivityBinding

class MEActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeactivityBinding
    private lateinit var fadeInAnimation : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeactivityBinding.inflate(layoutInflater)
        val viewSetup = binding.root
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fader)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Slide()
            enterTransition.duration = 769

        }
        setContentView(viewSetup)
        initView()
    }


    private fun initView(){
        binding.meInfoClose.setOnClickListener {
            finish()
        }

        binding.phoneMeButton.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "+17782680948")
            startActivity(dialIntent)
        }

        binding.devInfoFootNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.dev_website -> {
                    val url = "https://www.mekaegwim.ca/"
                    val websiteIntent = Intent(Intent.ACTION_VIEW)
                    websiteIntent.data = Uri.parse(url)
                    startActivity(websiteIntent)
                    true
                }
                R.id.dev_linkedin -> {
                    val url = "https://www.linkedin.com/in/emekaegwimdeveloper/"
                    val linkedInIntent = Intent(Intent.ACTION_VIEW)
                    linkedInIntent.data = Uri.parse(url)
                    startActivity(linkedInIntent)
                    true
                }
                R.id.dev_email -> {
                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.data = Uri.parse("mailto:")
                    emailIntent.type = "text/plain"
                    val toRecipient = "me@mekaegwim.ca"
                    val subject = "Re: ME Portfolio"
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(toRecipient))
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    try {
                        startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                    }
                    catch (e: Exception){
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }



                    true
                }
                R.id.dev_instagram -> {
                    val url = "https://www.instagram.com/darth.meka"
                    val instagramIntent = Intent(Intent.ACTION_VIEW)
                    instagramIntent.data = Uri.parse(url)
                    startActivity(instagramIntent)
                    true
                }
                else -> false
            }
        }

    }




    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.meInfoSubtitle.startAnimation(fadeInAnimation)
            binding.meInfoSubtitle.visibility = View.VISIBLE
        }, 1111)

    }


    override fun onBackPressed()
    {
        finish()
    }

}