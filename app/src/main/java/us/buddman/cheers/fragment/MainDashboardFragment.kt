package us.buddman.cheers.fragment

import android.content.Intent
import android.net.Network
import android.net.Uri
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_main_dashboard.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import us.buddman.cheers.BR
import us.buddman.cheers.R
import us.buddman.cheers.activity.YoutubeShowActivity
import us.buddman.cheers.databinding.ContentHeaderBinding
import us.buddman.cheers.databinding.ContentHeaderOlympicMedalBinding
import us.buddman.cheers.databinding.ContentNewsBinding
import us.buddman.cheers.databinding.ContentOlympicMedalBinding
import us.buddman.cheers.models.Nation
import us.buddman.cheers.models.NationHeader
import us.buddman.cheers.models.News
import us.buddman.cheers.utils.ErrorHelper
import us.buddman.cheers.utils.NetworkHelper
import java.util.*

/**
 * Created by Junseok on 2017-09-21.
 */
class MainDashboardFragment : Fragment() {

    var bindingArray = ArrayList<Any>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_main_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainDashBoardRV.layoutManager = LinearLayoutManager(context)
        mainDashBoardRV.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
        fetchCheerMedal()
        mainLiveContainer.setOnClickListener {
            NetworkHelper.networkInstance.getYoutubeId().enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    ErrorHelper.logError(t!!.localizedMessage)
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    when (response!!.code()) {
                        200 -> {
                            startActivity(Intent(context, YoutubeShowActivity::class.java).putExtra("videoId", response.body()!!.string()))
                        }
                    }
                }
            })
        }
    }

    fun fetchCheerMedal() {
        NetworkHelper.networkInstance.getCheerRank().enqueue(object : Callback<ArrayList<Nation>> {
            override fun onFailure(call: Call<ArrayList<Nation>>?, t: Throwable?) {
                ErrorHelper.logError(t!!.localizedMessage)
            }

            override fun onResponse(call: Call<ArrayList<Nation>>, response: Response<ArrayList<Nation>>) {
                when (response.code()) {
                    200 -> {
                        bindingArray.add(getKorea(response.body()!!, 0)!!)
                        for (nation in response.body()!!) {
                            bindingArray.add(nation)
                        }
                        fetchOlympicsMedal()
                    }
                }
            }

        })
    }

    fun fetchOlympicsMedal() {
        NetworkHelper.networkInstance.getRanking().enqueue(object : Callback<ArrayList<Nation>> {
            override fun onFailure(call: Call<ArrayList<Nation>>?, t: Throwable?) {
                ErrorHelper.logError(t!!.localizedMessage)
            }

            override fun onResponse(call: Call<ArrayList<Nation>>, response: Response<ArrayList<Nation>>) {
                when (response.code()) {
                    200 -> {
                        bindingArray.add(getKorea(response.body()!!, 1)!!)
                        for (nation in response.body()!!) {
                            bindingArray.add(nation)
                        }
                        fetchNews()
                    }
                }
            }

        })
    }

    fun fetchNews() {
        NetworkHelper.networkInstance.getNews().enqueue(object : Callback<ArrayList<News>> {
            override fun onFailure(call: Call<ArrayList<News>>?, t: Throwable?) {
                ErrorHelper.logError(t!!.localizedMessage)
            }

            override fun onResponse(call: Call<ArrayList<News>>, response: Response<ArrayList<News>>) {
                when (response.code()) {
                    200 -> {
                        bindingArray.add("뉴스 소식")
                        for (news in response.body()!!) {
                            bindingArray.add(news)
                        }
                        configureRV()
                    }
                }
            }

        })
    }

    fun configureRV() {
        LastAdapter(bindingArray, BR.content)
                .map<NationHeader, ContentHeaderOlympicMedalBinding>(R.layout.content_header_olympic_medal) {
                    onBind { }
                }
                .map<Nation, ContentOlympicMedalBinding>(R.layout.content_olympic_medal) {

                }
                .map<String, ContentHeaderBinding>(R.layout.content_header) {
                    onBind { }
                }
                .map<News, ContentNewsBinding>(R.layout.content_news) {
                    onBind {
                        it.binding.networkImage.setImageURI(
                                Uri.parse((bindingArray[it.layoutPosition] as News).photo)
                                , this)
                    }
                    onClick {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse((bindingArray[it.layoutPosition] as News).link)))
                    }
                }
                .into(mainDashBoardRV)

    }

    fun getKorea(nationArray: ArrayList<Nation>, type: Int): NationHeader? {
        nationArray
                .filter { it.country == "대한민국" }
                .forEach {
                    return NationHeader(if (type == 0) "응원메달 현황" else "올림픽 메달 현황", it.country, it.gold, it.silver, it.bronze, it.rank)
                }
        return null
    }
}