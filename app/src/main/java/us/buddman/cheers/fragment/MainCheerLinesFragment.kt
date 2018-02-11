package us.buddman.cheers.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_main_cheerlines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import us.buddman.cheers.R
import us.buddman.cheers.activity.CheerLineAddActivity
import us.buddman.cheers.databinding.ContentCheerlineBinding
import us.buddman.cheers.models.Sound
import us.buddman.cheers.utils.NetworkHelper
import us.buddman.cheers.utils.PlayAudioManager

/**
 * Created by Junseok on 2017-09-21.
 */
class MainCheerLinesFragment : Fragment() {

    var soundArr = ArrayList<Sound>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_main_cheerlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = "나라별 응원 구호"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.commonDarkBlue))
        mainCheerLineRV.layoutManager = LinearLayoutManager(context)
        initRv()
        addLine.setOnClickListener {

            startActivity(Intent(context, CheerLineAddActivity::class.java))
        }

    }

    fun initRv() {
        NetworkHelper.networkInstance.getCheerLineList(1).enqueue(object : Callback<ArrayList<Sound>> {
            override fun onResponse(call: Call<ArrayList<Sound>>?, response: Response<ArrayList<Sound>>?) {
                soundArr = response!!.body()!!
                LastAdapter(soundArr, BR.content)
                        .map<Sound, ContentCheerlineBinding>(R.layout.content_cheerline) {
                            onBind {
                                val position = it.layoutPosition
                                it.binding.great.setOnClickListener {
                                    //                                    if(soundArr[position].like_user.contains())
                                }
                                it.binding.play.setOnClickListener {
                                    if (PlayAudioManager.isPlaying) {
                                        PlayAudioManager.killMediaPlayer()
                                        (it as ImageView).setImageResource(R.drawable.ic_play_circle_filled_white_24px)
                                    } else {
                                        PlayAudioManager.playAudio(context, soundArr[position].path)
                                        (it as ImageView).setImageResource(R.drawable.ic_pause_circle_filled_white_24px)
                                    }
                                }
                            }
                        }
                        .into(mainCheerLineRV)
            }

            override fun onFailure(call: Call<ArrayList<Sound>>?, t: Throwable?) {
            }

        })
    }
}