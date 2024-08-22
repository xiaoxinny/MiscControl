package it2161.assignment2.movieviewerbasic_starter

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it2161.assignment2.movieviewerbasic_starter.data.SimpleMovieSampleData
import it2161.assignment2.movieviewerbasic_starter.entity.SimpleMovieItem
import androidx.appcompat.widget.Toolbar

class SimpleItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.simple_activity_item_detail)
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Movie Viewer"

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val movieTitle = intent.getStringExtra("movieTitle")

        val movieItemArray: ArrayList<SimpleMovieItem> = SimpleMovieSampleData.simpleMovieitemArray

        val foundMovie = movieItemArray.find { it.title == movieTitle }

        if (foundMovie != null) {

            val movieTitleTextView: TextView = findViewById<TextView>(R.id.movie_title)
            val movieOverviewTextView: TextView = findViewById<TextView>(R.id.movie_overview)
            val movieReleaseDateTextView: TextView = findViewById<TextView>(R.id.movie_release_date)
            val movieLanguageTextView: TextView = findViewById<TextView>(R.id.movie_langauge)

            movieTitleTextView.text = foundMovie.title
            movieOverviewTextView.text = foundMovie.overview
            movieReleaseDateTextView.text = foundMovie.release_date
            movieLanguageTextView.text = foundMovie.original_langauge

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
