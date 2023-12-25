package com.example.applyononeclickjob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.applyononeclickjob.databinding.FragmentRec11Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Rec11Fragment : Fragment() {
    lateinit var binding: FragmentRec11Binding

    private lateinit var rec111Adapter: Rec111Adapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRec11Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       activity?.title = "All job seeker Users"

        // Initialize the RecyclerView and set the adapter
        rec111Adapter = Rec111Adapter(emptyList()) // Initialize with an empty list

        val spanCount = 2 // Set the number of columns in the grid
        binding.profileRecyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.profileRecyclerView.adapter = rec111Adapter
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("job seeker")

            // Fetch data from Firebase
            // Assuming userReference is the reference to the specific user node
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Check if the snapshot has data
                    if (snapshot.exists()) {
                        // Iterate through each child (user) in the "Register Users" node
                        val profiles = mutableListOf<HashMap<String, String>>()
                        for (userSnapshot in snapshot.children) {
                            // Get data for each user
                            val name = userSnapshot.child("fullName").getValue(String::class.java)
                            val imageUri = userSnapshot.child("imageUrl").getValue(String::class.java)
                            val dob = userSnapshot.child("dob").getValue(String::class.java)
                            val skill = userSnapshot.child("skill").getValue(String::class.java)
                            val mobile = userSnapshot.child("mobile").getValue(String::class.java)
                            val gender = userSnapshot.child("gender").getValue(String::class.java)
                            val email = userSnapshot.child("email").getValue(String::class.java)
                            val jobTitle = userSnapshot.child("jobTitle").getValue(String::class.java)
                            val summary = userSnapshot.child("summary").getValue(String::class.java)
                            val location = userSnapshot.child("location").getValue(String::class.java)
                            val salary = userSnapshot.child("salary").getValue(String::class.java)
                            val education = userSnapshot.child("education").getValue(String::class.java)
                            val yearOfExperience =
                                userSnapshot.child("yearOfExperience").getValue(String::class.java)
                            val jobType = userSnapshot.child("jobType").getValue(String::class.java)
                            val defaultImageUri = "https://example.com/default-image.jpg"
                            val finalImageUri = if (imageUri.isNullOrEmpty()) {
                                defaultImageUri
                            } else {
                                imageUri
                            }

                            if (name != null && imageUri != null) {
                                // Create a HashMap and add it to the list
                                val profileData = HashMap<String, String>()
                                profileData["name"] = name
                                profileData["imageUri"] = finalImageUri
                                profileData["dob"] = dob.toString()
                                profileData["mobile"] = mobile.toString()
                                profileData["gender"] = gender.toString()
                                profileData["email"] = email.toString()
                                profileData["jobTitle"] = jobTitle.toString()
                                profileData["summary"] = summary.toString()
                                profileData["location"] = location.toString()
                                profileData["salary"] = salary.toString()
                                profileData["education"] = education.toString()
                                profileData["yearOfExperience"] = yearOfExperience.toString()
                                profileData["jobType"] = jobType.toString()
                                profileData["skill"] = skill.toString()

                                profiles.add(profileData)
                            }
                        }

                        // Update the adapter with the new data
                        rec111Adapter.updateData(profiles)
                    } else {
                        // Handle the case where the "Register Users" node doesn't exist
                        Toast.makeText(
                            requireContext(),
                            "No data found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            // ... rest of the code
        }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            }

            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }


}
