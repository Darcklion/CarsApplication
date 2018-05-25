package oleh.carsapplication.api

import io.reactivex.Observable
import oleh.carsapplication.api.responses.DatesResponse
import oleh.carsapplication.api.responses.ManufacturersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("manufacturer")
    fun getManufacturers(
            @Query("page") page: Int,
            @Query("pageSize") pageSize : Int = 15
    ): Observable<ManufacturersResponse>

    @GET("main-types")
    fun getMainTypes(
            @Query("manufacturer") manufacturer: String,
            @Query("page") page: Int,
            @Query("pageSize") pageSize : Int = 15
    ): Observable<ManufacturersResponse>

    @GET("built-dates")
    fun getBuildDates(
            @Query("manufacturer") manufacturer: String,
            @Query("main-type") mainType: String
    ): Observable<DatesResponse>
}