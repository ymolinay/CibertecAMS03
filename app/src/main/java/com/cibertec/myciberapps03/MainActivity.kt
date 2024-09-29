package com.cibertec.myciberapps03

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cibertec.myciberapps03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        }

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: ContactDatabase
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflamos el layout con View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = ContactDatabase.getDatabase(this)
        adapter = ContactAdapter { contact ->
            deleteContact(contact)
        }

        // Configuramos el RecyclerView usando binding
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Cargar los contactos iniciales
        loadContacts()

        // Configurar el evento Swipe to Refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadContacts()  // Vuelve a cargar los contactos
            binding.swipeRefreshLayout.isRefreshing = false  // Detiene el indicador de actualización
        }

        // Configuramos el botón FAB para agregar nuevos contactos
        binding.fab.setOnClickListener {
            result.launch(Intent(this, AddContactActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Volvemos a cargar la lista de contactos al regresar a la actividad
        loadContacts()
    }

    private fun loadContacts() {
        // Obtener los contactos de la base de datos y actualizarlos en el RecyclerView
        val contacts = database.contactDao().getAllContacts()
        adapter.submitList(contacts)
    }

    private fun deleteContact(contact: Contact) {
        // Eliminar el contacto de la base de datos
        database.contactDao().delete(contact)
        // Volver a cargar los contactos para actualizar la lista
        loadContacts()
    }
}