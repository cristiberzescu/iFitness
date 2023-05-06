package com.example.ifitness.application

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ifitness.R
import com.example.ifitness.adaptors.FoodListAdapter
import com.example.ifitness.adaptors.WorkoutListAdapter
import com.example.ifitness.domain.Food
import com.example.ifitness.domain.UserCharacteristics
import com.example.ifitness.domain.Workout
import com.google.firebase.database.*

class TrackingActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var workoutsRecycerView: RecyclerView
    private lateinit var workoutsArrayList: ArrayList<Workout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tracking_activity)

        var buttonMenu = findViewById(R.id.main_button) as ImageButton
        var buttonCalories = findViewById(R.id.calories_button) as ImageButton
        var buttonWorkouts = findViewById(R.id.workouts_button) as ImageButton
        var buttonTracking = findViewById(R.id.tracking_button) as ImageButton
        var buttonMap = findViewById(R.id.map_button) as ImageButton
        var buttonAddWorkout = findViewById(R.id.add_workout_button) as Button
        var emptyListMessage = findViewById(R.id.tv_empty_list) as TextView

        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonCalories.setOnClickListener {
            val intent = Intent(this, CaloriesActivity::class.java)
            startActivity(intent)
        }
        buttonWorkouts.setOnClickListener {
            val intent = Intent(this, SelectMuscleGroupActivity::class.java)
            startActivity(intent)
        }
        buttonTracking.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            startActivity(intent)
        }
        buttonMap.setOnClickListener {
            val query = "gym Timisoara"
            val gmmIntentUri = Uri.parse("geo:0,0?q=$query")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        buttonAddWorkout.setOnClickListener {
            val intent = Intent(this, Antrenamente::class.java)
            startActivity(intent)
        }

        workoutsRecycerView = findViewById(R.id.workouts_list)
        workoutsRecycerView.layoutManager = LinearLayoutManager(this)
        workoutsRecycerView.setHasFixedSize(true)

        workoutsArrayList = arrayListOf<Workout>()

        fun getWorkoutData() {
            database = FirebaseDatabase.getInstance().getReference("users")
                .child(UserCharacteristics.getUsername().toString()).child("workouts")

            database.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (serviceSnapshot in snapshot.children) {
                            val workout = serviceSnapshot.getValue(Workout::class.java)
                            workoutsArrayList.add(workout!!)
                        }
                        workoutsRecycerView.adapter =
                            WorkoutListAdapter(workoutsArrayList, applicationContext)


                    } else {
                        // Dacă lista este goală, faceți TextView-ul vizibil.
                        emptyListMessage.visibility = View.VISIBLE
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }
        getWorkoutData()
    }
}

/*
// Adaugarea unei noi fotografii
val storageRef = FirebaseStorage.getInstance().reference.child("gallery").child(UUID.randomUUID().toString())
val uploadTask = storageRef.putFile(photoUri)
uploadTask.continueWithTask { task ->
if (!task.isSuccessful) {
task.exception?.let {
throw it
}
}
storageRef.downloadUrl
}.addOnCompleteListener { task ->
if (task.isSuccessful) {
val downloadUri = task.result.toString()
val galleryPhoto = GalleryPhoto(downloadUri, date, measurements)
val db = FirebaseFirestore.getInstance()
db.collection("galleryPhotos").add(galleryPhoto)
} else {
// Handle error
}
}

// Afisarea fotografiilor din galerie utilizand un RecyclerView si ImageView
val galleryPhotoRef = db.collection("galleryPhotos")
galleryPhotoRef.addSnapshotListener { snapshot, e ->
if (e != null) {
// Handle error
return@addSnapshotListener
}

galleryPhotoList.clear()
for (doc in snapshot!!.documents) {
    val galleryPhoto = doc.toObject(GalleryPhoto::class.java)
    galleryPhotoList.add(galleryPhoto)
}
galleryPhotoAdapter.notifyDataSetChanged()
}

val recyclerView = findViewById<RecyclerView>(R.id.galleryRecyclerView)
recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
recyclerView.adapter = galleryPhotoAdapter

galleryPhotoAdapter.setOnItemClickListener(object : GalleryPhotoAdapter.OnItemClickListener {
override fun onItemClick(galleryPhoto: GalleryPhoto) {
val imageView = findViewById<ImageView>(R.id.photoImageView)
Picasso.get().load(galleryPhoto.imageUrl).into(imageView)
}
})

 */