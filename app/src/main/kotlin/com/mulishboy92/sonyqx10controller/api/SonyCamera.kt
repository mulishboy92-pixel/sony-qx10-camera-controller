package com.mulishboy92.sonyqx10controller.api

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class SonyCamera(
    private val cameraUrl: String = "http://192.168.122.1:10000/sony/camera"
) {
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private var requestId = 1

    suspend fun getAvailableApiList(): Result<List<String>> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("getAvailableApiList")
            val response = executeRequest(jsonRequest)
            Result.success(listOf())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun startRecMode(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("startRecMode")
            executeRequest(jsonRequest)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun startLiveview(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("startLiveview")
            val response = executeRequest(jsonRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun stopLiveview(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("stopLiveview")
            executeRequest(jsonRequest)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actTakePicture(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("actTakePicture")
            val response = executeRequest(jsonRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun setShutterSpeed(speed: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("setShutterSpeed", arrayOf(speed))
            executeRequest(jsonRequest)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun setFNumber(fNumber: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("setFNumber", arrayOf(fNumber))
            executeRequest(jsonRequest)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun setIsoSpeedRate(iso: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("setIsoSpeedRate", arrayOf(iso))
            executeRequest(jsonRequest)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun setFocusMode(mode: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("setFocusMode", arrayOf(mode))
            executeRequest(jsonRequest)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEvent(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val jsonRequest = createJsonRequest("getEvent", arrayOf(true))
            val response = executeRequest(jsonRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun createJsonRequest(
        method: String,
        params: Array<Any> = emptyArray()
    ): String {
        val json = JsonObject().apply {
            addProperty("method", method)
            add("params", com.google.gson.JsonArray().also { arr ->
                params.forEach { param ->
                    when (param) {
                        is String -> arr.add(param)
                        is Boolean -> arr.add(param)
                        is Number -> arr.add(param)
                        else -> arr.add(param.toString())
                    }
                }
            })
            addProperty("id", requestId++)
            addProperty("version", "1.0")
        }
        return json.toString()
    }

    private fun executeRequest(jsonBody: String): String {
        val mediaType = "application/json".toMediaType()
        val request = Request.Builder()
            .url(cameraUrl)
            .post(jsonBody.toRequestBody(mediaType))
            .build()

        return httpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                response.body?.string() ?: ""
            } else {
                throw Exception("API Error: ${response.code}")
            }
        }
    }
}
