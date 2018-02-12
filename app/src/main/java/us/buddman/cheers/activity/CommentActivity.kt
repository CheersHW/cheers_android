package us.buddman.cheers.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import us.buddman.cheers.R
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_comment.*


class CommentActivity : AppCompatActivity() {

    var commentArray = arrayListOf(
            "금산에서 응원해요 화이팅",
            "저희 여당도 올해 올림픽 응원합니다",
            "이걸 보니 암이 나았어요!",
            "저 시한부인데 이 올림픽을 보고 3개월 더 살수 있어요!",
            "우와 너무 잘한다..."
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commentArray)
        commentRV.adapter = adapter
    }
}
