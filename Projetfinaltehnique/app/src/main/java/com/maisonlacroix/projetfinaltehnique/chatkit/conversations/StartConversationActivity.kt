package com.maisonlacroix.projetfinaltehnique.chatkit.conversations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.maisonlacroix.projetfinaltehnique.R
import com.maisonlacroix.projetfinaltehnique.chatkit.ChatkitManager
import com.maisonlacroix.projetfinaltehnique.chatkit.chat.ChatActivity
import com.pusher.chatkit.rooms.Room
import com.pusher.chatkit.users.User
import kotlinx.android.synthetic.main.activity_start_conversation.*
import org.json.JSONArray

class StartConversationActivity : AppCompatActivity() {

    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_conversation)
    }

    override fun onResume() {
        super.onResume()
        ChatkitManager.getPossibleConv(this) {
            displayUsers(it)
        }
    }

    fun displayUsers(people: JSONArray) {
        val context = this

        // Load and display all suppliers that do not have an active conversation with currUser from the db
        adapter = PersonAdapter(context, object:PersonAdapterListener{
            // Happens when a supplier is selected
            override fun onPersonSelected(person: User) {
                // Ensure that the supplier exists
                ChatkitManager.createUser(context, person.id, "S", person.name!!, person.customData!!["Guid"].toString()) {
                    // Create a room for the currUser and the selected supplier (if one does not already exist)
                    ChatkitManager.createConversation(person.id, person.name!!) {
                        // Start chatting with the supplier
                        val chatIntent = Intent(context, ChatActivity::class.java)
                        chatIntent.putExtra("supplier_id", person.id)
                        context.startActivity(chatIntent)
                    }
                }
            }
        })
        recyclerViewSuppliers.layoutManager =  LinearLayoutManager(this)
        recyclerViewSuppliers.adapter = adapter

        val dummyRoom = Room(
            "dummy", "dummy", "dummy", true,
            null, null, null, "dummy",
            "dummy", "dummy"
        )

        for (i in 0 until people.length()) {
            val supplier = people.getJSONObject(i)
            adapter.addPerson(
                User(
                    id = "S${supplier.getString("iduser")}",
                    createdAt = "", updatedAt = "",
                    name = supplier.getString("nomutilisateur"),
                    avatarURL = null,
                    customData = mapOf("Guid" to supplier.getString("imgGUID"))
                ),
                dummyRoom
            )
        }
    }
}
