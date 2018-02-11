package us.buddman.cheers.utils


/**
 * Created by Junseok Oh on 2017-07-18.
 */

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.util.Pair
import com.google.gson.Gson
import us.buddman.cheers.AppController
import us.buddman.cheers.models.User



class CredentialsManager private constructor() {
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val context: Context = AppController.context

    val activeUser: Pair<Boolean, User>
        get() {
            if (preferences.getBoolean(HAS_ACTIVE_USER, false)) {
                val user = Gson().fromJson(preferences.getString(USER_SCHEMA, ""), User::class.java)
                return Pair.create(true, user)
            } else
                return Pair.create(false, null)
        }

    val isFirst: Boolean
        get() = preferences.getBoolean("IS_FIRST", true)

    init {
        preferences = context.getSharedPreferences("Cheers", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun save(key: String, data: String) {
        editor.putString(key, data)
        editor.apply()
    }

    fun saveUserInfo(user: User) {
        editor.putString(USER_SCHEMA, Gson().toJson(user))
        editor.putBoolean(HAS_ACTIVE_USER, true)
        editor.apply()
    }

    fun removeAllData() {
        editor.clear()
        editor.apply()
    }

    fun getString(key: String): String {
        return preferences.getString(key, "")
    }

    fun getInt(key: String): Int {
        return preferences.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun notFirst() {
        editor.putBoolean("IS_FIRST", false)
        editor.apply()
    }

    fun getLong(key: String): Long {
        return preferences.getLong(key, 0)
    }

    companion object {

        /* Login Type
    * 0 Facebook
    * 1: Native
    * */

        /* Data Keys */
        private val USER_SCHEMA = "user_schema"
        private val HAS_ACTIVE_USER = "hasactive"
        internal var manager: CredentialsManager? = null

        val instance: CredentialsManager
            get() {
                if (manager == null) manager = CredentialsManager()
                return manager as CredentialsManager
            }
    }

}