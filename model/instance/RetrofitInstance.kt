package model.instance

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import service.ApiService
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.time.LocalDate
import java.time.LocalTime
 import com.google.gson.*
 import java.lang.reflect.Type

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8000/"


    private val gson: Gson = GsonBuilder()
        // сериализатор и десериализатор для LocalDate
        .registerTypeAdapter(LocalDate::class.java,
            object : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
                override fun serialize(
                    src: LocalDate,
                    typeOfSrc: Type,
                    context: JsonSerializationContext
                ): JsonElement = JsonPrimitive(src.toString())

                override fun deserialize(
                    json: JsonElement,
                    typeOfT: Type,
                    context: JsonDeserializationContext
                ): LocalDate = LocalDate.parse(json.asString)
            }
        )
        // сериализатор и десериализатор для LocalTime
        .registerTypeAdapter(LocalTime::class.java,
            object : JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
                override fun serialize(
                    src: LocalTime,
                    typeOfSrc: Type,
                    context: JsonSerializationContext
                ): JsonElement = JsonPrimitive(src.toString())

                override fun deserialize(
                    json: JsonElement,
                    typeOfT: Type,
                    context: JsonDeserializationContext
                ): LocalTime = LocalTime.parse(json.asString)
            }
        )
        .create()

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            // 2) Передаём наш кастомный gson в конвертер
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}