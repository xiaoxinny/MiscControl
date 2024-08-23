package it2161.assignment2.movieviewerbasic_starter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it2161.assignment2.movieviewerbasic_starter.data.DatabaseAdapter
import it2161.assignment2.movieviewerbasic_starter.data.SimpleMovieDbHelper
import it2161.assignment2.movieviewerbasic_starter.data.SimpleMovieSampleData
import it2161.assignment2.movieviewerbasic_starter.entity.SimpleMovieItem

class ViewFavouritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_favourites)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Movie Viewer"

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val arrayAdapter: ArrayAdapter<*>
        val dbAdapter = DatabaseAdapter(this).open()

        val movieListView = findViewById<ListView>(R.id.favouriteslist)

        val favouritesItems = dbAdapter.getFavourites()

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, favouritesItems)
        movieListView.adapter = arrayAdapter

        movieListView.setOnItemClickListener{_, _, position, _ ->
            val selectedMovie = favouritesItems[position]

            // Create an Intent to start the DetailActivity
            val intent = Intent(this, SimpleItemDetailActivity::class.java).apply {
                // Optionally pass data to the new activity
                putExtra("movieTitle", selectedMovie.title)
                // Add more data if needed
            }

            // Start the new activity
            startActivity(intent)
        }
    }
}