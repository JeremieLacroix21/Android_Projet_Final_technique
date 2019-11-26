package com.maisonlacroix.projetfinaltehnique.chatkit.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.maisonlacroix.projetfinaltehnique.R
import com.pusher.chatkit.messages.multipart.Message
import java.util.*
import kotlin.math.abs


class MessageAdapter(private val currentUserId: String)
    : androidx.recyclerview.widget.RecyclerView.Adapter<MessageViewHolder>() {

    var messages = mutableListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_message, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position], currentUserId)
    }

    fun addMessage(message: Message) {
        Log.println(Log.DEBUG, "", "==================MESSAGE ADDED")
        this.messages.add(message)
        notifyItemInserted(this.messages.size)
    }

    fun timeElapsedString(datetime: Date, full: Boolean) {
        val now = Date()
        val ago = datetime
        val diffMs = abs(now.time - ago.time)

    }
}