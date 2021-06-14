package com

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.spidertask.databinding.ActivityLfBinding
import kotlin.math.sqrt

class LF_Activity : AppCompatActivity() {

    private var lfResult : Double = 0.0
    private val cValue: Long = 300000000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityLfBinding = ActivityLfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val vibe2: Vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val copyData : ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        binding.userResult.isVisible = false
        binding.check.isVisible = false

        binding.showLf.setOnClickListener {
            val speed = (binding.speedValue.text.toString()).toDoubleOrNull()
            if (speed == null) {
                vibe2.vibrate(100)
                Toast.makeText(this, "Please Enter the Speed!!", Toast.LENGTH_SHORT).show()
                binding.lfValue.text = ""
                binding.checkLf.isVisible = true
            }
            else
            {
                binding.lfValue.isVisible = true
                binding.checkLf.isVisible = false
                lfResult = 1 / sqrt(1 - ((speed * speed) / (cValue * cValue)))
                binding.lfValue.text = "Lorentz factor : $lfResult"
                binding.lfValue.setOnClickListener {
                    val clip = ClipData.newPlainText("label",binding.lfValue.text)
                    copyData.setPrimaryClip(clip)
                    Toast.makeText(this, "Copied to Clipboard",Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.checkLf.setOnClickListener {
            binding.userResult.isVisible = true
            binding.check.isVisible = true
            binding.showLf.isVisible = false

            binding.check.setOnClickListener {
                val speed = (binding.speedValue.text.toString()).toDoubleOrNull()
                val answer = (binding.userResult.text.toString()).toDoubleOrNull()
                if (speed == null||answer == null) {
                    vibe2.vibrate(100)
                    binding.lfValue.text = ""
                    Toast.makeText(this, "Please Enter the Values!!",Toast.LENGTH_SHORT).show()
                }
                else{
                    lfResult = 1 / sqrt(1 - ((speed * speed) / (cValue * cValue)))
                    if(lfResult == answer){
                        Toast.makeText(this, "Your Answer is Correct!!",Toast.LENGTH_SHORT).show()
                        binding.lfLayout.setBackgroundColor(Color.GREEN)
                    }else{
                        binding.lfValue2.isVisible = true
                        vibe2.vibrate(100)
                        Toast.makeText(this, "Wrong Answer :{",Toast.LENGTH_SHORT).show()
                        binding.lfValue2.text = "Lorentz Factor is $lfResult"
                        binding.lfLayout.setBackgroundColor(Color.RED)
                    }
                }
            }
        }

        binding.reset.setOnClickListener {
            binding.lfLayout.setBackgroundColor(Color.WHITE)
            binding.showLf.isVisible = true
            binding.checkLf.isVisible = true
            binding.check.isVisible = false
            binding.userResult.isVisible = false
            binding.lfValue.isVisible = false
            binding.lfValue2.isVisible = false
            binding.speedValue.text.clear()
            binding.userResult.text.clear()
        }
    }
}