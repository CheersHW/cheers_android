package us.buddman.cheers.utils

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import us.buddman.cheers.models.Nation
import us.buddman.cheers.models.News
import us.buddman.cheers.models.Sound
import us.buddman.cheers.models.User

/**
 * Created by Chad Park on 2018-02-11.
 */
public interface NetworkInterface {

    @GET("/ranking")
    fun getRanking(): Call<ArrayList<Nation>>

    @GET("/youtube")
    fun getYoutubeId(): Call<ResponseBody>

    @GET("/news")
    fun getNews(): Call<ArrayList<News>>

    @POST("/aid/rank")
    fun getCheerRank(): Call<ArrayList<Nation>>


    @POST("/auth/login")
    @FormUrlEncoded
    fun loginByLocal(@Field("email") email: String, @Field("password") password: String): Call<User>

    @POST("/auth/register")
    @FormUrlEncoded
    fun registerByLocal(
            @Field("username") username: String,
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("pe") pe: Int,
            @Field("region") region: Int
    ): Call<User>

    @POST("/auth/edituser")
    @FormUrlEncoded
    fun editUser(
            @Field("username") username: String,
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("pe") pe: Int,
            @Field("region") region: Int
    ): Call<User>

    @POST("/auth/auto")
    @FormUrlEncoded
    fun autoLogin(
            @Field("user_token") user_token: String
    ): Call<User>

    @Multipart
    @POST("/aid/upload")
    fun newCheerLine(
            @Part("file\"; filename=\"file.mp3\" ") file: RequestBody,
            @Part("region") region: RequestBody,
            @Part("title") title: RequestBody,
            @Part("user_token") user_token: RequestBody,
            @Part("username") username: RequestBody,
            @Part("text") text: RequestBody
    ): Call<ResponseBody>

    @POST("/aid/like")
    @FormUrlEncoded
    fun likeCheerLine(
            @Field("sound_token") sound_token: String,
            @Field("user_token") user_token: String
    ): Call<User>

    @POST("/aid/dislike")
    @FormUrlEncoded
    fun dislikeCheerLine(
            @Field("sound_token") sound_token: String,
            @Field("user_token") user_token: String
    ): Call<User>

    @POST("/aid/list")
    @FormUrlEncoded
    fun getCheerLineList(
            @Field("region") region: Int
    ): Call<ArrayList<Sound>>


}