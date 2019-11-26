package com.maisonlacroix.projetfinaltehnique.chatkit

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.pusher.chatkit.*
import com.pusher.chatkit.ChatManager
import com.pusher.util.Result
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

object ChatkitManager {

    private const val INSTANCE_LOCATOR = "v1:us1:b11e2a1a-b943-4206-9800-c883a9855b69"
    private const val TOKEN_PROVIDER_URL = "https://us1.pusherplatform.io/services/chatkit_token_provider/v1/b11e2a1a-b943-4206-9800-c883a9855b69/token"
    //TODO: Change api url once it is migrated on the AWS
    private const val API_URL = "http://3.15.151.13/Laravel/api"
    const val LOG_TAG = "DEMO_APP"

    private lateinit var chatManager: ChatManager
    var currentUser: CurrentUser? = null

    interface ChatManagerConnectedListener{
        fun onConnected(user: CurrentUser)
        fun onError(error: String)
    }

    fun connect(context: Context, userId: String, userType: String, userName: String, userGuid: String, listener: ChatManagerConnectedListener) {

        //check if we're already connected
        if (currentUser != null){
            //if we already have a current user let's sign them out first
            chatManager.close {
                when (it) {
                    is Result.Success -> {
                        connectToChatkit(context, userId, userType, userName, userGuid, listener)
                    }
                    is Result.Failure -> {
                        listener.onError(it.error.reason)
                    }
                }
            }
        } else {
            connectToChatkit(context, userId, userType, userName, userGuid, listener)
        }

    }

    fun makeChatkitUserId(id: String, userType: String): String {
        var resultUserId = id

        //TODO: Replace harcoded user types values with global constants
        resultUserId = if (userType == "D" || userType == "Distributeur") {
            "D$id"
        } else if (userType == "S" || userType == "Fournisseur") {
            "S$id"
        } else {
            throw Exception("Invalid user type")
        }

        return resultUserId
    }

    fun createConversation(supplierId: String, supplierName: String, onSuccess: ()->Unit) {
        if ((currentUser!!.id[0] != 'D') || (supplierId[0] != 'S')) {
            throw Exception("The current user is not a distributor or the given supplierId is invalid")
        }

        val roomId = "${currentUser!!.id}-$supplierId"
        val roomName = "Conversation ${currentUser!!.name}-$supplierName"

        // Check if the conversation exists
        if (currentUser!!.rooms.any { r -> r.id == roomId }) {
            Log.println(Log.DEBUG, "", "Could not create room: $roomId already exists")
            onSuccess()
        } else {
            currentUser!!.createRoom(
                roomId,
                roomName,
                true,
                null,
                listOf(currentUser!!.id, supplierId),
                callback = {result ->
                    when (result) {
                        is Result.Success -> {
                            Log.println(Log.DEBUG, "", "A room was created successfully: $roomName")
                            onSuccess()
                        }
                        is Result.Failure -> {
                            Log.println(Log.ERROR, "", result.error.reason)
                        }
                    }
                }
            )
        }
    }

    fun getPossibleConv(context: Context, onSuccess: (JSONArray)->Unit) {
        // ( °͜ʖ°)
        var inRoomWithIds = ""
        this.currentUser!!.rooms.forEach {
            // Extract the supplierId from the roomId
            inRoomWithIds += it.id.split('-')[1] + ";"
        }
        if (inRoomWithIds != "") {
            // Remove the last delimiter
            inRoomWithIds = inRoomWithIds.substring(0, inRoomWithIds.length - 1)
        }

        val queue = Volley.newRequestQueue(context)

        val params = JSONObject()
        params.put("suppliersInConv", inRoomWithIds)

        val req = JsonObjectRequest(
            Request.Method.POST,
            "$API_URL/GetAllNotInConvSuppliers",
            params,
            Response.Listener { res ->
                Log.println(Log.DEBUG, "", res.getJSONArray("users").toString())
                onSuccess(res.getJSONArray("users"))
            },
            Response.ErrorListener { err ->
                Log.println(Log.ERROR, "", err.toString())
            }
        )

        queue.add(req)
    }

    // Will create a chatkit user if the user does not exist
    fun createUser(context: Context, id: String, type: String, name: String, guid: String, onSuccess: ()->Unit) {
        val queue = Volley.newRequestQueue(context)

        val params = JSONObject()
        params.put("IdUser", id)
        params.put("Nom", name)
        params.put("Guid", guid)

        val req = JsonObjectRequest(
            Request.Method.POST,
            "$API_URL/CreateUser",
            params,
            Response.Listener { res ->
                Log.println(Log.DEBUG, "", "========================== SUCCESS CREATE USER ==========================")
                Log.println(Log.DEBUG, "", res.toString())
                onSuccess()
            },
            Response.ErrorListener { err ->
                Log.println(Log.DEBUG, "", "========================== ERROR CREATE USER ==========================")
                Log.println(Log.ERROR, "", "$err")
            }
        )

        queue.add(req)
    }

    // IMPORTANT : The param is NOT a chatkit id
    fun getUserById(context: Context, id: String, onSuccess: (JSONObject)->Unit) {
        val queue = Volley.newRequestQueue(context)

        val params = JSONObject()
        params.put("user_id", id)

        val req = JsonObjectRequest(
            Request.Method.POST,
            "$API_URL/GetUserForChat",
            params,
            Response.Listener { res ->
                Log.println(Log.DEBUG, "", res.toString())
                onSuccess(res)
            },
            Response.ErrorListener { err ->
                Log.println(Log.ERROR, "", err.toString())
            }
        )

        queue.add(req)
    }

    private fun connectToChatkit(context: Context, userId: String, userType: String, userName: String, userGuid: String, listener: ChatManagerConnectedListener) {

        createUser(context, userId, userType, userName, userGuid) {
            //set up your chat manager with your instance locator and token provider
            chatManager = ChatManager(
                instanceLocator = INSTANCE_LOCATOR,
                userId = userId,
                dependencies = AndroidChatkitDependencies(
                    tokenProvider = ChatkitTokenProvider(
                        endpoint = TOKEN_PROVIDER_URL,
                        userId = userId
                    ),
                    context = context
                )
            )

            //connect to chatkit
            chatManager.connect(
                listeners = ChatListeners(),
                callback = { result ->
                    when (result) {
                        is Result.Success -> {
                            result.value.let { user ->
                                currentUser = user
                                listener.onConnected(user)
                            }
                        }

                        is Result.Failure -> {
                            listener.onError(result.error.reason)
                        }
                    }
                }
            )
        }
    }
}