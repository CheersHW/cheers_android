package us.buddman.cheers.utils

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Junseok Oh on 2017-08-02.
 */

object NetworkHelper {
    private val url = "http://soylatte.kr"
    private val port = 3000

    private var retrofit: Retrofit? = null

    val networkInstance: NetworkInterface
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(url + ":" + port)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!.create<NetworkInterface>(NetworkInterface::class.java)
        }

    fun returnNetworkState(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}
