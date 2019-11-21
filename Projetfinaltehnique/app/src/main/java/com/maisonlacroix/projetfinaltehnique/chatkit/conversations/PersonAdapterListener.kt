package com.maisonlacroix.projetfinaltehnique.chatkit.conversations

import com.pusher.chatkit.users.User

interface PersonAdapterListener {
    fun onPersonSelected(person: User)
}