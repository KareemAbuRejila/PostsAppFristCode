package ps.kareemaburejila.postsappfristcode.data

import okhttp3.ResponseBody
import ps.kareemaburejila.postsappfristcode.models.PostModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val BASE_URL="https://jsonplaceholder.typicode.com/"
    private var postInterface: PostInterface?=null
    private var apiClientInstant : ApiClient?=null

    constructor(){
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postInterface=retrofit.create(PostInterface::class.java)
    }

    fun getInstance(): ApiClient {
        if (apiClientInstant==null)
            apiClientInstant= ApiClient()
        return apiClientInstant as ApiClient
    }
    fun getPosts():Call<List<PostModel?>?>?{
        return postInterface!!.getPosts()
    }
    fun deletePost(postID:Int):Call<ResponseBody>{
        return postInterface!!.deletePost(postID = postID)
    }
    fun editPost(postID:Int):Call<ResponseBody>{
        return postInterface!!.editPost(postID = postID)
    }
    fun addPost(postModel: PostModel):Call<ResponseBody>{
        return postInterface!!.addPost(postModel = postModel)
    }
}