package com.getir.homework5

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import android.util.Log

// Page 31
fun exampleChannelSenderReceiver() = runBlocking {
    val channel = Channel<Int>()

    launch {
        Log.v("selen", "sender started")
        channel.send(10)
        Log.v("selen", "value sent: 10")
    }

    launch {
        Log.v("selen", "receiver started")
        val value = channel.receive()
        Log.v("selen", "Value received: $value")
    }
}

// Page 32
fun exampleChannelSenderReceiverMultiple() = runBlocking {
    val channel = Channel<String>()

    launch {
        Log.v("selen", "sender started")
        for (i in 1..5) {
            channel.send("Message $i")
            Log.v("selen", "Message sent: Message $i")
        }
    }

    launch {
        Log.v("selen", "Receiver started")
        while (true){
            val message = channel.receive()
            Log.v("selen", "Message received: $message")
        }
    }
}
