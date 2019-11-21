package com.maisonlacroix.projetfinaltehnique.chatkit.conversations

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maisonlacroix.projetfinaltehnique.R
import com.pusher.chatkit.rooms.Room
import com.pusher.chatkit.users.User

data class Person(val roomId: String, var person: User, var unreadCount: Int)

class PersonAdapter(private val context: Context, val listener: PersonAdapterListener) : RecyclerView.Adapter<PersonViewHolder>() {

    private var people = mutableListOf<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PersonViewHolder {
        return PersonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_person, parent, false), listener
        )
    }

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        holder.bind(person.person, person.unreadCount, context)
    }

    fun addPerson(person: User, room: Room) {

        var unreadCount = room.unreadCount
        if (unreadCount == null) {
            unreadCount = 0
        }

        people.add(Person(room.id, person, unreadCount))
        notifyItemInserted(people.size)
    }

    fun updatePresence(person: User) {
        for (p in people) {
            if (p.person.id == person.id) {
               p.person = person
                notifyItemChanged(people.indexOf(p))
            }
        }
    }

    fun updateUnreadCountForPerson(person: User, unreadCount: Int) {
        for (p in people) {
            if (p.person.id == person.id) {
                p.unreadCount = unreadCount
                notifyItemChanged(people.indexOf(p))
            }
        }
    }

    fun updateUnreadCountForRoom(roomId: String, unreadCount: Int) {
        for (p in people) {
            if (p.roomId == roomId) {
                p.unreadCount = unreadCount
                notifyItemChanged(people.indexOf(p))
            }
        }
    }



}