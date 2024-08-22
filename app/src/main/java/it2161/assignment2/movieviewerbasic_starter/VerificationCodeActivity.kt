package it2161.assignment2.movieviewerbasic_starter

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VerificationCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verification_code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.verification_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var verifcationCode = 123456

        var verifyButton = findViewById<android.widget.Button>(R.id.verify_button)

        verifyButton.setOnClickListener {
            var verificationCodeInput = findViewById<android.widget.EditText>(R.id.verification_code)

            if (verificationCodeInput.text.toString().toInt() == verifcationCode) {
                startActivity(android.content.Intent(this, LoginActivity::class.java))
            }
            else {
                android.widget.Toast.makeText(this, "Invalid verification code", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}