package com.maisonlacroix.projetfinaltehnique.chatkit.chat

import android.view.View
import android.widget.TextView
import com.pusher.chatkit.messages.multipart.Message
import com.pusher.chatkit.messages.multipart.Payload
import kotlinx.android.synthetic.main.row_message.view.*
import java.text.SimpleDateFormat
import java.util.*

class MessageViewHolder (itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun bind(message: Message, currentUserId: String){
        val msgDateFormat = SimpleDateFormat("yyyy-MM-dd | HH:mm")
        msgDateFormat.timeZone = Calendar.getInstance().timeZone;
        val inlineMessage: Payload.Inline = message.parts[0].payload as Payload.Inline

        // Workaround for old messages that show up randomly
        itemView.lblMessageFromYou.visibility = View.INVISIBLE
        itemView.lblMessageFromOther.visibility = View.INVISIBLE
        itemView.lblYouMessageDate.visibility = View.INVISIBLE
        itemView.lblOtherMessageDate.visibility = View.INVISIBLE

        val displays =
            if (message.sender.id == currentUserId) arrayOf(itemView.lblMessageFromYou, itemView.lblYouMessageDate)
            else arrayOf(itemView.lblMessageFromOther, itemView.lblOtherMessageDate)

        displays.forEach {
            it.visibility  = View.VISIBLE
        }
        displays[0].text = inlineMessage.content
        displays[1].text = msgDateFormat.format(message.createdAt)
    }

}