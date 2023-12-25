package com.example.applyononeclickjob

import android.R
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.applyononeclickjob.databinding.ActivityRegisterBinding
import com.example.applyononeclickjob.databinding.ActivityUpdateDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.Calendar
import java.util.regex.Matcher
import java.util.regex.Pattern

class Update_data_Activity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateDataBinding
    lateinit var picker: DatePickerDialog
    private var imageUrl: String = ""
    lateinit var selectedGender:String
    lateinit var selectedjob:String
    lateinit var genderRadioGroup: RadioGroup
    lateinit var maleRadioButton: RadioButton
    lateinit var femaleRadioButton: RadioButton

    lateinit var jobRadioGroup: RadioGroup
    lateinit var seekRadioButton: RadioButton
    lateinit var hiringRadioButton: RadioButton

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    lateinit var uriImage: Uri
lateinit var imageUri:String
    private val auth = FirebaseAuth.getInstance()
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this).apply {
            setMessage("Sending data...")
            setCancelable(false)
        }
    }
    val year= arrayListOf(
        "2012","2013","2014","2015","2016"
    )

    val classg= arrayListOf(
        "Graduation","Master","phd"
    )

    val salary= arrayListOf(
        "10k","20k","30k","40k","50k"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        genderRadioGroup = binding.gender
        maleRadioButton = binding.m
        femaleRadioButton = binding.f

        binding.gender.setOnCheckedChangeListener { group, checkedId ->
            // Check which radio button is selected
            when (checkedId) {
                binding.m.id->selectedGender = "Male"
                binding.f.id -> selectedGender = "Female"
                // Add more cases if you have additional radio buttons
            }
        }

        jobRadioGroup = binding.gjob
        seekRadioButton = binding.seeker
        hiringRadioButton = binding.hiring

        binding.gjob.setOnCheckedChangeListener { group, checkedId ->
            // Check which radio button is selected
            when (checkedId) {
                binding.seeker.id->selectedjob = "Job Seeker"
                binding.hiring.id -> selectedjob = "Hiring Persons"
                // Add more cases if you have additional radio buttons
            }
        }



        binding.edu.adapter=
            ArrayAdapter<String>(this, R.layout.simple_expandable_list_item_1,classg)
        binding.edu.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                binding.tedu.text=classg.get(position.toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }
        binding.img.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 23)
        }

        binding.yop.adapter=ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,year)
        binding.yop.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                binding.tyop.text=year.get(position.toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }

        binding.salary.adapter=ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,salary)
        binding.salary.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                binding.tsalary.text=salary.get(position.toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }

        binding.dob.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val day = calendar[Calendar.DAY_OF_MONTH]
            val month = calendar[Calendar.MONTH]
            val year = calendar[Calendar.YEAR]


            //Date Picker Dialog

            picker = DatePickerDialog(this@Update_data_Activity,

                { view, year, month, dayOfMonth -> binding.dob.setText(dayOfMonth.toString() + "/" + (month + 1) + "/" + year) },
                year,
                month,
                day
            )

            picker!!.show()
        })
        val name = intent.getStringExtra("name")
        val dob = intent.getStringExtra("dob")
        val mobile = intent.getStringExtra("mobile")
        val gender = intent.getStringExtra("gender")
         imageUri = intent.getStringExtra("img").toString()
        val email = intent.getStringExtra("email")
        val jobTitle = intent.getStringExtra("jobTitle")
        val summary = intent.getStringExtra("summary")
        val location = intent.getStringExtra("location")
        val salary = intent.getStringExtra("salary")
        val education = intent.getStringExtra("education")
        val yearOfExperience = intent.getStringExtra("yearOfExperience")
        val jobType = intent.getStringExtra("jobType")
        val skill = intent.getStringExtra("skill")


        binding.fullNameEt.setText(name)
        binding.phone.setText(mobile)
        binding.dob.setText(dob)
        Picasso.get().load(imageUri).into(binding.img)
        binding.email.setText(email)
        binding.jobtitleCompany.setText(jobTitle)
        binding.summary.setText(summary)
        binding.location.setText(location)
        binding.tsalary.setText(salary)
        binding.tedu.setText(education)
        binding.tyop.setText(yearOfExperience)
        binding.skill.setText(skill)



        binding.register.setOnClickListener { view: View? ->





            //obtain the entered data
            val textFullName = binding.fullNameEt.getText().toString()
            val textEmail = binding.email.getText().toString()
            val textDob = binding.dob.getText().toString()
            val textMobile = binding.phone.getText().toString()
            val jobtitle=binding.jobtitleCompany.getText().toString()
            val summary = binding.summary.getText().toString()
            val  loc=binding.location.getText().toString()
            val salary=binding.tsalary.getText().toString()
            val yop=binding.tyop.getText().toString()
            val edu =binding.tedu.getText().toString()
            val skill =binding.skill.getText().toString()
            val img=imageUri


            val textGender: String // Can't obtain the value before verifying if any button was selected or not
            val textjobt:String

            //validate Mobile Number using Matcher and pattern (Regular Expression)
            val mobileRegex =
                "[0-9][0-9]{9}" // First no. can be {6,8,9} and rest 9 nos. can be any no.
            val mobileMatcher: Matcher
            val mobilePattern =
                Pattern.compile(mobileRegex)
            mobileMatcher = mobilePattern.matcher(textMobile)
            if (TextUtils.isEmpty(textFullName)) {
                Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show()
                binding.fullNameEt.setError("Full Name is required")
                binding.fullNameEt.requestFocus()
            } else if (TextUtils.isEmpty(textEmail)) {
                Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show()
                binding.email.setError("Email is required")
                binding.email.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                Toast.makeText(this, "Please re-enter your email", Toast.LENGTH_SHORT).show()
                binding.email.setError("Valid Email is required")
                binding.email.requestFocus()
            } else if (TextUtils.isEmpty(textDob)) {
                Toast.makeText(this, "Please your date of birth", Toast.LENGTH_SHORT).show()
                binding.dob.setError("Date of Birth is required")
                binding.dob.requestFocus()
            } else if (TextUtils.isEmpty(textMobile)) {
                Toast.makeText(this, "Please enter your mobile no.", Toast.LENGTH_SHORT).show()
                binding.phone.setError("Mobile No. is required")
                binding.phone.requestFocus()
            } else if (!mobileMatcher.find()) {
                Toast.makeText(this, "Please re-enter your mobile no.", Toast.LENGTH_SHORT).show()
                binding.phone.setError("Mobile No. is not valid")
                binding.phone.requestFocus()
            } else if (textMobile.length != 11) {
                Toast.makeText(this, "Please re-enter your mobile no.", Toast.LENGTH_SHORT).show()
                binding.phone.setError("Mobile No. should be 11 digits")
                binding.phone.requestFocus()
            } else if (TextUtils.isEmpty(jobtitle)) {
                Toast.makeText(this, "Write your job title", Toast.LENGTH_SHORT).show()
                binding.jobtitleCompany.setError("Write your job title")
                binding.jobtitleCompany.requestFocus()
            } else if (TextUtils.isEmpty(summary)) {
                Toast.makeText(this, "Enter Summary", Toast.LENGTH_SHORT).show()
                binding.summary.setError("Enter summary")
                binding.summary.requestFocus()

                //cleared the entered password

            }
            else if (TextUtils.isEmpty(loc)) {
                Toast.makeText(this, "Enter Location", Toast.LENGTH_SHORT).show()
                binding.location.setError("Enter correct Location")
                binding.location.requestFocus()
            }
            else if (TextUtils.isEmpty(salary)) {
                Toast.makeText(this, "select salary", Toast.LENGTH_SHORT).show()
                binding.tsalary.setError("select salary")
                binding.tsalary.requestFocus()
            }
            else if (TextUtils.isEmpty(edu)) {
                Toast.makeText(this, "select education", Toast.LENGTH_SHORT).show()
                binding.tedu.setError("select Education")
                binding.tedu.requestFocus()
            }
            else if (TextUtils.isEmpty(yop)) {
                Toast.makeText(this, "select year of experince", Toast.LENGTH_SHORT).show()
                binding.tyop.setError("select year of experince")
                binding.tyop.requestFocus()
            }
            else if (TextUtils.isEmpty(skill)) {
                Toast.makeText(this, "select year of experince", Toast.LENGTH_SHORT).show()
                binding.skill.setError("select year of experince")
                binding.skill.requestFocus()
            }


            else {

// ...

                val userId = auth.currentUser?.uid
                val jobKey = if (selectedjob == "Job Seeker") "job seeker" else "hiring persons"

                if (userId != null ) {
                    // Create a map of fields you want to update
                    val updates = HashMap<String, Any>()
                    updates["fullName"] = binding.fullNameEt.text.toString()
                    updates["email"] = binding.email.text.toString()
                    updates["dob"] = binding.dob.text.toString()
                    updates["mobile"] = binding.phone.text.toString()
                    updates["jobTitle"] = binding.jobtitleCompany.text.toString()
                    updates["summary"] = binding.summary.text.toString()
                    updates["location"] = binding.location.text.toString()
                    updates["salary"] = binding.tsalary.text.toString()
                    updates["education"] = binding.tedu.text.toString()
                    updates["yearOfExperience"] = binding.tyop.text.toString()
                    updates["gender"] = selectedGender
                    if (imageUrl.isEmpty()){ updates["imageUrl"] =  img}else
                    {updates["imageUrl"] =  imageUrl}
                    updates["skill"]=skill

                    progressDialog.show()

                    // Update the user data in Firebase with the user's ID as the key
                    FirebaseDatabase.getInstance().getReference().child(jobKey).child(userId)
                        .updateChildren(updates)
                        .addOnCompleteListener { task ->
                            progressDialog.dismiss()

                            if (task.isSuccessful) {
                                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@Update_data_Activity, DashBoardActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "User ID is null", Toast.LENGTH_SHORT).show()
                }

// ...








            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 23 && resultCode == RESULT_OK && data != null && data.data != null) {
            uriImage = data.data!!
            binding.img.setImageURI(uriImage)
        // Register observers to listen for when the upload is done or if it fails
            uploadImage()
        }
    }
    private fun uploadImage() {
        if (uriImage != null) {
            progressDialog.show()

            // Create a reference to "images/<user_id>.jpg"
            val imageRef = storageRef.child("images/${auth.currentUser?.uid}.jpg")

            // Upload file to Firebase Storage
            val uploadTask = imageRef.putFile(uriImage!!)

            // Register observers to listen for when the upload is done or if it fails
            uploadTask.addOnSuccessListener { taskSn->
                // Image uploaded successfully

                // Get the download URL
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Handle the download URL (e.g., display the image or save it in the database)
                    imageUrl = downloadUri.toString()

                    // Optionally, you can use the imageUrl as needed
                    Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()

                    // Optionally, you can now use the imageUrl as needed
                    Log.d("ImageUrl", imageUrl)

                    progressDialog.dismiss()
                }.addOnFailureListener { e ->
                    // Handle the failure to get the download URL
                    Toast.makeText(this, "Error getting download URL: ${e.message}", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }.addOnFailureListener { e ->
                // Handle unsuccessful uploads
                Toast.makeText(this, "Error uploading image: ${e.message}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
    }


}