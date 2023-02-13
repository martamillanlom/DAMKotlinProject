package com.example.damkotlinproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referència de la bbdd
        val database = Firebase.database

        // Escriure un valor a una referència
        database.getReference("missatge").setValue("Hello, DAM2")

        // Escriure un objecte a una referència
        database.getReference("Llibre").setValue(Llibre("a","a",500))

        // Escriure un objecte a una referència (array)
        database.getReference("Llibres").push().setValue(Llibre("b","b",500))
        database.getReference("Llibres").push().setValue(Llibre("c","c",500))


        // Llegir un valor a una referència
        database.getReference("missatge").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                Log.i("FirebaseTest", "Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("FirebaseTest", "Failed to read value.", error.toException())
            }
        })

        // Llegir un objecte a una referència
        database.getReference("Llibre").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<Llibre>()
                Log.i("FirebaseTest", "Value is: " + value?.title + " " + value?.pages)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("FirebaseTest", "Failed to read value.", error.toException())
            }
        })

        // Llegir un objecte a una referència (array)
        database.getReference("Llibres").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot:DataSnapshot in snapshot.getChildren()) {
                    val llibre: Llibre? = snapshot.getValue<Llibre>()
                    Log.i("FirebaseTest", llibre?.title.toString());
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("FirebaseTest", "Failed to read value.", error.toException())
            }
        })
    }
}