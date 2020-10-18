package ps.kareemaburejila.postsappfristcode.ui.mainactivity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import okhttp3.ResponseBody
import ps.kareemaburejila.postsapp.ui.mainactivity.MainViewModel
import ps.kareemaburejila.postsappfristcode.R
import ps.kareemaburejila.postsappfristcode.adapters.PostsAdapter
import ps.kareemaburejila.postsappfristcode.data.ApiClient
import ps.kareemaburejila.postsappfristcode.databinding.DialogAddPostBinding
import ps.kareemaburejila.postsappfristcode.init.StandardActivity
import ps.kareemaburejila.postsappfristcode.models.PostModel
import ps.kareemaburejila.postsappfristcode.ui.PostFragment
import ps.kareemaburejila.postsappfristcode.ui.PostsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : StandardActivity(), PostsAdapter.SelectionListenerPost, PostsFragment.PostsListener {
    private final val TAG= MainActivity::class.simpleName
    private  var postsFragment:PostsFragment?=null
    private  var postFragment: PostFragment?=null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "OnCreate")
        mainViewModel=ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.getInstance(this)
        mainViewModel.getPosts()

        if(!isInTowPanelMode()){
            val fragmentTransaction=supportFragmentManager.beginTransaction()
            postsFragment= PostsFragment()
            fragmentTransaction.replace(
                R.id.container,
                postsFragment!!,
                postsFragment!!::class.simpleName
            )
//            fragmentTransaction.addToBackStack(postsFragment!!::class.simpleName)
            fragmentTransaction.commit()

        }else{
            postFragment=supportFragmentManager.findFragmentById(R.id.post_frag) as PostFragment
        }

    }

    private fun isInTowPanelMode():Boolean{
        return findViewById<FrameLayout>(R.id.container)==null
    }

    override fun OnPostClicked(postModel: PostModel) {

        if (postFragment==null)
            postFragment= PostFragment()

        if (!isInTowPanelMode()){

            val bundle=Bundle()
            bundle.putParcelable("post", postModel)
            postFragment!!.arguments=bundle
            val fragmentTransaction=supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.container,
                postFragment!!,
                postFragment!!::class.simpleName
            )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            supportFragmentManager.executePendingTransactions()
        }else{
            postFragment!!.updatePostDisplay(postModel)
        }

    }

    override fun OnPostDeleted(
        postId: Int,
        postsAdapter: PostsAdapter,
        adapterPosition: Int,
        postModels: MutableList<PostModel?>?
    ) {
        ApiClient().getInstance().deletePost(postID = postId)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    println("Deleted")
                    postModels!!.removeAt(adapterPosition)
                    postsAdapter.notifyItemChanged(adapterPosition)

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    println("NotDeleted")

                }

            })
    }

    override fun OnPostEdit(postModel: PostModel) {
        val dialogView=DialogAddPostBinding.inflate(layoutInflater)
        dialogView.edtTitle.setText(postModel.title)
        dialogView.edtImgUrl.setText(postModel.url)
        AlertDialog.Builder(this)
            .setView(dialogView.root)
            .setPositiveButton("Save"){dialog,which->
                postModel.title=dialogView.edtTitle.text.toString()
                postModel.url=dialogView.edtImgUrl.text.toString()
                ApiClient().getInstance().editPost(postID = postModel.id!!)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            println("Edited Yes")
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            println("Edited No")
                        }
                    })
            }.setNegativeButton("No"){dialog,which->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun OnGivenPosts(postsAdapter: PostsAdapter, progressBar: ProgressBar) {
        Log.i("OnGivenPosts", "OnCreate")
        progressBar.visibility= View.VISIBLE
        mainViewModel.postsMDL.observe(this, Observer {
            postsAdapter.setPosts(it!!)
            progressBar.visibility= View.GONE
            postsAdapter.notifyDataSetChanged()
        })
    }

}