package us.buddman.cheers.models

/**
 * Created by Chad Park on 2018-02-11.
 */
data class Sound(
        val region : String,
        val title : String,
        val author_token : String,
        val author_name : String,
        val path : String,
        val text : String,
        val like : Int,
        val like_user : ArrayList<String>,
        val sound_token : String
)