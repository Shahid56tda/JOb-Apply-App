package com.example.applyononeclickjob

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.applyononeclickjob.databinding.ActivityAllDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class AllDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllDetailBinding
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "All Detail Of User"
//        binding.email.setOnClickListener {
//            val emailIntent = Intent(Intent.ACTION_SENDTO)
//            emailIntent.data = Uri.parse("mailto:shahidalishahidali461@gmail.com") // Replace with the desired email address
//            startActivity(Intent.createChooser(emailIntent, "Send email..."))
//        }

        binding.whatsapp.setOnClickListener {
            val phoneNumber = "923480060631" // Replace with the desired phone number
            val message = "Hello, this is a test message." // Your message

            // Create the intent
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra("jid", "$phoneNumber@s.whatsapp.net")
            sendIntent.putExtra(Intent.EXTRA_TEXT, message)
            sendIntent.type = "text/plain"

            // Check if there is any activity that can handle the intent
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            } else {
                Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
            }
        }


        val name = intent.getStringExtra("name")
        val dob = intent.getStringExtra("dob")
        val mobile = intent.getStringExtra("mobile")
        val gender = intent.getStringExtra("gender")
        val imageUri = intent.getStringExtra("img")
        val email = intent.getStringExtra("email")
        val jobTitle = intent.getStringExtra("jobTitle")
        val summary = intent.getStringExtra("summary")
        val location = intent.getStringExtra("location")
        val salary = intent.getStringExtra("salary")
        val education = intent.getStringExtra("education")
        val yearOfExperience = intent.getStringExtra("yearOfExperience")
        val jobType = intent.getStringExtra("jobType")
        val skill = intent.getStringExtra("skill")
        val jobKey = if (jobType == "Job Seeker") "job seeker" else "hiring persons"
        databaseReference = FirebaseDatabase.getInstance().getReference(jobKey)


        if (name != null && imageUri != null) {
            binding.fullName.setText(name)
            binding.name1.setText(name)
            Picasso.get().load(imageUri).into(binding.img)
            binding.mobile.setText(mobile)
            binding.gender.setText(gender)
            binding.dob.setText(dob)
            binding.email.setText(email)
            binding.jobTitle.setText(jobTitle.toString())
            binding.summary.setText(summary.toString())
            binding.location.setText(location)
            binding.salary.setText(salary)
            binding.education.setText(education)
            binding.yearOfExperience.setText(yearOfExperience)
            binding.jobType.setText(jobType)
            binding.skill.setText(skill)

        }

        // Use the data as needed
        // ...
        auth = FirebaseAuth.getInstance()
        binding.fab.setOnClickListener { v ->
            val builder = AlertDialog.Builder(v.getContext())
            builder.setTitle("Are you sure!")
            builder.setMessage("Edit your file here")
            builder.setPositiveButton(
                "Update"
            ) { dialog, which ->
                val intent = Intent(this@AllDetailActivity, Update_data_Activity::class.java)


                intent.putExtra("name",name)
                intent.putExtra("dob",dob )
                intent.putExtra("mobile",mobile)
                intent.putExtra("gender",gender)
                intent.putExtra("img",imageUri)
                intent.putExtra("email",email)

                intent.putExtra("jobTitle",jobTitle)
                intent.putExtra("summary",summary)
                intent.putExtra("location",location)
                intent.putExtra("salary",salary)
                intent.putExtra("education",education)
                intent.putExtra("yearOfExperience",yearOfExperience)
                intent.putExtra("jobType",jobType)
                intent.putExtra("skill",skill)



                startActivity(intent)
                finish()
            }
            builder.setNegativeButton(
                "Delete"
            ) { dialog, which ->
                val jobType = intent.getStringExtra("jobType")
                deleteProfileFromDatabase()
            }
            builder.show()
        }

    }
    private fun deleteProfileFromDatabase() {
        // Get the user's ID or any unique identifier for the profile
        val userId = auth.currentUser?.uid

        // Check if the ID is not null and jobType is not null
        if (userId != null ) {
            // Reference to the user's profile in the appropriate node in the database
            val userRef = databaseReference.child(userId)

            // Remove the user's profile data from the database
            userRef.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AllDetailActivity, DashBoardActivity::class.java)
                    startActivity(intent)
                    finish() // Close the activity after deletion
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error deleting profile: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}


