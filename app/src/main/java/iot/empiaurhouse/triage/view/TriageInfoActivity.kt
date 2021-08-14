package iot.empiaurhouse.triage.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Slide
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.databinding.ActivityTriageInfoBinding
import iot.empiaurhouse.triage.utils.UserPreferenceManager

class TriageInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTriageInfoBinding
    private lateinit var userManager: UserPreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTriageInfoBinding.inflate(layoutInflater)
        userManager = UserPreferenceManager(this)
        val viewSetup = binding.root
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Slide()
            enterTransition.duration = 769

        }
        setContentView(viewSetup)
        initView()
    }

    private fun initView(){
        fetchUUID()
        binding.triageInfoClose.setOnClickListener {
            finish()
        }

        binding.triageInfoButton.setOnClickListener {
            val url = "https://github.com/emeraldemperaur/Triage"
            val gitIntent = Intent(Intent.ACTION_VIEW)
            gitIntent.data = Uri.parse(url)
            startActivity(gitIntent)
        }
    }


    fun fetchUUID(){
        val charset = Charsets.UTF_8
        val bytes = userManager.getChironID()?.toByteArray(charset)
        binding.serverUuidText.text = bytes.contentToString()

    }


    override fun onBackPressed()
    {
        finish()
    }

}