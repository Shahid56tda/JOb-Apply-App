package com.example.applyononeclickjob

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.applyononeclickjob.databinding.ActivityRec111Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Rec111Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRec111Binding
    private lateinit var rec111Adapter: Rec111Adapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRec111Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportActionBar?.title = "All Register Users"
//
//        // Initialize the RecyclerView and set the adapter
//        rec111Adapter = Rec111Adapter(emptyList()) // Initialize with an empty list
//
//        val spanCount = 2 // Set the number of columns in the grid
//        binding.profileRecyclerView.layoutManager = GridLayoutManager(this, spanCount)
//        binding.profileRecyclerView.adapter = rec111Adapter
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("registrations")
//
//        // Fetch data from Firebase
//        // Assuming userReference is the reference to the specific user node
//        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // Check if the snapshot has data
//                if (snapshot.exists()) {
//                    // Iterate through each child (user) in the "Register Users" node
//                    val profiles = mutableListOf<HashMap<String, String>>()
//                    for (userSnapshot in snapshot.children) {
//                        // Get data for each user
//                        val name = userSnapshot.child("fullName").getValue(String::class.java)
//                        val imageUri = userSnapshot.child("imageUrl").getValue(String::class.java)
//                        val dob=userSnapshot.child("dob").getValue(String::class.java)
//                        val mobile=userSnapshot.child("mobile").getValue(String::class.java)
//                        val gender=userSnapshot.child("gender").getValue(String::class.java)
//                        val email=userSnapshot.child("email").getValue(String::class.java)
//                        val jobTitle=userSnapshot.child("jobTitle").getValue(String::class.java)
//                          val summary=userSnapshot.child("summary").getValue(String::class.java)
//                        val location=userSnapshot.child("location").getValue(String::class.java)
//                        val salary=userSnapshot.child("salary").getValue(String::class.java)
//                        val education=userSnapshot.child("education").getValue(String::class.java)
//                        val yearOfExperience=userSnapshot.child("yearOfExperience").getValue(String::class.java)
//                        val jobType=userSnapshot.child("jobType").getValue(String::class.java)
//
//
//
//
//
//                        if (name != null && imageUri != null) {
//                            // Create a HashMap and add it to the list
//                            val profileData = HashMap<String, String>()
//                            profileData["name"] = name
//                            profileData["imageUri"] = imageUri
//                            profileData["dob"]=dob.toString()
//                            profileData["mobile"]=mobile.toString()
//                            profileData["gender"]=gender.toString()
//                            profileData["email"]=email.toString()
//                            profileData["jobTitle"] = jobTitle.toString()
//                            profileData["summary"] = summary.toString()
//                            profileData["location"]=location.toString()
//                            profileData["salary"]=salary.toString()
//                            profileData["education"]=education.toString()
//                            profileData["yearOfExperience"]=yearOfExperience.toString()
//                            profileData["jobType"] = jobType.toString()
//
//                            profiles.add(profileData)
//                        }
//                    }
//
//                    // Update the adapter with the new data
//                    rec111Adapter.updateData(profiles)
//                } else {
//                    // Handle the case where the "Register Users" node doesn't exist
//                    Toast.makeText(this@Rec111Activity, "No data found", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle errors
//                Toast.makeText(this@Rec111Activity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
    }

//    override fun onBackPressed() {
//        val intent = Intent(this@Rec111Activity, RegisterActivity::class.java)
//        startActivity(intent)
//        super.onBackPressed()
//    }
}
