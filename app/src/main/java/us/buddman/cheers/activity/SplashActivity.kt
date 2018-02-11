package us.buddman.cheers.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Response
import us.buddman.cheers.R
import us.buddman.cheers.models.User
import us.buddman.cheers.utils.CredentialsManager
import us.buddman.cheers.utils.NetworkHelper
import javax.security.auth.callback.Callback

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if(CredentialsManager.instance.activeUser.first!!){
            NetworkHelper.networkInstance.autoLogin(CredentialsManager.instance.activeUser.second!!.user_token).enqueue(object : retrofit2.Callback<User>{
                override fun onFailure(call: Call<User>?, t: Throwable?) {
                }

                override fun onResponse(call: Call<User>?, response: Response<User>?) {
                    when(response!!.code()){
                        200 -> {
                            Toast.makeText(this@SplashActivity, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                            CredentialsManager.instance.saveUserInfo(response.body()!!)
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finish()
                        }
                    }
                }

            })
        }
        login.setOnClickListener{
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()

        }
        register.setOnClickListener{
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            finish()

        }
    }
}
