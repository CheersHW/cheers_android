package us.buddman.cheers.activity

import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import us.buddman.cheers.R
import us.buddman.cheers.models.User
import us.buddman.cheers.utils.BaseActivity
import us.buddman.cheers.utils.CredentialsManager
import us.buddman.cheers.utils.ErrorHelper
import us.buddman.cheers.utils.NetworkHelper

class LoginActivity : BaseActivity() {
    override val viewId: Int = R.layout.activity_login
    override val toolbarId: Int = 0

    override fun setDefault() {
        login.setOnClickListener {
            if (isNotEmpty(emailInput, passwordInput)) {
                NetworkHelper.networkInstance.loginByLocal(
                        emailInput.text.toString().trim(),
                        passwordInput.text.toString().trim()).enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        ErrorHelper.logError(t!!.localizedMessage)
                    }

                    override fun onResponse(call: Call<User>?, response: Response<User>?) {
                        when (response!!.code()!!) {
                            200 -> {
                                Toast.makeText(this@LoginActivity, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                                CredentialsManager.instance.saveUserInfo(response.body()!!)
                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                finish()
                            }
                            else -> {
                                Toast.makeText(this@LoginActivity, "아이디 혹은 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })

            } else
                Toast.makeText(this@LoginActivity, "빈칸 없이 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    fun isNotEmpty(vararg editTexts: EditText): Boolean {
        for (e in editTexts) {
            if (e.text.toString().trim() == "") return false
        }
        return true
    }


}