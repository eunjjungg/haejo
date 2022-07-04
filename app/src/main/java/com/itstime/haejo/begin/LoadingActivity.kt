package com.itstime.haejo.begin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.itstime.haejo.BeginActivity
import com.itstime.haejo.MainActivity
import com.itstime.haejo.databinding.ActivityLoadingBinding
import com.itstime.haejo.util.AppSetting

//splash view, Lottie 사용 (참고: https://stickode.tistory.com/67)
class LoadingActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoadingBinding
    var userName : String = "null"
    var userEmail : String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoadingBinding.inflate(layoutInflater)


        //loading animation setting
        //var imgLoading  = binding.imgLoading as LottieAnimationView
        getEmailName()
        //imgLoading.playAnimation()

        //2 sec loading
        val handler: Handler = Handler()
        handler.postDelayed({
            var intent: Intent
            if(userEmail == "null") {
                intent = Intent(this, BeginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)

        setContentView(binding.root)
    }



    private fun getEmailName() {
        userEmail = AppSetting.prefs.getEmail()
        userName = AppSetting.prefs.getName()
    }
}