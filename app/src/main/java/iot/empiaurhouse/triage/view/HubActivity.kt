package iot.empiaurhouse.triage.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.R

class HubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale)
        setContentView(R.layout.activity_hub)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.hub_toolmenu, menu)
        return true
    }

}