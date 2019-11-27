package com.maisonlacroix.projetfinaltehnique.chatkit.chat

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.maisonlacroix.projetfinaltehnique.R
import com.maisonlacroix.projetfinaltehnique.chatkit.ChatkitManager
import com.pusher.chatkit.CurrentUser
import com.pusher.chatkit.messages.multipart.Message
import com.pusher.chatkit.presence.Presence
import com.pusher.chatkit.users.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), ChatPresenter.View {
    private lateinit var adapter: MessageAdapter

    private val presenter = ChatPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        presenter.onViewAttached(this)

        if (ChatkitManager.currentUser != null) {
            //set up our recyclerview adapter
            adapter = MessageAdapter(ChatkitManager.currentUser!!.id)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.stackFromEnd = true
            recyclerViewMessages.layoutManager =  layoutManager
            recyclerViewMessages.adapter = adapter

            //handle sending messages
            txtMessage.setOnEditorActionListener { _, actionId, _ ->
                if (txtMessage.length() > 0 && actionId == EditorInfo.IME_ACTION_SEND){
                    presenter.sendMessageToRoom(txtMessage.text.toString())
                    txtMessage.setText("")
                    true
                } else {
                    false
                }
            }

            val supplierId = intent.getStringExtra("supplier_id")

            //tell our presenter to connect as the seller user
            presenter.connect(supplierId)
        } else {
            onError("Current user was not found - have you signed in?")
        }

    }

    override fun onError(exception: String) {
        runOnUiThread {
            txtMessage.isEnabled = false
            lblError.text = exception
            recyclerViewMessages.visibility = View.GONE
        }

    }

    override fun onConnected(person: CurrentUser) {
        runOnUiThread {
            Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayPresence(user: User) {
        val presence = user.presence
        //Picasso.get().load("http://3.15.151.13/expressShop/assets/img/ESLogo2_Black_NoBg_NoText_Reverse_278x155.png").into(imgStatus)
        Picasso.get().load("http://3.15.151.13/expressShop/assets/img/" + user.customData!!["Guid"]).into(imgStatus)
        if (presence == Presence.Online) {
            imgStatus.setColorFilter(ContextCompat.getColor(applicationContext, R.color.online_color))
        } else {
            imgStatus.setColorFilter(ContextCompat.getColor(applicationContext, R.color.offline_color))
        }
    }

    override fun onOtherMember(person: User) {
        runOnUiThread {
            lblName.text = person.name
            displayPresence(person)
        }

    }

    override fun onMemberPresenceChanged(person: User) {
        runOnUiThread {
            displayPresence(person)
        }
    }

    override fun onMessageReceived(message: Message) {
        runOnUiThread {
            adapter.addMessage(message)
            recyclerViewMessages.layoutManager?.scrollToPosition(adapter.messages.size -1)
        }
    }

}
