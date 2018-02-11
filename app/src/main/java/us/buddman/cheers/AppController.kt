package us.buddman.cheers

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Chad Park on 2018-02-11.
 */
class AppController : Application(){
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Fresco.initialize(applicationContext)
    }

    companion object {
        lateinit var context: Context
    }
}