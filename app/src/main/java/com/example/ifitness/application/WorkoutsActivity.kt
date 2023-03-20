package com.example.ifitness.application

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.example.ifitness.R

class WorkoutsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workouts_activity)

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
            val intent = Intent(this, WorkoutsActivity::class.java)
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
// Adaugarea unui antrenament in baza de date
val workout = Workout("Chest Day", "04/03/2023")
val db = FirebaseFirestore.getInstance()
val workoutRef = db.collection("workouts").document()
workoutRef.set(workout)

// Adaugarea unui exercitiu in cadrul unui antrenament
val exercise = Exercise("Bench Press", 3, 10)
val workoutRef = db.collection("workouts").document(workoutId)
val exercisesRef = workoutRef.collection("exercises")
exercisesRef.add(exercise)

// Afisarea antrenamentelor si exercitiilor intr-un RecyclerView
val workoutList = ArrayList<Workout>()
val workoutAdapter = WorkoutAdapter(workoutList)
val layoutManager = LinearLayoutManager(this)
recyclerView.layoutManager = layoutManager
recyclerView.adapter = workoutAdapter

val db = FirebaseFirestore
val workoutRef = db.collection("workouts")
workoutRef.addSnapshotListener { snapshot, e ->
if (e != null) {
// Handle error
return@addSnapshotListener
}
workoutList.clear()
for (doc in snapshot!!.documents) {
    val workout = doc.toObject(Workout::class.java)
    workoutList.add(workout)
}
workoutAdapter.notifyDataSetChanged()
}

// Adaugarea unui antrenament sau exercitiu cu ajutorul unui AlertDialog
val builder = AlertDialog.Builder(this)
builder.setTitle("Add Workout")

val view = layoutInflater.inflate(R.layout.dialog_add_workout, null)
builder.setView(view)

val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
val addExerciseButton = view.findViewById<Button>(R.id.addExerciseButton)

addExerciseButton.setOnClickListener {
// Adaugarea unui nou exercitiu in AlertDialog
}

builder.setPositiveButton("Save") { dialog, which ->
val name = nameEditText.text.toString()
val date = dateTextView.text.toString()
val workout = Workout(name, date)
val db = FirebaseFirestore.getInstance()
db.collection("workouts").add(workout)
}

builder.setNegativeButton("Cancel") { dialog, which ->
dialog.cancel()
}

val dialog = builder.create()
dialog.show()

// Selectarea datei cu ajutorul unui DatePicker
val calendar = Calendar.getInstance()
val year = calendar.get(Calendar.YEAR)
val month = calendar.get(Calendar.MONTH)
val day = calendar.get(Calendar.DAY_OF_MONTH)

val datePickerDialog = DatePickerDialog(this,
DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
// Salvarea datei selectate
val date = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year)
dateTextView.text = date
}, year, month, day)

datePickerDialog.show()

 */







/*
class ExerciseListActivity : AppCompatActivity() {

    // referinte catre elementele din layout
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    // referinta catre baza de date Firebase
    private lateinit var database: FirebaseDatabase

    // referinta catre nodul din baza de date unde sunt stocate exercitiile
    private lateinit var exercisesRef: DatabaseReference

    // lista de exercitii
    private val exerciseList = mutableListOf<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        // initializare referinte catre elementele din layout
        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        // initializare referinta catre baza de date Firebase
        database = FirebaseDatabase.getInstance()

        // initializare referinta catre nodul din baza de date unde sunt stocate exercitiile
        exercisesRef = database.getReference("exercises")

        // creare adaptor pentru recycler view
        val adapter = ExerciseListAdapter(exerciseList)

        // setare adaptor pentru recycler view
        recyclerView.adapter = adapter

        // setare layout manager pentru recycler view
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ascultare eveniment pentru a afisa lista de exercitii
        exercisesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                exerciseList.clear()
                for (exerciseSnapshot in dataSnapshot.children) {
                    val exercise = exerciseSnapshot.getValue(Exercise::class.java)
                    exerciseList.add(exercise!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "onCancelled: $databaseError")
            }
        })

        // ascultare eveniment pentru a adauga un exercitiu in lista
        addButton.setOnClickListener {
            val exercise = Exercise("New exercise")
            exercisesRef.push().setValue(exercise)
        }

        // adaugare functionalitate pentru stergerea unui exercitiu din lista prin swipe
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val exercise = exerciseList[position]
                exercisesRef.child(exercise.id!!).removeValue()
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    companion object {
        private const val TAG = "ExerciseListActivity"
    }
}

 */










/*

import com.google.firebase.database.*

class WorkoutManager(private val database: FirebaseDatabase) {

    fun addWorkout(workout: Workout, userId: String, callback: () -> Unit) {
        val workoutsRef = database.getReference("workouts/$userId")
        val workoutId = workoutsRef.push().key ?: return
        workout.id = workoutId
        workoutsRef.child(workoutId).setValue(workout).addOnSuccessListener {
            callback()
        }
    }

    fun getWorkouts(userId: String, callback: (List<Workout>) -> Unit) {
        val workoutsRef = database.getReference("workouts/$userId")
        workoutsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workouts = mutableListOf<Workout>()
                for (workoutSnapshot in snapshot.children) {
                    val workout = workoutSnapshot.getValue(Workout::class.java)
                    workout?.let {
                        workouts.add(it)
                    }
                }
                callback(workouts)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}

 */


















/*

class WorkoutsFragment : Fragment() {

    private lateinit var workoutManager: WorkoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Firebase.database
        workoutManager = WorkoutManager(database)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Afisam lista cu antrenamente utilizand RecyclerView si apelam getWorkouts pentru a obtine datele din baza de date
        workoutManager.getWorkouts(userId) { workouts ->
            // Afisam datele obtinute in RecyclerView
        }
    }

    private fun addWorkout() {
        // Creem un obiect de tipul Workout cu datele introduse de utilizator si apelam addWorkout pentru a-l adauga in baza de date
        val workout = Workout(...)
        workoutManager.addWorkout(workout, userId) {
            // Antrenamentul a fost adaugat cu succes in baza de date
        }
    }
}

 */