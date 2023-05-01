package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.example.ifitness.R

class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tracking_activity)

        var buttonMenu = findViewById(R.id.main_button) as ImageButton
        var buttonCalories = findViewById(R.id.calories_button) as ImageButton
        var buttonWorkouts = findViewById(R.id.workouts_button) as ImageButton
        var buttonTracking = findViewById(R.id.tracking_button) as ImageButton
        var buttonMap = findViewById(R.id.map_button) as ImageButton

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
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }
        buttonMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }


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