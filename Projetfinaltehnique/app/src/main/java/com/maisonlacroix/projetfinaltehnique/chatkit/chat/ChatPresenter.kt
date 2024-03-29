package com.maisonlacroix.projetfinaltehnique.chatkit.chat

import android.util.Log
import com.maisonlacroix.projetfinaltehnique.chatkit.BasePresenter
import com.maisonlacroix.projetfinaltehnique.chatkit.ChatkitManager
import com.pusher.chatkit.CurrentUser
import com.pusher.chatkit.messages.multipart.Message
import com.pusher.chatkit.rooms.Room
import com.pusher.chatkit.rooms.RoomListeners
import com.pusher.chatkit.users.User
import com.pusher.util.Result

class ChatPresenter :  BasePresenter<ChatPresenter.View>(){

    interface View {
        fun onError(exception: String)
        fun onConnected(person: CurrentUser)
        fun onOtherMember(person: User)
        fun onMemberPresenceChanged(person: User)
        fun onMessageReceived(message: Message)
    }

    private lateinit var room: Room

    fun connect(supplierId: String) {
        subscribeToRoom(supplierId)
    }

    private fun subscribeToRoom(supplierId: String) {
        if (ChatkitManager.currentUser == null) {
            handleError("Current user was not found - have you signed in?")
            return
        }

        val currUserIsDistrib = (ChatkitManager.currentUser!!.id[0] == 'D')
        var roomId = if (currUserIsDistrib) {
            "${ChatkitManager.currentUser!!.id}-$supplierId"
        } else {
            "$supplierId-${ChatkitManager.currentUser!!.id}"
        }
        room = ChatkitManager.currentUser!!.rooms.find { room -> room.id == roomId}!!

        //subscribe to the room
        ChatkitManager.currentUser!!.subscribeToRoomMultipart(
            roomId = room.id ,
            listeners = RoomListeners(
                onMultipartMessage = { message ->

                    view?.onMessageReceived(message)
                    updateReadCursor(room.id, message.id)
                },
                onPresenceChange = { person ->
                    if (isViewAttached() &&
                            person.id != ChatkitManager.currentUser!!.id) {
                        view?.onMemberPresenceChanged(person)
                    }
                }
            ),
            messageLimit = 20,
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
                        //check we actually have another user to talk to
                        val otherMember = members.find { user-> user.id != ChatkitManager.currentUser!!.id }
                        if (otherMember == null) {
                            handleError("could not find the other user to talk to - " +
                                    "have you created the sample data?")
                        } else {
                            view?.onOtherMember(otherMember)
                        }
                    }
                }

                is Result.Failure -> {
                    handleError(result.error.reason)
                }

            }
        })
    }

    fun sendMessageToRoom(message: String) {

        ChatkitManager.currentUser!!.sendSimpleMessage(room, message,
            callback = { result ->
                when (result) {

                    //we handle the success automatically by display the message

                    is Result.Failure -> {
                        handleError(result.error.reason)
                    }

                }
        })
    }

    private fun updateReadCursor(roomId: String ,messageId: Int) {
        ChatkitManager.currentUser!!.setReadCursor(roomId, messageId)
    }

    private fun handleError(error: String) {
        Log.e(ChatkitManager.LOG_TAG, error)
        view?.onError(error)
    }

}