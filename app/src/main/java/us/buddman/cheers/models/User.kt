package us.buddman.cheers.models

/**
 * Created by Chad Park on 2018-02-11.
 */
data class User(
        val username : String,
        val email : String,
        val password: String,
        val region : Int,
        val pe : Int,
        val user_token : String
)