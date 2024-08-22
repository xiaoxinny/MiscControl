package it2161.assignment2.movieviewerbasic_starter

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it2161.assignment2.movieviewerbasic_starter.data.SimpleMovieSampleData
import it2161.assignment2.movieviewerbasic_starter.entity.SimpleMovieItem
import androidx.appcompat.widget.Toolbar

class SimpleViewListOfMoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_list_of_movies)
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.list_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val arrayAdapter: ArrayAdapter<*>
        val movieItemArray: ArrayList<SimpleMovieItem> = SimpleMovieSampleData.simpleMovieitemArray

        var movieListView = findViewById<ListView>(R.id.movielist)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, movieItemArray)
        movieListView.adapter = arrayAdapter

        movieListView.setOnItemClickListener{_, _, position, _ ->
            val selectedMovie = movieItemArray[position]

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
