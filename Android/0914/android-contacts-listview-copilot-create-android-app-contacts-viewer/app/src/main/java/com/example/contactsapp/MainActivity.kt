package com.example.contactsapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var searchView: SearchView
    private var adapter: ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewContacts)
        searchView = findViewById(R.id.searchView)

        checkContactsPermission()
    }

    private fun checkContactsPermission() {
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) {
            loadContactsAsync()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_CONTACTS) {
            val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (granted) {
                loadContactsAsync()
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadContactsAsync() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val contacts = loadContacts()
                withContext(Dispatchers.Main) {
                    setupListView(contacts)
                }
            } catch (_: SecurityException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, getString(R.string.permission_denied), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun loadContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )

        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

            while (cursor.moveToNext()) {
                val contactId = if (idIndex >= 0) cursor.getString(idIndex) else ""
                val name = if (nameIndex >= 0) cursor.getString(nameIndex).orEmpty() else ""
                if (name.isBlank()) continue

                val phone = getPhoneNumber(contactId)
                val email = getEmail(contactId)
                contacts.add(
                    Contact(
                        name = name,
                        phone = phone,
                        email = email
                    )
                )
            }
        }

        return contacts
    }

    private fun getPhoneNumber(contactId: String): String {
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
        val selectionArgs = arrayOf(contactId)

        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (numberIndex >= 0) {
                    return cursor.getString(numberIndex).orEmpty()
                }
            }
        }
        return ""
    }

    private fun getEmail(contactId: String): String {
        val projection = arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS)
        val selection = "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?"
        val selectionArgs = arrayOf(contactId)

        contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                if (emailIndex >= 0) {
                    return cursor.getString(emailIndex).orEmpty()
                }
            }
        }
        return ""
    }

    private fun setupListView(contacts: List<Contact>) {
        adapter = ContactAdapter(this, contacts)
        listView.adapter = adapter

        searchView.queryHint = getString(R.string.search_hint)
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter?.filter?.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return true
            }
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            val contact = adapter?.getItem(position) ?: return@setOnItemClickListener
            Toast.makeText(this, "${contact.name} - ${contact.phone}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_READ_CONTACTS = 1001
    }
}
