package ps.kareemaburejila.postsapp.ui.mainactivity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ps.kareemaburejila.postsappfristcode.data.ApiClient
import ps.kareemaburejila.postsappfristcode.models.DataSourceOffline
import ps.kareemaburejila.postsappfristcode.models.PostModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val postsMDL=MutableLiveData<List<PostModel?>?>()
    private var context:Context?=null

    fun getInstance(context: Context){
        this.context=context
    }
    fun getPosts(){
        ApiClient().getInstance().getPosts()!!.enqueue(object : Callback<List<PostModel?>?> {
            val dataSourceOffline= DataSourceOffline(context!!)
            override fun onResponse(
                call: Call<List<PostModel?>?>,
                response: Response<List<PostModel?>?>
            ) {
                postsMDL.value = response.body()
                dataSourceOffline.dropAll()
                dataSourceOffline.addAllPost(response.body()!!)
            }

            override fun onFailure(call: Call<List<PostModel?>?>, t: Throwable) {
                postsMDL.value = dataSourceOffline.allPosts
            }
        })
    }

}