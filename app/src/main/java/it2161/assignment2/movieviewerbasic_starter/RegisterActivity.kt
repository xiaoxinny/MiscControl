package it2161.assignment2.movieviewerbasic_starter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class RegisterActivity : AppCompatActivity() {

    operator fun times(s: String): String {
        return s
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var registerButton = findViewById<android.widget.Button>(R.id.register_button)

        registerButton.setOnClickListener {
            startActivity(android.content.Intent(this, VerificationCodeActivity::class.java))

            var registerName = findViewById<android.widget.EditText>(R.id.username).text.toString()
            var registerPassword = findViewById<android.widget.EditText>(R.id.password).text.toString()
            var hiddenPassword = "*".repeat(registerPassword.length)
            var registerEmail = findViewById<android.widget.EditText>(R.id.email).text.toString()
            var registerAdminNo = findViewById<android.widget.EditText>(R.id.adminno).text.toString()
            var registerPemGrp = findViewById<android.widget.EditText>(R.id.pem_grp).text.toString()

            val message = """
                Login Name: $registerName 
                Password: $hiddenPassword 
                Email: $registerEmail 
                Admin No: $registerAdminNo 
                PEM Group: $registerPemGrp
            """.trimIndent()

            android.widget.Toast.makeText(
                this,
                message,
                android.widget.Toast.LENGTH_LONG
            ).show()
        }

    }
}