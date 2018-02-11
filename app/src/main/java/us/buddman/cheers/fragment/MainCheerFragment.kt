package us.buddman.cheers.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_main_cheer.*
import kotlinx.android.synthetic.main.fragment_main_dashboard.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import us.buddman.cheers.BR
import us.buddman.cheers.R
import us.buddman.cheers.activity.YoutubeShowActivity
import us.buddman.cheers.databinding.*
import us.buddman.cheers.models.Nation
import us.buddman.cheers.models.NationHeader
import us.buddman.cheers.models.News
import us.buddman.cheers.models.Sport
import us.buddman.cheers.utils.ErrorHelper
import us.buddman.cheers.utils.NetworkHelper
import java.util.*

/**
 * Created by Junseok on 2017-09-21.
 */
class MainCheerFragment : Fragment() {

    var dataArray = ArrayList<Any>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_main_cheer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainCheerRV.layoutManager = LinearLayoutManager(context)
        mainCheerRV.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
        Collections.addAll(dataArray, "이전 경기 모음",
                Sport(R.drawable.snowboard, "봅슬레이", "남자 2인승", "대한민국 | 동메달"),
                Sport(R.drawable.nordic, "노르딕 복합", "노멀힐 개인 10km 스키점프", "대한민국 | 4위"),
                Sport(R.drawable.bopslay, "스노보드", "여자 슬로프 스타일", "대한민국 | 8위")
        )
        configureRV()
        liveContainer.setOnClickListener {
            NetworkHelper.networkInstance.getYoutubeId().enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    ErrorHelper.logError(t!!.localizedMessage)
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    when (response!!.code()) {
                        200 -> {
                            startActivity(Intent(context, YoutubeShowActivity::class.java).putExtra("videoId", response.body()!!.string()
                            ))
                        }
                    }
                }
            })
        }
    }

    fun configureRV() {
        LastAdapter(dataArray, BR.content)
                .map<String, ContentHeaderBinding>(R.layout.content_header) {
                    onBind { }
                }
                .map<Sport, ContentCheerBinding>(R.layout.content_cheer) {
                    onBind {
                        it.binding.contentImage.setImageResource((dataArray[it.layoutPosition] as Sport).icon)
                    }
                }
                .into(mainCheerRV)
    }

}