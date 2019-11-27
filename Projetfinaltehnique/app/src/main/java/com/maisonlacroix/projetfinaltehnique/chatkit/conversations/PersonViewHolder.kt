package com.maisonlacroix.projetfinaltehnique.chatkit.conversations

import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.maisonlacroix.projetfinaltehnique.R
import com.pusher.chatkit.presence.Presence
import com.pusher.chatkit.users.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.row_person.view.*

class PersonViewHolder (itemView: View, val listener: PersonAdapterListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User, unreadCount: Int, context: Context){

        itemView.lblProfile.text = user.name

        Picasso.get().load("http://3.15.151.13/expressShop/assets/img/" + user.customData!!["Guid"]).into(itemView.imgProfile)
        if (user.presence == Presence.Online) {
            itemView.imgProfile.setColorFilter(ContextCompat.getColor(context, R.color.online_color))
        } else {
            itemView.imgProfile.setColorFilter(ContextCompat.getColor(context, R.color.offline_color))
        }

        if (unreadCount == 0) {
            itemView.lblUnreadCount.visibility = View.GONE
        } else if (unreadCount > 99) {
            itemView.lblUnreadCount.text = "100+"
        } else {
            itemView.lblUnreadCount.text = unreadCount.toString()
        }

        itemView.setOnClickListener {
            listener.onPersonSelected(user)
        }

    }

}