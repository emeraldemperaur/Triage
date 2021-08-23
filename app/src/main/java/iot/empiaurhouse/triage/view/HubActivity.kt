package iot.empiaurhouse.triage.view

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import iot.empiaurhouse.triage.R
import iot.empiaurhouse.triage.databinding.ActivityHubBinding
import iot.empiaurhouse.triage.model.ChironRecords
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
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var hubNavController: NavController
    private lateinit var hubNavHostFragment: NavHostFragment
    private lateinit var hubDrawer: DrawerLayout
    private lateinit var navView: View
    var recordsFound = arrayListOf<ChironRecords>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHubBinding.inflate(layoutInflater)
        val viewSetup = binding.root
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale)
        setContentView(viewSetup)
        userManager = UserPreferenceManager(this)
        hubMenu = binding.hubToolbar.menu
        navigationView = binding.hubDrawerNavView
        hubDrawer = binding.hubDrawerView
        bottomNavigation = binding.hubFootNav
        initSideOptionsMenu()
        initView()
        offSetFX()
        //ListStore.cacheRecordsList(recordsFound)
        hideOption(0)
        hideOption(1)
        hubNavHostFragment = supportFragmentManager.findFragmentById(R.id.hub_nav_host_fragment) as NavHostFragment
        hubNavController = hubNavHostFragment.navController
        bottomNavigation.setupWithNavController(hubNavController)


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
                    binding.hubToolbar.navigationIcon = ResourcesCompat.getDrawable(resources,
                        R.drawable.ic_baseline_account_circle_24, baseContext.theme)
                } else if (isShow) {
                    isShow = false
                    hideOption(0)
                    hideOption(1)
                    binding.hubToolbar.navigationIcon = null


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
        navView = navigationView.getHeaderView(0)
        val chironID: TextView = navView.findViewById(R.id.chiron_id_sidemenu)
        val serverUrlView: TextView = navView.findViewById(R.id.chiron_serverinfo_sidemenu)
        val timeZone: TextView = navView.findViewById(R.id.triage_timezone_sidemenu)
        chironID.text = displayID
        serverUrlView.text = sliceURL(serverUrl)
        timeZone.text = fetchTZ()
        val toolbar = binding.hubToolbar
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(null)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.hub_toolmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                hubDrawer.openDrawer(GravityCompat.START)
            }

            R.id.app_bar_chiron -> {
                val url = serverIntentUrl
                val optionIntent = Intent(Intent.ACTION_VIEW)
                optionIntent.data = Uri.parse(url)
                startActivity(optionIntent)
            }
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


//    private fun fetchRecordsData(): ArrayList<ChironRecords>{
//        var result: Boolean
//        val fetchedRecords = arrayListOf<ChironRecords>()
//        recordsViewModel.serverStatus.observe(this, androidx.lifecycle.Observer{reply ->
//            reply?.let{
//                result = reply.isNotEmpty()
//                fetchedRecords.addAll(reply)
//                println("Records response object is not empty: $result")
//                println("See Chiron Records response result: $reply")
//
//            }
//        })
//
//        return fetchedRecords
//    }





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
            slicedUrl = slicedUrl.substring(0, strLength)
        }
        return slicedUrl
    }


    private fun fetchTZ(): String {
        val tz: TimeZone = TimeZone.getDefault()
        println("Client Device Time Zone: " + tz.getDisplayName(Locale.ENGLISH))
        return tz.getDisplayName(Locale.ENGLISH)
    }







}