package ps.kareemaburejila.postsappfristcode.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ps.kareemaburejila.postsappfristcode.databinding.FragmentPostBinding
import ps.kareemaburejila.postsappfristcode.models.PostModel


private const val ARG_POST = "post"

class PostFragment : Fragment() {
    private val TAG= PostFragment::class.java.simpleName
    private lateinit var fragmentPostBinding: FragmentPostBinding
    private var postModel: PostModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postModel = it.getParcelable(ARG_POST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentPostBinding= FragmentPostBinding.inflate(inflater,container,false)
        return fragmentPostBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (postModel!=null)
            updatePostDisplay(postModel!!)

    }

    fun updatePostDisplay(postModel: PostModel) {
        Log.i(TAG, "Entered updatePostDisplay()")

        if (view!=null){
            fragmentPostBinding.post=postModel
        }
    }

}