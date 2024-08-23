package it2161.assignment2.movieviewerbasic_starter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar
import it2161.assignment2.movieviewerbasic_starter.data.DatabaseAdapter

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

        val dbAdapter = DatabaseAdapter(this).open()

        val foundMovie = dbAdapter.getMovie(movieTitle)

        val movieTitleTextView: TextView = findViewById<TextView>(R.id.movie_title)
        val movieOverviewTextView: TextView = findViewById<TextView>(R.id.movie_overview)
        val movieReleaseDateTextView: TextView = findViewById<TextView>(R.id.movie_release_date)
        val movieLanguageTextView: TextView = findViewById<TextView>(R.id.movie_langauge)

        movieTitleTextView.text = foundMovie?.title
        movieOverviewTextView.text = foundMovie?.overview
        movieReleaseDateTextView.text = foundMovie?.release_date
        movieLanguageTextView.text = foundMovie?.original_langauge

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_favourite_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_favourite -> {
                val movieTitle = intent.getStringExtra("movieTitle")
                val dbAdapter = DatabaseAdapter(this).open()
                dbAdapter.updateFavourite(movieTitle, true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
