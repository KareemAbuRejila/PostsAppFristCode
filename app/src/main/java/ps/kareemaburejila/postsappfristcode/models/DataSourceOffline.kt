package ps.kareemaburejila.postsappfristcode.models

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import ps.kareemaburejila.postsappfristcode.data.MySqLiteHelper
import ps.kareemaburejila.postsappfristcode.data.MySqLiteHelper.PostsTable
import java.util.ArrayList

class DataSourceOffline(context: Context) {
    private var database:SQLiteDatabase?=null
    private var dbHelper: MySqLiteHelper?=null

    @Throws(SQLException::class)
    private fun open(){
        database=dbHelper!!.writableDatabase
    }

    private fun close(){
        if (database!=null){
            database!!.close()
        }
    }

    fun addPost(postModel: PostModel){
        open()
        val database=dbHelper!!.writableDatabase

        val values=ContentValues()
        values.put(PostsTable.COLUMN_NAME_ID,postModel.id)
        values.put(PostsTable.COLUMN_NAME_TITLE,postModel.title)
        values.put(PostsTable.COLUMN_NAME_URL,postModel.url)
        values.put(PostsTable.COLUMN_NAME_THUMBNAIL_URL,postModel.thumbnailUrl)
        values.put(PostsTable.COLUMN_NAME_ALBUM_ID,postModel.albumId)
        database.insert(PostsTable.TABLE_NAME,null,values)
        close()
    }

    fun addAllPost(posts: List<PostModel?>){
        open()
        val database=dbHelper!!.writableDatabase
        posts.forEach { postModel ->
            val values=ContentValues()
            values.put(PostsTable.COLUMN_NAME_ID,postModel!!.id)
            values.put(PostsTable.COLUMN_NAME_TITLE,postModel.title)
            values.put(PostsTable.COLUMN_NAME_URL,postModel.url)
            values.put(PostsTable.COLUMN_NAME_THUMBNAIL_URL,postModel.thumbnailUrl)
            values.put(PostsTable.COLUMN_NAME_ALBUM_ID,postModel.albumId)
            database.insert(PostsTable.TABLE_NAME,null,values)
        }
        close()
    }

    fun dropAll(){
        open()
        dbHelper!!.onUpgrade(database,1,1)
        close()
    }

    val allPosts:List<PostModel?>
    get() {

        val db=dbHelper!!.readableDatabase

        val projection= arrayOf(
            PostsTable.COLUMN_NAME_ID,
            PostsTable.COLUMN_NAME_TITLE,
            PostsTable.COLUMN_NAME_URL,
            PostsTable.COLUMN_NAME_THUMBNAIL_URL,
            PostsTable.COLUMN_NAME_ALBUM_ID)

//        val sortOrder = PostsTable.COLUMN_NAME_TITLE + " ASC"
        val cursor= db.query(
            PostsTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val posts: MutableList<PostModel> = ArrayList()
        while (cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(PostsTable.COLUMN_NAME_ID))
            val albumId=cursor.getInt(cursor.getColumnIndexOrThrow(PostsTable.COLUMN_NAME_ALBUM_ID))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(PostsTable.COLUMN_NAME_TITLE))
            val url=cursor.getString(cursor.getColumnIndexOrThrow(PostsTable.COLUMN_NAME_URL))
            val thumbnailUrl=cursor.getString(cursor.getColumnIndexOrThrow(PostsTable.COLUMN_NAME_THUMBNAIL_URL))
            posts.add(
                PostModel(
                    albumId = albumId,
                    id = id,
                    title = title,
                    url = url,
                    thumbnailUrl = thumbnailUrl
            )
            )
        }
        cursor.close()
        close()
        return posts
    }

    init {
        dbHelper= MySqLiteHelper(context)
    }


}