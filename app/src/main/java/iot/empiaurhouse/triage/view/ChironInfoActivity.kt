package iot.empiaurhouse.triage.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Slide
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import iot.empiaurhouse.triage.databinding.ActivityChironInfoBinding
import iot.empiaurhouse.triage.model.APIStatus
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import iot.empiaurhouse.triage.viewmodel.InitViewModel
import java.util.*


class ChironInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChironInfoBinding
    private lateinit var initViewModel: InitViewModel
    private lateinit var userManager: UserPreferenceManager
    private lateinit var serverInfo: APIStatus
    private lateinit var result: APIStatus



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChironInfoBinding.inflate(layoutInflater)
        initViewModel = ViewModelProvider(this).get(InitViewModel::class.java)
        initViewModel.pingServer(this)
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
        binding.chironInfoClose.setOnClickListener {
            finish()
        }

        binding.chironInfoButton.setOnClickListener {
            val url = "https://github.com/emeraldemperaur/chiron"
            val gitIntent = Intent(Intent.ACTION_VIEW)
            gitIntent.data = Uri.parse(url)
            startActivity(gitIntent)
        }
        renderServerInfo()


    }

    private fun renderServerInfo(){
        val host = "LOCAL HOST: ${userManager.getServerHost()}"
        val sign = "SIGNATURE: ${userManager.getServerSign()}"
        binding.serverStatus.text = userManager.getServerStatus()?.capitalize(Locale.ROOT)
        binding.serverLocalhost.text = host
        binding.serverSignature.text = sign
    }



    override fun onBackPressed()
    {
        finish()
    }

}