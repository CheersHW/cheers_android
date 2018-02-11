package us.buddman.cheers.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import us.buddman.cheers.R
import android.widget.Toast
import com.google.android.youtube.player.internal.e
import okhttp3.ResponseBody
import android.content.Intent
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import us.buddman.cheers.models.User
import us.buddman.cheers.utils.BaseActivity
import us.buddman.cheers.utils.NetworkHelper


class RegisterActivity : BaseActivity() {
    override val viewId: Int = R.layout.activity_register
    override val toolbarId: Int = 0

    override fun setDefault() {
        register.setOnClickListener {
            if (isNotEmpty(emailInput, passwordInput, nameInput)) {
                NetworkHelper.networkInstance.registerByLocal(
                        nameInput.text.toString().trim(),
                        emailInput.text.toString().trim(),
                        passwordInput.text.toString().trim(), 0, 1).enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<User>?, response: Response<User>?) {
                        when (response!!.code()!!) {
                            200 -> {
                                Toast.makeText(this@RegisterActivity, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(applicationContext, LoginActivity::class.java))
                                finish()
                            }
                            409 -> {
                                Toast.makeText(this@RegisterActivity, "이미 아이디가 존재합니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })

            } else
                Toast.makeText(this@RegisterActivity, "빈칸 없이 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    fun isNotEmpty(vararg editTexts: EditText): Boolean {
        return editTexts.none { it.text.toString().trim() == "" }
    }


}
