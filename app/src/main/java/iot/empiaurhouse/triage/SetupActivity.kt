package iot.empiaurhouse.triage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iot.empiaurhouse.triage.databinding.ActivitySetupBinding

class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        val viewSetup = binding.root
        setContentView(viewSetup)
    }
}