package com.example.ifitness.application
/*
class UserProfileActivity : AppCompatActivity() {

    // referinte catre elementele din layout
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var saveButton: Button

    // referinta catre baza de date Firebase
    private lateinit var database: FirebaseDatabase

    // referinta catre nodul din baza de date unde sunt stocate informatiile utilizatorului
    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // initializare referinte catre elementele din layout
        nameTextView = findViewById(R.id.nameTextView)
        ageTextView = findViewById(R.id.ageTextView)
        heightTextView = findViewById(R.id.heightTextView)
        weightTextView = findViewById(R.id.weightTextView)
        saveButton = findViewById(R.id.saveButton)

        // initializare referinta catre baza de date Firebase
        database = FirebaseDatabase.getInstance()

        // initializare referinta catre nodul din baza de date unde sunt stocate informatiile utilizatorului
        userRef = database.getReference("users").child("user_id_123")

        // ascultare eveniment pentru a afisa informatiile utilizatorului
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                nameTextView.text = user?.name
                ageTextView.text = user?.age.toString()
                heightTextView.text = user?.height.toString()
                weightTextView.text = user?.weight.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "onCancelled: $databaseError")
            }
        })

        // ascultare eveniment pentru a salva informatiile utilizatorului
        saveButton.setOnClickListener {
            val name = nameTextView.text.toString()
            val age = ageTextView.text.toString().toInt()
            val height = heightTextView.text.toString().toDouble()
            val weight = weightTextView.text.toString().toDouble()
            val user = User(name, age, height, weight)
            userRef.setValue(user)
        }
    }

    companion object {
        private const val TAG = "UserProfileActivity"
    }

}

 */










/*
import com.google.firebase.database.*

class ProgressManager(private val database: FirebaseDatabase) {

    fun addProgress(progress: Progress, userId: String, callback: () -> Unit) {
        val progressRef = database.getReference("progress/$userId")
        val progressId = progressRef.push().key ?: return
        progress.id = progressId
        progressRef.child(progressId).setValue(progress).addOnSuccessListener {
            callback()
        }
    }

    fun getProgress(userId: String, callback: (List<Progress>) -> Unit) {
        val progressRef = database.getReference("progress/$userId")
        progressRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val progressList = mutableListOf<Progress>()
                for (progressSnapshot in snapshot.children) {
                    val progress = progressSnapshot.getValue(Progress::class.java)
                    progress?.let {
                        progressList.add(it)
                    }
                }
                callback(progressList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}

 */






/*
class ProgressFragment : Fragment() {

    private lateinit var progressManager: ProgressManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Firebase.database
        progressManager = ProgressManager(database)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Afisam lista cu progrese utilizand RecyclerView si apelam getProgress pentru a obtine datele din baza de date
        progressManager.getProgress(userId) { progressList ->
            // Afisam datele obtinute in RecyclerView
        }
    }

    private fun addProgress() {
        // Creem un obiect de tipul Progress cu datele introduse de utilizator si apelam addProgress pentru a-l adauga in baza de date
        val progress = Progress(...)
        progressManager.addProgress(progress, userId) {
            // Progresul a fost adaugat cu succes in baza de date
        }
    }
}

 */