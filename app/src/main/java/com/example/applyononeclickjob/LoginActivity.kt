package com.example.applyononeclickjob

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.applyononeclickjob.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    private var passwordShowing = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())





        auth = FirebaseAuth.getInstance()
        binding.passwordIcon.setOnClickListener { v ->
            if (passwordShowing) {
                passwordShowing = false
                binding.passwordEt.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.passwordIcon.setImageResource(R.drawable.password_show)
            } else {
                passwordShowing = true
                binding.passwordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                binding.passwordIcon.setImageResource(R.drawable.password_hide)
            }
            binding.passwordEt.setSelection(binding.passwordEt.length())
        }
        binding.signup.setOnClickListener { v ->
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }
        binding.signInBtn.setOnClickListener { v ->
            val email: String = binding.email.getText().toString()
            val password: String = binding.passwordEt.getText().toString()
            if (email.isEmpty() || !email.contains("@")) {
                dataError(binding.email, "your email is not valid")
            } else if (password.isEmpty() || password.length < 7) {
                dataError(binding.passwordEt, "Your pasword is Wrogn")
            } else {
                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@LoginActivity, "LogIn", Toast.LENGTH_SHORT)
                                .show()

                            //code for verification
                            val preferences = getSharedPreferences("login", MODE_PRIVATE)
                            val editor = preferences.edit()
                            editor.putBoolean("flag", false)
                            editor.apply()

                            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                            startActivity(intent)



                        } else {
                            Toast.makeText(this@LoginActivity, "wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this@LoginActivity, "network error", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        val USER = auth!!.currentUser
//        if (USER != null) {
//            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

    fun dataError(editText: EditText, s: String?) {
        editText.error = s
        editText.requestFocus()
    }
}