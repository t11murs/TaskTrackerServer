package service

import model.request.LoginRequest
import model.request.RegisterRequest
import model.request.TaskRequest
import model.response.RegisterResponse
import model.response.TaskResponse
import model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<ResponseBody>

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): Response<ResponseBody>

    @GET("/user/me")
    suspend fun getMe(@Header("Authorization") token: String): Response<UserResponse>

    @GET("/task")
    suspend fun getTasks(@Header("Authorization") token: String): Response<List<TaskResponse>>

    @POST("/task")
    suspend fun createTask(
        @Body request: TaskRequest,
        @Header("Authorization") token: String
    ): Response<TaskResponse>

    @PUT("task/{id}")
    suspend fun updateTask(
        @Path("id") taskId: Long,
        @Body body: TaskRequest,
        @Header("Authorization") authHeader: String
    ): Response<Unit>

    @DELETE("task/{id}")
    suspend fun deleteTask(
        @Path("id") id: Long,
        @Header("Authorization") bearer: String
    ): Response<Unit>
}