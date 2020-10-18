package ps.kareemaburejila.postsappfristcode.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ps.kareemaburejila.postsappfristcode.databinding.ItemPostBinding
import ps.kareemaburejila.postsappfristcode.models.PostModel


class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    private var postModels: MutableList<PostModel?>? = ArrayList()
    private var selectionListenerPost: SelectionListenerPost? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemPostBinding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(itemPostBinding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postModel = postModels!![position]!!)
    }

    override fun getItemCount(): Int {
        return postModels!!.size
    }

    fun setPosts(postModels: List<PostModel?>) {
        this.postModels = postModels.toMutableList()
    }

    fun setListener(selectionListenerPost: SelectionListenerPost) {
        this.selectionListenerPost = selectionListenerPost
    }

    inner class PostViewHolder(private val itemPostBinding: ItemPostBinding) :
        RecyclerView.ViewHolder(itemPostBinding.root) {

        fun bind(postModel: PostModel) {
            itemPostBinding.title = postModel.title
            itemPostBinding.executePendingBindings()

            itemPostBinding.root.setOnClickListener {
                selectionListenerPost?.OnPostClicked(postModel = postModel)

            }
            itemPostBinding.root.setOnLongClickListener {
                if (it.isSelected) {
                    it.isSelected = false
                    itemPostBinding.imgDelete.visibility = View.GONE
                    itemPostBinding.imgEdit.visibility = View.GONE
                    return@setOnLongClickListener true
                } else {
                    it.isSelected = true
                    itemPostBinding.imgDelete.visibility = View.VISIBLE
                    itemPostBinding.imgEdit.visibility = View.VISIBLE
                    return@setOnLongClickListener true
                }
            }
            itemPostBinding.imgDelete.setOnClickListener {
                AlertDialog.Builder(it.context)
                    .setMessage("Are You sure?")
                    .setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }.setPositiveButton("Yes") { dialog, which ->
                        if (selectionListenerPost != null)
                            selectionListenerPost!!.OnPostDeleted(postId = postModel.id!!,this@PostsAdapter,adapterPosition,postModels)
                        else
                            Toast.makeText(it.context, "Something Wrong!!", Toast.LENGTH_LONG)
                                .show()
                    }.create().show()
            }

            itemPostBinding.imgEdit.setOnClickListener {
                if (selectionListenerPost != null)
                    selectionListenerPost!!.OnPostEdit(postModel = postModel)
                else
                    Toast.makeText(it.context, "Something Wrong!!", Toast.LENGTH_LONG)
                        .show()
            }
        }
    }

    interface SelectionListenerPost {
        fun OnPostClicked(postModel: PostModel)
        fun OnPostDeleted(
            postId: Int,
            postsAdapter: PostsAdapter,
            adapterPosition: Int,
            postModels: MutableList<PostModel?>?
        )
        fun OnPostEdit(postModel: PostModel)
    }
}