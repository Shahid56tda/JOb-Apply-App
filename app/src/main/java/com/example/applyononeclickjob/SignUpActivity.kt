package com.example.applyononeclickjob

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.applyononeclickjob.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private var passwordShowing = false
    private var conPasswordShowing = false
    lateinit var auth: FirebaseAuth
    lateinit var progressDialog: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())




        progressDialog = ProgressBar(this)
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
        binding.conpasswordIcon.setOnClickListener { v ->
            if (conPasswordShowing) {
                conPasswordShowing = false
                binding.conPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.conpasswordIcon.setImageResource(R.drawable.password_show)
            } else {
                conPasswordShowing = true
                binding.conPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                binding.conpasswordIcon.setImageResource(R.drawable.password_hide)
            }
            binding.conPasswordEt.setSelection(binding.passwordEt.length())
        }
        binding.signUpBtn.setOnClickListener { v -> DataSendingRule() }
        binding.signIn.setOnClickListener { v ->
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }

    fun DataSendingRule() {
        val name: String = binding.fullNameEt.getText().toString()
        val email: String = binding.email.getText().toString()
        val password: String = binding.passwordEt.getText().toString()
        val phone: String = binding.phone.getText().toString()
        val confirmpassword: String = binding.conPasswordEt.getText().toString()
        if (name.isEmpty() || name.length < 7) {
            dataError(binding.fullNameEt, "User name is not valid")
        } else if (email.isEmpty() || !email.contains("@")) {
            dataError(binding.email, "your email is not valid")
        } else if (password.isEmpty() || password.length < 7) {
            dataError(binding.passwordEt, "Your pasword is Wrogn")
        } else if (confirmpassword.isEmpty() || confirmpassword != password) {
            dataError(binding.conPasswordEt, "passwor does not match")
        } else if (phone.isEmpty() || phone.length < 11) {
            dataError(binding.phone, "Invalid PhoneNumber")
        } else {
            auth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Sign up successful",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        //Shared Prefrence
                       // Finish the SignUpActivity


                        //code for verification
                        val preferences = getSharedPreferences("login", MODE_PRIVATE)
                        val editor = preferences.edit()
                        editor.putBoolean("flag", false)
                        editor.apply()

                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignUpActivity, "Sign up failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val USER = auth!!.currentUser
        if (USER != null) {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun dataError(editText: EditText, s: String?) {
        editText.error = s
        editText.requestFocus()
    }
}