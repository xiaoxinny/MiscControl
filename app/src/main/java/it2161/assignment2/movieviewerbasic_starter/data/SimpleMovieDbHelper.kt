package it2161.assignment2.movieviewerbasic_starter.data
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class SimpleMovieDbHelper(c: Context) : SQLiteOpenHelper(c, DATABASE_NAME, null, DATABASE_VER) {

    object FeedReaderContract{
        object FeedEntry : BaseColumns {
            const val TABLE_NAME = "movies"
            const val COLUMN_NAME_OVERVIEW = "overview"
            const val COLUMN_NAME_RELEASE_DATE = "release_date"
            const val COLUMN_NAME_ORIGINAL_LANGUAGE = "original_language"
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_IS_FAVORITE = "is_favorite"
        }
    }

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"

    companion object {
        val DATABASE_NAME = "simplemovie.db"
        val DATABASE_VER = 1
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_OVERVIEW} TEXT," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_RELEASE_DATE} TEXT," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_ORIGINAL_LANGUAGE} TEXT," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} TEXT," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_IS_FAVORITE} INTEGER)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        db?.execSQL(SQL_CREATE_ENTRIES)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}