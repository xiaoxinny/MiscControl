package it2161.assignment2.movieviewerbasic_starter.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.database.Cursor
import it2161.assignment2.movieviewerbasic_starter.entity.SimpleMovieItem


class DatabaseAdapter(c: Context) {
    private val dbHelper: SimpleMovieDbHelper = SimpleMovieDbHelper(c)
    private lateinit var db: SQLiteDatabase

    fun open(): DatabaseAdapter {
        db = dbHelper.writableDatabase
        return this
    }

    fun close() {
        dbHelper.close()
    }

    fun insertMovies(dbHelper: SimpleMovieDbHelper, movies: ArrayList<SimpleMovieItem>) {
        val db = dbHelper.writableDatabase

        for (movie in movies) {
            val contentValues = ContentValues().apply {
                put(
                    SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_OVERVIEW,
                    movie.overview
                )
                put(
                    SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_RELEASE_DATE,
                    movie.release_date
                )
                put(
                    SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_ORIGINAL_LANGUAGE,
                    movie.original_langauge
                )
                put(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, movie.title)
                put(
                    SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_IS_FAVORITE,
                    if (movie.is_favorite) 1 else 0
                )
            }

            db.insert(
                SimpleMovieDbHelper.FeedReaderContract.FeedEntry.TABLE_NAME,
                null,
                contentValues
            )
        }

        db.close()
    }

    fun getAllMovies(): ArrayList<SimpleMovieItem> {
        val cursor = db.query(
            SimpleMovieDbHelper.FeedReaderContract.FeedEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val movies = ArrayList<SimpleMovieItem>()

        with(cursor) {
            while (moveToNext()) {
                val movie = SimpleMovieItem(
                    overview = getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_OVERVIEW)),
                    release_date = getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_RELEASE_DATE)),
                    original_langauge = getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_ORIGINAL_LANGUAGE)),
                    title = getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)),
                    is_favorite = getInt(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_IS_FAVORITE)) == 1
                )
                movies.add(movie)
            }
        }
        cursor.close()
        return movies
    }

    fun getMovie(title: String?): SimpleMovieItem? {
        if (title.isNullOrEmpty()) {
            return null
        }

        val cursor = db.query(
            SimpleMovieDbHelper.FeedReaderContract.FeedEntry.TABLE_NAME,
            null,
            "${SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} = ?",
            arrayOf(title),
            null,
            null,
            null
        )

        var movieItem: SimpleMovieItem? = null
        if (cursor.moveToFirst()) {
            movieItem = SimpleMovieItem(
                overview = cursor.getString(cursor.getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_OVERVIEW)),
                release_date = cursor.getString(cursor.getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_RELEASE_DATE)),
                original_langauge = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_ORIGINAL_LANGUAGE
                    )
                ),
                title = cursor.getString(cursor.getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)),
                is_favorite = cursor.getString(cursor.getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_IS_FAVORITE))
                    .toBoolean()
            )
        }

        cursor.close()

        return movieItem
    }

    fun updateFavourite(title: String?, isFavorite: Boolean) {
        if (title.isNullOrEmpty()) {
            return
        }

        val contentValues = ContentValues().apply {
            put(
                SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_IS_FAVORITE,
                if (isFavorite) 1 else 0
            )
        }

        db.update(
            SimpleMovieDbHelper.FeedReaderContract.FeedEntry.TABLE_NAME,
            contentValues,
            "${SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} = ?",
            arrayOf(title)
        )
    }

    fun getFavourites(): ArrayList<SimpleMovieItem> {
        val cursor = db.query(
            SimpleMovieDbHelper.FeedReaderContract.FeedEntry.TABLE_NAME,
            null,
            "${SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_IS_FAVORITE} = ?",
            arrayOf("1"),
            null,
            null,
            null
        )

        val movies = ArrayList<SimpleMovieItem>()

        with(cursor) {
            while (moveToNext()) {
                val movie = SimpleMovieItem(
                    getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_ORIGINAL_LANGUAGE)),
                    getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_OVERVIEW)),
                    getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_RELEASE_DATE)),
                    getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)),
                    getString(getColumnIndexOrThrow(SimpleMovieDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_IS_FAVORITE)).toBoolean()
                )
                movies.add(movie)
            }
        }
        cursor.close()
        return movies
    }


}

