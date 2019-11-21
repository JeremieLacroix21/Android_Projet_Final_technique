package com.maisonlacroix.projetfinaltehnique.chatkit.conversations

import android.content.Context
import android.util.Log
import com.maisonlacroix.projetfinaltehnique.chatkit.BasePresenter
import com.maisonlacroix.projetfinaltehnique.chatkit.ChatkitManager
import com.pusher.chatkit.CurrentUser
import com.pusher.chatkit.rooms.Room
import com.pusher.chatkit.rooms.RoomListeners
import com.pusher.chatkit.users.User
import com.pusher.util.Result

class ConversationsPresenter : BasePresenter<ConversationsPresenter.View>() {

    interface View {
        fun onConnected(user: CurrentUser)
        fun onError(error: String)
        fun onMemberPresenceChanged(user: User)
        fun onPerson(user: User, room: Room)
        fun onUnreadCountChanged(room: Room)
    }

    fun connectToChatkit(context: Context, userId: String, userType: String, username: String, userGuid: String) {

        // Connect to Chatkit using the currUser id passed in parameters
        ChatkitManager.connect(
            context,
            userId,
            userType,
            username,
            userGuid,
            object : ChatkitManager.ChatManagerConnectedListener {
                override fun onConnected(user: CurrentUser) {
                    view?.onConnected(user)
                }

                override fun onError(error: String) {
                    view?.onError(error)
                }
            })
    }

    fun subscribeToRoom(room: Room) {

        if (ChatkitManager.currentUser == null) {
            view?.onError("Current user was not found - have you signed in?")
            Log.e(ChatkitManager.LOG_TAG, "Current user was not found - have you signed in?")
            return
        }

        //subscribe to the room
        ChatkitManager.currentUser!!.subscribeToRoomMultipart(
            roomId = room.id ,
            listeners = RoomListeners(
                onPresenceChange = { person ->
                    if (person.id != ChatkitManager.currentUser!!.id) {
                        view?.onMemberPresenceChanged(person)
                    }
                },
                onMultipartMessage = {
                    view?.onUnreadCountChanged(it.room)
                }
            ),
            messageLimit = 0,
            callback = { subscription ->
                //success
                getMembersForRoom(room)
            }
        )
    }

    private fun getMembersForRoom(room: Room){
        //get members for room
        ChatkitManager.currentUser!!.usersForRoom( room, callback = { result ->
            when (result) {
                is Result.Success -> {
                    result.value.let { members ->
                        //TODO: A room with only 1 user should still be accessible. To be implemented: A room will be deleted when the last member in that room leaves.
                        //check we actually have another user to talk to
                        val otherMember = members.find { user-> user.id != ChatkitManager.currentUser!!.id }
                        if (otherMember == null) {
                            val error = "Couldn't find any other people in room " + room.name
                            Log.e(ChatkitManager.LOG_TAG, error)
                            view?.onError(error)
                        } else {
                            view?.onPerson(otherMember, room)
                        }
                    }
                }

                is Result.Failure -> {
                    Log.e(ChatkitManager.LOG_TAG, result.error.reason)
                    view?.onError(result.error.reason)
                }

            }
        })
    }
}