package iot.empiaurhouse.triage.view

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivityHubBinding
import iot.empiaurhouse.triage.utils.UserPreferenceManager
import kotlinx.android.synthetic.main.activity_hub.view.*
import kotlinx.android.synthetic.main.side_menu_top_view.view.*
import java.util.*


class HubActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHubBinding
    private lateinit var userManager: UserPreferenceManager
    private lateinit var displayID: String
    private var serverUrl = ""
    private var serverIntentUrl = ""
    private var userPUID = ""
    private val strLength = 24
    private lateinit var hubMenu: Menu
    private lateinit var pivotsTitle: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHubBinding.inflate(layoutInflater)
        val viewSetup = binding.root
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale)
        setContentView(viewSetup)
        userManager = UserPreferenceManager(this)
        hubMenu = binding.hubToolbar.menu
        initSideOptionsMenu()
        fetchTZ()
        initView()
        offSetFX()
        hideOption(0)
        hideOption(1)

    }






    override fun onResume() {
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
    }


    override fun onBackPressed()
    {
        moveTaskToBack(true)
    }


    private fun offSetFX(){
        val mAppBarLayout = findViewById<View>(R.id.hub_app_bar) as AppBarLayout
        mAppBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    showOption(0)
                    showOption(1)
                } else if (isShow) {
                    isShow = false
                    hideOption(0)
                    hideOption(1)


                }
            }
        })
    }


    private fun initView(){
        serverUrl = userManager.getServerUrl().toString()
        userPUID = userManager.getUserID().toString()
        serverIntentUrl = prepURL(serverUrl)
        displayID = userPUID.substringBefore("@")
        if (displayID.length > strLength) {
            displayID = userPUID.substring(0, strLength);
        }
        val greeting = "Hello, $displayID!"
        binding.hubUsernameTitle.text = greeting
        invalidateOptionsMenu()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.hub_toolmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.app_bar_chiron) {
            return true
        } else if (id == R.id.app_bar_search) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hideOption(itemId: Int) {

        hubMenu.getItem(itemId).isVisible = false
    }

    private fun showOption(itemId: Int) {
        hubMenu.getItem(itemId).isVisible = true
    }

    private fun initSideOptionsMenu(){

        val userid = binding.hubDrawerNavView.hub_drawer_nav_view.chiron_id_sidemenu
        val clientServer = binding.hubDrawerNavView.hub_drawer_nav_view.chiron_serverinfo_sidemenu
        val clientTZ = binding.hubDrawerNavView.triage_timezone_sidemenu
        binding.hubDrawerNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.launch_chiron -> {
                    val url = serverIntentUrl
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    true
                }
                R.id.about_chiron -> {
                    startActivity(
                        Intent(this@HubActivity, ChironInfoActivity::class.java),
                        ActivityOptions.makeSceneTransitionAnimation(this@HubActivity).toBundle()
                    )
                    true
                }
                R.id.about_triage -> {
                    startActivity(
                        Intent(this@HubActivity, TriageInfoActivity::class.java),
                        ActivityOptions.makeSceneTransitionAnimation(this@HubActivity).toBundle()
                    )
                    true
                }
                R.id.about_me -> {
                    startActivity(
                        Intent(this@HubActivity, MEActivity::class.java),
                        ActivityOptions.makeSceneTransitionAnimation(this@HubActivity).toBundle()
                    )
                    true
                }
                else -> false
            }
        }
    }




    private fun prepURL(urlString: String): String{
        var preppedUrlString = ""
        if(!urlString.startsWith("http://") || !urlString.startsWith("https://")){
           preppedUrlString = "https://$urlString"
        }
        return preppedUrlString
    }

    private fun sliceURL(urlString: String): String{
        var slicedUrl = ""
        slicedUrl = urlString.substringAfter("https://")
        slicedUrl = slicedUrl.substringAfter("http://")
        slicedUrl = slicedUrl.substringAfter("www.")
        slicedUrl = slicedUrl.substringBefore(".")
        if (slicedUrl.length > strLength) {
            slicedUrl = slicedUrl.substring(0, strLength);
        }
        return slicedUrl
    }


    private fun fetchTZ(): String {
        val tz: TimeZone = TimeZone.getDefault()
        println("Client Device Time Zone: " + tz.getDisplayName(Locale.ENGLISH))
        return tz.getDisplayName(Locale.ENGLISH)
    }


}