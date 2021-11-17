package iot.empiaurhouse.triage.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Explode
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivitySetupBinding
import iot.empiaurhouse.triage.utils.SetupVerify
import iot.empiaurhouse.triage.utils.TypeWriterTextView
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import iot.empiaurhouse.triage.viewmodel.SetupActivityViewModel

class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding
    private lateinit var fadeInAnimation : Animation
    private lateinit var typeText : TypeWriterTextView
    private lateinit var chironVerify : SetupVerify
    private lateinit var setupActivityViewModel: SetupActivityViewModel
    private lateinit var userManager: UserPreferenceManager
    private var intentValue:String? = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setupActivityViewModel = ViewModelProvider(this)[SetupActivityViewModel::class.java]
        userManager = UserPreferenceManager(this)
        setupActivityViewModel.pingServer()
        val viewSetup = binding.root
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fader)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Explode()
            enterTransition.duration = 1000


        }
        setContentView(viewSetup)
        fetchPUID()
        chironVerify = SetupVerify()

    }


    private fun serverPing(): Boolean{
        var result = false
        setupActivityViewModel.serverStatus.observe(this, androidx.lifecycle.Observer{reply ->
            reply?.let{
                result = reply.isNotEmpty()
                println("Setup response object is not empty: " + reply.isNotEmpty().toString())
                println("See response result: $reply")

            }
        })
        return result
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun proceedButton(view: View) {
        if (chironVerify.verifyID(binding.setupUsername, binding.chironIDNote)
            && (chironVerify.verifyURL(binding.setupServerUrl, binding.chironURLNote)
                    || chironVerify.verifyIP(binding.setupServerUrl, binding.chironURLNote))){
                        //ping Chiron URL before intent
            if(chironVerify.chironConnect(this, serverPing())){
                userManager.storeUserData(binding.setupUsername.text.toString(), binding.setupServerUrl.text.toString(),
                "","${binding.setupUsername.text}::${binding.setupServerUrl.text}")
                startActivity(Intent(this@SetupActivity, HubActivity::class.java))
            }
            else if(!chironVerify.chironConnect(this, serverPing())){
                errorNoteSnackBar(view)
            }

        }

    }

    override fun onBackPressed()
    {
        moveTaskToBack(true)
        binding.wiredUpText.visibility = View.INVISIBLE
        binding.setupChironTitle.visibility = View.INVISIBLE
        binding.setupChironTitle.clearAnimation()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun errorNoteSnackBar(view: View){
        val connectErrorNote = Snackbar.make(view,"Hmm, ${binding.setupServerUrl.text} wasn't " +
                "reachable! Please confirm API availability " +
                "and enter a valid address", Snackbar.LENGTH_LONG)
        val errorNoteView = connectErrorNote.view
            errorNoteView.layoutParams
        val errorNoteText = errorNoteView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        errorNoteView.setBackgroundColor(getColor(R.color.chiron_blue))
        errorNoteText.setTextColor(getColor(R.color.white))
        val fontFace = resources.getFont(R.font.montserratlight)
        errorNoteText.typeface = fontFace
        errorNoteText.maxLines = 4
        connectErrorNote.show()

    }


    private fun fetchPUID(){
        intentValue = intent.getStringExtra("chironPUID")
        if (!intentValue.isNullOrEmpty()){
            binding.setupUsername.setText(intentValue.toString())
        }
    }


}