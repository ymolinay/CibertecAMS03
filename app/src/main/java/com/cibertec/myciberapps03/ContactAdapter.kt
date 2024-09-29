package com.cibertec.myciberapps03

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.myciberapps03.databinding.ItemContactBinding
import com.cibertec.myciberapps03.databinding.ItemEmptyBinding

class ContactAdapter(
    private val onDeleteClick: (Contact) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var contacts: List<Contact> = emptyList()

    // Constantes para identificar los tipos de vista
    private val VIEW_TYPE_CONTACT = 1
    private val VIEW_TYPE_EMPTY = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CONTACT) {
            // Inflamos el layout normal para los contactos
            val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ContactViewHolder(binding)
        } else {
            // Inflamos el layout para la vista de "No hay datos"
            val binding = ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EmptyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContactViewHolder) {
            val contact = contacts[position]
            holder.bind(contact, onDeleteClick)
        }
    }

    override fun getItemCount(): Int {
        // Si la lista está vacía, devolvemos 1 para mostrar la vista de "No hay datos"
        return if (contacts.isEmpty()) 1 else contacts.size
    }

    override fun getItemViewType(position: Int): Int {
        // Si la lista está vacía, mostramos el tipo de vista para "No hay datos"
        return if (contacts.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_CONTACT
    }

    fun submitList(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }
}

class ContactViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(contact: Contact, onDeleteClick: (Contact) -> Unit) {
        binding.textViewName.text = contact.name
        binding.textViewPhone.text = contact.phone

        binding.buttonDelete.setOnClickListener {
            onDeleteClick(contact)
        }
    }
}

class EmptyViewHolder(private val binding: ItemEmptyBinding)
    : RecyclerView.ViewHolder(binding.root) {

        fun bind() {}

    }