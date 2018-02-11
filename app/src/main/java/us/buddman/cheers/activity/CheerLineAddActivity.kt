package us.buddman.cheers.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cheer_line_add.*
import us.buddman.cheers.R
import android.content.Intent
import android.app.Activity
import android.net.Uri
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import us.buddman.cheers.utils.CredentialsManager
import us.buddman.cheers.utils.ErrorHelper
import us.buddman.cheers.utils.NetworkHelper
import java.io.File

class CheerLineAddActivity : AppCompatActivity() {

    var uploadUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheer_line_add)

        mp3Select.setOnClickListener {
            val intent: Intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "audio/mp3"
            startActivityForResult(Intent.createChooser(intent, "파일을 선택해주세요"), 6974)
        }
        upload.setOnClickListener {
            NetworkHelper.networkInstance.newCheerLine(
                    RequestBody.create(MediaType.parse("audio/mp3"), File(uploadUri!!.path)),
                    RequestBody.create(MediaType.parse("text/plain"), "1"),
                    RequestBody.create(MediaType.parse("text/plain"), titleInput.text.toString()),
                    RequestBody.create(MediaType.parse("text/plain"), CredentialsManager.instance.activeUser.second!!.user_token),
                    RequestBody.create(MediaType.parse("text/plain"), CredentialsManager.instance.activeUser.second!!.username),
                    RequestBody.create(MediaType.parse("text/plain"), "")
            ).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    ErrorHelper.logError(t!!.localizedMessage)
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    when (response!!.code()!!) {
                        200 -> {
                            Toast.makeText(applicationContext, "등록되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }

            })
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 6974 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                uploadUri = data.data
            }
        }
    }
}
