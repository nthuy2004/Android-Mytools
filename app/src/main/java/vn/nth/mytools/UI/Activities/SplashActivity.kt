package vn.nth.mytools.UI.Activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import vn.nth.mytools.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}