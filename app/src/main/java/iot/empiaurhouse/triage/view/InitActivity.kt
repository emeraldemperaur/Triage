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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wajahatkarim3.easyvalidation.core.view_ktx.textEqualTo
import com.wajahatkarim3.easyvalidation.core.view_ktx.textNotEqualTo
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivityInitBinding
import iot.empiaurhouse.triage.utils.TypeWriterTextView
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import iot.empiaurhouse.triage.viewmodel.DataPivotViewModel
import iot.empiaurhouse.triage.viewmodel.InitViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*


class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    private lateinit var fadeInAnimation : Animation
    private lateinit var typeText : TypeWriterTextView
    private lateinit var userManager: UserPreferenceManager
    private lateinit var initViewModel: InitViewModel
    private lateinit var pivotViewModel: DataPivotViewModel
    private var userPUID = ""
    private var serverUrl = ""


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        initViewModel = ViewModelProvider(this)[InitViewModel::class.java]
        initViewModel.pingServer(this)
        pivotViewModel = ViewModelProvider(this)[DataPivotViewModel::class.java]
        val app = this.application
        pivotViewModel.processPivot(app)
        urlPrep()
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
                    // perform check if user preferences found or not
                    if (fetchUserData()){
                        // do network test, if positive go to HUB.
                            if (serverTest()){
                                startActivity(Intent(this@InitActivity, HubActivity::class.java),
                                    ActivityOptions.makeSceneTransitionAnimation(this@InitActivity).toBundle())
                            }
                            else if (!serverTest()){
                                // If ping is negative; pass UserID via bundle to edittext on Setup View if URL not found
                                pushUserData()
                                // show toast notification to inform user of Chiron server unavailability
                                Toast.makeText(applicationContext,"Hmm, $serverUrl wasn't " +
                                        "reachable! Please confirm API availability " +
                                        "and enter a valid address",Toast.LENGTH_LONG).show()
                                userManager.clearUserData()
                            }

                    }
                    // direct user to Setup if UserData not found
                    else if (!fetchUserData()){
                        startActivity(Intent(this@InitActivity, SetupActivity::class.java),
                            ActivityOptions.makeSceneTransitionAnimation(this@InitActivity).toBundle())
                    }

                    finish()
                }, 3200)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })



    }

    private fun fetchUserData():Boolean{
        var userStatus = false
        userManager = UserPreferenceManager(this)
        userPUID = userManager.getUserID().toString()
        serverUrl = userManager.getServerUrl().toString()
        if ((userPUID.isBlank() || serverUrl.isBlank()) || (userPUID.textEqualTo("null")
                    || serverUrl.textEqualTo("null"))){
            userStatus = false
        }
        else if ((userPUID.isNotEmpty() && serverUrl.isNotEmpty())
            || (userPUID.textNotEqualTo("null") || serverUrl.textNotEqualTo("null"))){
            userStatus = true
        }
        return userStatus
    }


    private fun serverTest(): Boolean{
        var result: Boolean = false
        initViewModel.serverStatus.observe(this, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                println("Init response object is not empty: " + reply.isNotEmpty().toString())
                println("See response result: $reply")

            }
        })
        return result
    }

    private fun pushUserData(){
        val retryIntent = Intent(this, SetupActivity::class.java).apply {
            putExtra("chironPUID",userPUID)
        }
        val options = ActivityOptions.makeSceneTransitionAnimation(this)

        startActivity(retryIntent)

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


    private fun urlPrep(){
        userManager = UserPreferenceManager(this)
        serverUrl = userManager.getServerUrl().toString()
        println("\t\nThis is the init server Url found: " + serverUrl)
        if (serverUrl != null) {
            if (!serverUrl.endsWith("/")) {
                serverUrl = serverUrl.plus("/")
            }
            if (!serverUrl.startsWith("http://") && !serverUrl.startsWith("https://")) {
                serverUrl = "https://$serverUrl"
            }
            println("\t\nThis is the prepped server Url found: " + serverUrl)
        }
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