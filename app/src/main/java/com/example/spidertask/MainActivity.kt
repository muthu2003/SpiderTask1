package com.example.spidertask

import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.LF_Activity
import com.example.spidertask.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private var spiValue : Double = 1.234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val vibe: Vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        binding.lfButton.setOnClickListener {
            vibe.vibrate(100)
            val intent = Intent(this, LF_Activity::class.java)
            startActivity(intent)
            binding.showSpi.isVisible = false
        }

        binding.spiFactor.setOnClickListener {
            val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"))

            val hour: Int = cal.get(Calendar.HOUR)
            val minute: Int = cal.get(Calendar.MINUTE)
            val second: Int = cal.get(Calendar.SECOND)

            spiValue = fact(hour)/((minute * minute * minute).toDouble() + second.toDouble())

            binding.showSpi.isVisible = true
            binding.showSpi.text = " Time :$hour:$minute:$second\nSpi factor : $spiValue"
            vibe.vibrate(100)
        }
    }

    private fun fact(tH: Int): Long {
        if (tH == 0) {
            return 1
        } else {
            var newHour: Long = tH.toLong()
            for (item in (1..tH)) {
                newHour *= item
            }
            return newHour
        }
    }
}