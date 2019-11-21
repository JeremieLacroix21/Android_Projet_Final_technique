package com.maisonlacroix.projetfinaltehnique.chatkit.conversations

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.maisonlacroix.projetfinaltehnique.R
import com.maisonlacroix.projetfinaltehnique.chatkit.ChatkitManager
import com.maisonlacroix.projetfinaltehnique.chatkit.chat.ChatActivity
import com.pusher.chatkit.CurrentUser
import com.pusher.chatkit.rooms.Room
import com.pusher.chatkit.users.User
import kotlinx.android.synthetic.main.activity_conversations.*

class ConversationsActivity : AppCompatActivity(), ConversationsPresenter.View {

    private var currUserId: String = ""
    private var currUsername: String = ""
    private var currUserType: String = ""
    private var currUserGuid: String = ""
    private var initializing = true

    private val presenter = ConversationsPresenter()
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversations)

        presenter.onViewAttached(this)

        //TODO: Init the currUser properly OR use a global object
        currUserId = intent.getStringExtra("curr_user_id")
        ChatkitManager.getUserById(this, currUserId) {
            Log.println(Log.DEBUG, "", it.toString())
            currUsername = it.getString("nomutilisateur")
            currUserType = it.getString("TypeUser")
            currUserGuid = it.getString("imgGUID")

            currUserId = ChatkitManager.makeChatkitUserId(currUserId, currUserType)

            presenter.connectToChatkit(this, currUserId, currUserType, currUsername, currUserGuid)
            lblError.text = "connecting"
            containerContent2.visibility = View.GONE

            if (currUserId[0] == 'D') {
                FBTN_NewConv.visibility = View.VISIBLE
                val context = this
                FBTN_NewConv.setOnClickListener {
                    val startConvIntent = Intent(context, StartConversationActivity::class.java)
                    context.startActivity(startConvIntent)
                }
            }
        }
    }

    override fun onConnected(user: CurrentUser) {
        //display all the conversations
        runOnUiThread {
            containerContent2.visibility = View.VISIBLE
            lblError.visibility = View.GONE

            displayConversations(user.rooms)
            initializing = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (!initializing) {
            displayConversations(ChatkitManager.currentUser!!.rooms)
        }
    }

    fun displayConversations(rooms: List<Room>) {

        // A room id will always be formatted as follow : 'distribID-supplierID'
        // So to access a conversation between a distributor and a supplier, we only need their IDs
        val context = this
        // RecyclerView setup. Everytime a supplier is selected, the chat activity will be started.
        adapter = PersonAdapter(this, object:PersonAdapterListener{
            override fun onPersonSelected(person: User) {
                val chatIntent = Intent(context, ChatActivity::class.java)
                chatIntent.putExtra("supplier_id", person.id)
                context.startActivity(chatIntent)
                adapter.updateUnreadCountForPerson(person, 0)
            }
        })
        recyclerViewPeople.layoutManager =  LinearLayoutManager(this)
        recyclerViewPeople.adapter = adapter

        //currently we have to subscribe to the room to get the people but we can change this soon!
        for (room in rooms) {
            presenter.subscribeToRoom(room)
        }
    }

    override fun onError(error: String) {
        runOnUiThread {
            lblError.text = error
            lblError.visibility = View.VISIBLE
            containerContent2.visibility = View.GONE
        }
    }

    override fun onMemberPresenceChanged(user: User) {
        runOnUiThread {
            adapter.updatePresence(user)
        }
    }

    override fun onPerson(user: User, room: Room) {
        runOnUiThread {
            adapter.addPerson(user, room)
        }
    }

    override fun onUnreadCountChanged(room: Room) {
        runOnUiThread {
            var unreadCount = room.unreadCount
            if (unreadCount == null) {
                unreadCount = 0
            }
            adapter.updateUnreadCountForRoom(room.id, unreadCount)
        }
    }
}
