package co.zw.amosesuwali.dogplayground.api

import co.zw.amosesuwali.dogplayground.models.BreedRandomResponse
import co.zw.amosesuwali.dogplayground.models.ServerResponse
import co.zw.amosesuwali.dogplayground.models.Pictures
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://dog.ceo/api/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object DogCeoApi {
    val retrofitService: DogCeoApiService by lazy { retrofit.create(DogCeoApiService::class.java) }
}


interface DogCeoApiService {
    /**
     * Returns a [List] of [BreedRandomResponse] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("breeds/image/random/16")
    suspend fun getPhotos(): Pictures

    @GET("breeds/list/all")
    suspend fun getDogBreedList(): ServerResponse

    @GET("breed/{breedName}/images/random")
    suspend fun getDogBreedRandomImage( @Path("breedName")breedName:String): BreedRandomResponse
}

