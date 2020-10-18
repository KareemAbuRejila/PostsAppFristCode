package ps.kareemaburejila.postsappfristcode.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import okhttp3.ResponseBody
import ps.kareemaburejila.postsappfristcode.adapters.PostsAdapter
import ps.kareemaburejila.postsappfristcode.data.ApiClient
import ps.kareemaburejila.postsappfristcode.databinding.DialogAddPostBinding
import ps.kareemaburejila.postsappfristcode.databinding.FragmentPostsBinding
import ps.kareemaburejila.postsappfristcode.models.PostModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class PostsFragment : Fragment() {

    private lateinit var fragmentPostsBinding: FragmentPostsBinding
    private val TAG:String= PostsFragment::class.simpleName!!
    private var postModels:ArrayList<PostModel?>?=ArrayList()
    private lateinit var selectionListenerPost: PostsAdapter.SelectionListenerPost
    private val postsAdapter=PostsAdapter()
    private var postsListener: PostsListener?=null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PostsAdapter.SelectionListenerPost){
            selectionListenerPost=context as PostsAdapter.SelectionListenerPost
        }else{
            throw RuntimeException("You Must Implement OnClickedPost")
        }
        if (context is PostsListener){
            postsListener=context as PostsListener
        }else{
            throw RuntimeException("You Must Implement OnGivenPosts")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentPostsBinding= FragmentPostsBinding.inflate(inflater,container,false)
        fragmentPostsBinding.fab.setOnClickListener {
            addPost(inflater)
        }
        return fragmentPostsBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance=true
        Log.i(TAG,"OnCreate")

        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsAdapter.setPosts(postModels as List<PostModel>)
        postsAdapter.setListener(selectionListenerPost)
        fragmentPostsBinding.adapter=postsAdapter

    }
    fun addPost(layoutInflater:LayoutInflater){
        val dialogView= DialogAddPostBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .setPositiveButton("Add"){dialog,which->
                val postModel=PostModel()
                postModel.id=1
                postModel.title=dialogView.edtTitle.text.toString()
                postModel.url=dialogView.edtImgUrl.text.toString()
                postModel.albumId=1
                postModel.thumbnailUrl=dialogView.edtImgUrl.text.toString()
                ApiClient().getInstance().addPost(postModel)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            println("Added Yes")
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            println("Added No")
                        }
                    })
            }.setNegativeButton("Cancel"){dialog,which->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    override fun onStart() {
        super.onStart()
        if (postsListener!=null)
            postsListener!!.OnGivenPosts(postsAdapter,fragmentPostsBinding.progress)
    }
    interface PostsListener {
        fun OnGivenPosts(postsAdapter:PostsAdapter,progressBar: ProgressBar)
    }

}