package com.example.applyononeclickjob

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.applyononeclickjob.databinding.ItemProfileBinding
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso


class Rec111Adapter(private var profiles: List<HashMap<String, String>>) :
    RecyclerView.Adapter<Rec111Adapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding =
            ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = profiles[position]
        holder.binding.profileName.text = profile["name"] // Assuming "name" is a key in the HashMap
        val img = profile["imageUri"] // Assuming "img" is a key in the HashMap


        holder.binding.dob.text=profile["doB"]
        holder.binding.mobile.text=profile["mobile"]
        holder.binding.gender.text=profile["gender"]
        holder.binding.email.text=profile["email"]

        if (img != null) {
            Picasso.get().load(img).into(holder.binding.profileImage)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, AllDetailActivity::class.java)

            intent.putExtra("name",  profile["name"])
            intent.putExtra("dob",  profile["dob"])
            intent.putExtra("mobile",  profile["mobile"])
            intent.putExtra("gender",  profile["gender"])
            intent.putExtra("img",  profile["imageUri"])
            intent.putExtra("email",  profile["email"])
            intent.putExtra("jobTitle",  profile["jobTitle"])
            intent.putExtra("summary",  profile["summary"])
            intent.putExtra("location",  profile["location"])
            intent.putExtra("salary",  profile["salary"])
            intent.putExtra("education",  profile["education"])
            intent.putExtra("yearOfExperience",  profile["yearOfExperience"])
            intent.putExtra("jobType",  profile["jobType"])
            intent.putExtra("skill",  profile["skill"])

            holder.itemView.context.startActivity(intent)
            (holder.itemView.context as? AppCompatActivity)?.finish()

        }
//        holder.itemView.setOnLongClickListener {
//
//            val intent = Intent(holder.itemView.context, Update_data_Activity::class.java)
//
//            intent.putExtra("name",  profile["name"])
//            intent.putExtra("dob",  profile["dob"])
//            intent.putExtra("mobile",  profile["mobile"])
//            intent.putExtra("gender",  profile["gender"])
//            intent.putExtra("img",  profile["imageUri"])
//            intent.putExtra("email",  profile["email"])
//
//            intent.putExtra("jobTitle",  profile["jobTitle"])
//            intent.putExtra("summary",  profile["summary"])
//            intent.putExtra("location",  profile["location"])
//            intent.putExtra("salary",  profile["salary"])
//            intent.putExtra("education",  profile["education"])
//            intent.putExtra("yearOfExperience",  profile["yearOfExperience"])
//            intent.putExtra("jobType",  profile["jobType"])
//
//            holder.itemView.context.startActivity(intent)
//            (holder.itemView.context as? AppCompatActivity)?.finish()
//            return@setOnLongClickListener true
//        }


    }
    fun updateData(newProfiles: MutableList<HashMap<String, String>>) {
        profiles = newProfiles
        notifyDataSetChanged()
    }
}

