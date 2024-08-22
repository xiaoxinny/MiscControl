package it2161.assignment2.movieviewerbasic_starter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        var registerButton = findViewById<android.widget.Button>(R.id.register_button)

        registerButton.setOnClickListener {
            startActivity(android.content.Intent(this, RegisterActivity::class.java))
        }

        var loginButton = findViewById<android.widget.Button>(R.id.login_button)

        loginButton.setOnClickListener {

            var loginName = findViewById<android.widget.EditText>(R.id.username)
            var loginPassword = findViewById<android.widget.EditText>(R.id.password)

            if (loginName.text.toString() == "admin" && loginPassword.text.toString() == "12345678") {
                startActivity(android.content.Intent(this, SimpleViewListOfMoviesActivity::class.java))
            }
            else {
                android.widget.Toast.makeText(this, "Invalid username or password", android.widget.Toast.LENGTH_SHORT).show()
            }

        }

    }
}