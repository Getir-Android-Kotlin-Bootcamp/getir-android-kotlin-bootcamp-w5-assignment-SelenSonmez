package com.getir.homework5

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.textView)

        // Page 6
        exampleCoroutineLaunch()

        // Page 7
        exampleAsyncAwait()

        // Page 8
        exampleBlockingCoroutine()

        // Page 9
        exampleUpdateUI(textView)

        // Page 10
        exampleNetworkCall()

        // Page 11
        exampleCoroutineDefaultDispatcher()

        // Page 12
        exampleWithContext()

        // Page 13
        exampleCancelJob()

        // Page 15
        val numbers = getNumbers(1000)
        Log.v("selen", numbers.toString())

        // Page 16
        exampleFlowWithCollect()

        // Page 17
        exampleScopeLaunch()

        // Page 18
        exampleSupervisorJob()

        // Page 20
        exampleMainScope()

        // Page 23
        runBlocking {
            examplePage23()
        }

        // Page 24
        runBlocking {
            examplePage24()
        }

        // Page 25
        runBlocking {
            examplePage25()
        }

        // Page 26
        runBlocking {
            examplePage26()
        }

        // Page 27
        runBlocking {
            examplePage27()
        }

        // Page 28
        runBlocking {
            examplePage28()
        }

        // Page 31 and Page 32: Call examples from the ChannelExamples file
        exampleChannelSenderReceiver()
        exampleChannelSenderReceiverMultiple()
    }

    // Page 6
    private fun exampleCoroutineLaunch() {
        Log.v("selen", "Example for page 6")
        CoroutineScope(Dispatchers.IO).launch {
            delay(10000)
            Log.v("selen", "Coroutine Tamamlandı")
        }
    }

    // Page 7
    private fun exampleAsyncAwait() {
        Log.v("selen", "Example for page 7")
        val deferred = GlobalScope.async {
            "merhaba!"
        }
        runBlocking {
            val mesaj = deferred.await()
            Log.v("selen", mesaj)
        }
    }

    // Page 8
    private fun exampleBlockingCoroutine() {
        Log.v("selen", "Example for page 8")
        runBlocking {
            delay(5000)
            Log.v("selen", "coroutine completed")
        }
    }

    // Page 9
    private fun exampleUpdateUI(textView: TextView) {
        Log.v("selen", "Example for page 9")
        CoroutineScope(Dispatchers.Main).launch {
            val text = "UI güncellendi"
            textView.text = text
            Log.v("selen", "TextView Güncellendi")
        }
    }

    // Page 10
    private fun exampleNetworkCall() {
        Log.v("selen", "Example for page 10")
        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://65c38b5339055e7482c12050.mockapi.io/api/products"
            val response = URL(url).readText()
            Log.v("selen", response)
        }
    }

    // Page 11
    private fun exampleCoroutineDefaultDispatcher() {
        Log.v("selen", "Example for page 11")
        CoroutineScope(Dispatchers.Default).launch {
            var sum = 0
            for (i in 0..10) {
                sum += i
            }
            Log.v("selen", "sum $sum")
        }
    }

    // Page 12
    private fun exampleWithContext() {
        Log.v("selen", "Example for page 12")
        val url = "https://65c38b5339055e7482c12050.mockapi.io/api/products"

        CoroutineScope(Dispatchers.IO).launch {
            val response = withContext(Dispatchers.IO) {
                URL(url).readText()
            }
            Log.v("selen", "Veri $response")
        }
    }

    // Page 13
    private fun exampleCancelJob() {
        Log.v("selen", "Example for page 13")
        val scopeExample = CoroutineScope(Dispatchers.IO)
        val job = scopeExample.launch {
            // Your task here
        }
        job.cancel()
    }

    // Page 15
    private fun getNumbers(delay: Long): List<Int> {
        Log.v("selen", "Example for page 15")
        val numbers = mutableListOf<Int>()
        for (i in 1..10) {
            Thread.sleep(delay)
            numbers.add(i)
        }
        return numbers
    }

    // Page 16
    private fun exampleFlowWithCollect() {
        Log.v("selen", "Example for page 16")
        val flow = getNumbersFlow(1000)
        CoroutineScope(Dispatchers.IO).launch {
            flow.collect { number ->
                Log.v("selen", number.toString())
            }
        }
    }

    private fun getNumbersFlow(delay: Long): Flow<Int> {
        return flow {
            for (i in 1..10) {
                emit(i)
                delay(delay)
            }
        }
    }

    // Page 17
    private fun exampleScopeLaunch() {
        Log.v("selen", "Example for page 17")
        val scopeLaunch = CoroutineScope(Dispatchers.IO)
        scopeLaunch.launch {
            // Start the coroutine
            delay(2000) // Simulating some work
            Log.v("selen", "Coroutine completed")
        }
    }

    // Page 18
    private fun exampleSupervisorJob() {
        Log.v("selen", "Example for page 18")
        val supervisorJob = SupervisorJob()
        val scopePage18 = CoroutineScope(supervisorJob)

        scopePage18.launch {
            // Coroutine 1
            delay(2000)
            Log.v("selen", "Coroutine 1 completed")
        }

        scopePage18.launch {
            // Coroutine 2
            delay(1000)
            Log.v("selen", "Coroutine 2 completed")
        }
    }

    // Page 20
    private fun exampleMainScope() {
        Log.v("selen", "Example for page 20")
        val uiScope = MainScope()
        uiScope.launch {
            // UI coroutine
            delay(2000)
            Log.v("selen", "UI Coroutine completed")
        }

        // Cancelling the UI scope after a delay
        GlobalScope.launch {
            delay(3000)
            uiScope.cancel()
            Log.v("selen", "UI Scope cancelled")
        }
    }

    // Page 23
    private fun examplePage23() {
        Log.v("selen", "Example for Page 23")
        runBlocking {
            val flow = flow {
                emit(1)
                emit(2)
                emit(3)
            }

            flow.collect { value -> Log.v("selen", value.toString()) }
        }
    }

    // Page 24
    private fun examplePage24() {
        Log.v("selen", "Example for Page 24")
        runBlocking {
            val numbers = listOf(1, 2, 3)
            val flow = numbers.asFlow()

            flow.collect { value -> Log.v("selen", value.toString()) }
        }
    }

    // Page 25
    private fun examplePage25() {
        Log.v("selen", "Example for Page 25")
        runBlocking {
            val numbersFlow = flow {
                emit(1)
                emit(2)
                emit(3)
            }

            val squaresFlow = numbersFlow.map { number -> number * number }

            squaresFlow.collect { square -> Log.v("selen", square.toString()) }
        }
    }

    // Page 26
    private fun examplePage26() {
        Log.v("selen", "Example for Page 26")
        runBlocking {
            val numbersFlow = flow {
                emit(1)
                emit(2)
                emit(3)
                emit(4)
                emit(5)
            }

            val oddFlow = numbersFlow.filter { number -> number % 2 == 1 }

            oddFlow.collect { odd -> Log.v("selen", "odd") }
        }
    }

    // Page 27
    private fun examplePage27() {
        Log.v("selen", "Example for Page 27")
        runBlocking {
            (1..3).asFlow()
                .transform { request ->
                    emit("Making Request $request")
                    emit(performRequest(request))
                }
                .collect { response -> Log.v("selen", response) }
        }
    }

    private suspend fun performRequest(request: Int): String {
        delay(1000)
        return "response $request"
    }

    // Page 28
    private fun examplePage28() {
        Log.v("selen", "Example for Page 28")
        runBlocking {
            val numbersFlow = flow {
                emit(1)
                emit(2)
                emit(3)
                emit(4)
                emit(5)
            }

            numbersFlow
                .flowOn(Dispatchers.IO)
                .collect { number -> Log.v("selen", number.toString()) }
        }
    }
}
