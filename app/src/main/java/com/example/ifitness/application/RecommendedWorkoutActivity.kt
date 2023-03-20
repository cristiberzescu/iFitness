package com.example.ifitness.application

class RecommendedWorkoutActivity {
}

/*
// Obtine videoclipurile relevante utilizand YouTube API
val apiKey = "YOUR_API_KEY"
val query = "Chest workout"
val url = "https://www.googleapis.com/youtube/v3/search?key=$apiKey&q=$query&part=snippet&maxResults=10"
val client = OkHttpClient()
val request = Request.Builder().url(url).build()

client.newCall(request).enqueue(object : Callback {
override fun onFailure(call: Call, e: IOException) {
// Handle error
}

override fun onResponse(call: Call, response: Response) {
    val json = response.body()?.string()
    val gson = Gson()
    val videos = gson.fromJson(json, VideoResponse::class.java).items

    // Salvarea videoclipurilor in baza de date
    val db = FirebaseFirestore.getInstance()
    for (video in videos) {
        val recommendedWorkout = RecommendedWorkout(query, "Chest", video.id.videoId)
        db.collection("recommendedWorkouts").add(recommendedWorkout)
    }
}
})

// Afisarea antrenamentelor recomandate utilizand un RecyclerView si WebView
val recommendedWorkoutRef = db.collection("recommendedWorkouts")
recommendedWorkoutRef.addSnapshotListener { snapshot, e ->
if (e != null) {
// Handle error
return@addSnapshotListener
}

recommendedWorkoutList.clear()
for (doc in snapshot!!.documents) {
    val recommendedWorkout = doc.toObject(RecommendedWorkout::class.java)
    recommendedWorkoutList.add(recommendedWorkout)
}
recommendedWorkoutAdapter.notifyDataSetChanged()
}

val recyclerView = findViewById<RecyclerView>(R.id.recommendedWorkoutsRecyclerView)
recyclerView.layoutManager = LinearLayoutManager(this)
recyclerView.adapter = recommendedWorkoutAdapter

recommendedWorkoutAdapter.setOnItemClickListener(object : RecommendedWorkoutAdapter.OnItemClickListener {
override fun onItemClick(recommendedWorkout: RecommendedWorkout) {
val videoId = recommendedWorkout.videoId
val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
startActivity(intent)
}
})
 */