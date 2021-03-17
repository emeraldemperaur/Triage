package iot.empiaurhouse.triage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.databinding.ActivityLaunchBinding

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        val viewLaunch = binding.root
        setContentView(viewLaunch)
    }
}