package com.example.applyononeclickjob


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.applyononeclickjob.databinding.ActivityDashBoardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DashBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jobh = intent.getStringExtra("m")
        val jobs = intent.getStringExtra("f")

        if ("m" == jobh) {
            replaceFragment(Rec11Fragment(), addToBackStack = true)
        } else if ("f" == jobs) {
            replaceFragment(Rec22Fragment(), addToBackStack = true)
        } else {
            // Default fragment when neither "m" nor "f" is passed
            replaceFragment(Rec11Fragment(), addToBackStack = false)
        }

        binding.bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.jobtitle))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.person))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.share))

        binding.bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> replaceFragment(Rec11Fragment(), addToBackStack = true)
                2 -> replaceFragment(Rec22Fragment(), addToBackStack = true)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        } else {
            // If not adding to back stack, pop the previous fragments
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Inflate menu items
        menuInflater.inflate(R.menu.common_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout) {
            val preferences = getSharedPreferences("login", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean("flag", false)
            editor.apply()

            val currentUser = FirebaseAuth.getInstance().currentUser

            // Check if there's a current user before attempting to delete
            if (currentUser != null) {
                currentUser.delete().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show()
                       val selectedJobType=intent.getStringExtra("jobse")

                        // Determine the corresponding Firebase node based on the job type
                        val jobNode = if (selectedJobType == "Job Seeker") "job seeker" else "hiring persons"

                        // Use the jobNode in your Firebase database reference
                        val databaseReference = FirebaseDatabase.getInstance().getReference().child(jobNode)

                        databaseReference.removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
                                 // Close the activity after deletion
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error deleting profile: ${it.message}", Toast.LENGTH_SHORT).show()
                            }


                    } else {
                        Toast.makeText(this, "Failed to delete account: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            FirebaseAuth.getInstance().signOut()


            val intent = Intent(this@DashBoardActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}