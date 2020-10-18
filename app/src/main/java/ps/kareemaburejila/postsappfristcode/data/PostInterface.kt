package ps.kareemaburejila.postsappfristcode.data

import okhttp3.ResponseBody
import ps.kareemaburejila.postsappfristcode.models.PostModel
import retrofit2.Call
import retrofit2.http.*

interface PostInterface {
    @GET("photos")
    fun getPosts(): Call<List<PostModel?>?>?

    @DELETE("photos/{id}")
    fun deletePost(@Path("id")postID:Int):Call<ResponseBody>

    @PATCH("photos/{id}")
    fun editPost(@Path("id")postID:Int):Call<ResponseBody>

    @POST("photos")
    fun addPost(@Body postModel: PostModel):Call<ResponseBody>
}