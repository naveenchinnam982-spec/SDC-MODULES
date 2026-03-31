package project.offline_firstappwithsynchronization

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("notes")
    suspend fun uploadNote(@Body note: Note): Response<Unit>
}