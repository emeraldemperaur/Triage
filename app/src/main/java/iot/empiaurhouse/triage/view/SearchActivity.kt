package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivitySearchBinding
import iot.empiaurhouse.triage.utils.TypeWriterTextView
import iot.empiaurhouse.triage.utils.UserPreferenceManager

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var userManager: UserPreferenceManager
    private lateinit var serverTitle: TextView
    private lateinit var searchTitle: TypeWriterTextView
    private lateinit var searchSubTitle: TextView
    private lateinit var closeButton: ImageView
    private lateinit var searchFragment: FragmentContainerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
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
        serverTitle = binding.searchPatientServerTitle
        searchTitle = binding.searchPatientTitle
        searchSubTitle = binding.searchPatientSubtitle
        closeButton = binding.chironSearchClose
        searchFragment = binding.searchFragmentView
        val serverUrl = userManager.getServerUrl().toString()
        serverTitle.text = sliceURL(serverUrl)
        closeButton.setOnClickListener {
            finish()
        }

    }


    override fun onResume() {
        super.onResume()
        searchTitle.setCharacterDelay(190)
        searchTitle.displayTextWithAnimation(getString(R.string.search))
        searchTitle.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            searchSubTitle.visibility = View.VISIBLE
            serverTitle.visibility = View.VISIBLE
        }, 1000)

    }

    override fun onPause() {
        super.onPause()
        serverTitle.visibility = View.INVISIBLE
        searchTitle.visibility = View.INVISIBLE
        searchSubTitle.visibility = View.INVISIBLE
        searchTitle.clearAnimation()
    }

    override fun onBackPressed()
    {
        finish()
    }


    private fun sliceURL(urlString: String): String{
        var slicedUrl = ""
        val strLength = 24
        slicedUrl = urlString.substringAfter("https://")
        slicedUrl = slicedUrl.substringAfter("http://")
        slicedUrl = slicedUrl.substringAfter("www.")
        slicedUrl = slicedUrl.substringBefore(".")
        if (slicedUrl.length > strLength) {
            slicedUrl = slicedUrl.substring(0, strLength)
        }
        return slicedUrl
    }

}