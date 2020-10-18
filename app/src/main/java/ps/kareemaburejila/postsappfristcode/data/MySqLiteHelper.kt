package ps.kareemaburejila.postsappfristcode.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class MySqLiteHelper internal constructor(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    internal object PostsTable : BaseColumns {
        const val TABLE_NAME = "posts"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_ALBUM_ID = "albumId"
        const val COLUMN_NAME_URL = "url"
        const val COLUMN_NAME_THUMBNAIL_URL = "thumbnailUrl"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+ PostsTable.TABLE_NAME)
        onCreate(db)
    }

    companion object{
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PostsTable.TABLE_NAME + " (" +
                    PostsTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    PostsTable.COLUMN_NAME_TITLE + " TEXT," +
                    PostsTable.COLUMN_NAME_ALBUM_ID + " INTEGER," +
                    PostsTable.COLUMN_NAME_URL + " TEXT," +
                    PostsTable.COLUMN_NAME_THUMBNAIL_URL + " TEXT)"
        private const val DATABASE_NAME = "postsApp.db"
        private const val DATABASE_VERSION = 1
    }
}