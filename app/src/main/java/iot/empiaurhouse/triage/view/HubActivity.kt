package iot.empiaurhouse.triage.view

import android.os.Bundle
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

}