package com.cibertec.myciberapps03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.myciberapps03.databinding.ActivityAddContactBinding

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private lateinit var database: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflamos el layout con View Binding
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = ContactDatabase.getDatabase(this)

        // Configuramos el botón para guardar el contacto
        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val contact = Contact(name = name, phone = phone)
                database.contactDao().insert(contact)
                finish() // Volvemos a la actividad anterior
            }
        }
    }
}