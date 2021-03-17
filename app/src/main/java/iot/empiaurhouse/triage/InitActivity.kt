package iot.empiaurhouse.triage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        val viewInit = binding.root
        setContentView(viewInit)
    }
}