package ps.kareemaburejila.postsappfristcode.models

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ps.kareemaburejila.postsappfristcode.R

class PostModel :Parcelable{
    var albumId:Int?=null
    var id:Int?=null
    var title:String?=null
    var url:String?=null
    var thumbnailUrl:String?=null

    constructor(parcel: Parcel) : this() {
        albumId = parcel.readValue(Int::class.java.classLoader) as? Int
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        title = parcel.readString()
        url = parcel.readString()
        thumbnailUrl = parcel.readString()
    }

    constructor()
    constructor(albumId: Int?, id: Int?, title: String?, url: String?, thumbnailUrl: String?) {
        this.albumId = albumId
        this.id = id
        this.title = title
        this.url = url
        this.thumbnailUrl = thumbnailUrl
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(albumId)
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostModel> {
        override fun createFromParcel(parcel: Parcel): PostModel {
            return PostModel(parcel)
        }

        override fun newArray(size: Int): Array<PostModel?> {
            return arrayOfNulls(size)
        }

        @JvmStatic
        @BindingAdapter("ImgVPost")
        fun loadImagePost(view: ImageView,url:String?){
            if (url != null && url != "")
                Picasso.get().load(url).placeholder(android.R.color.darker_gray)
                    .error(R.drawable.ic_baseline_broken_image_24).into(view)
        }

    }



}