package oleh.carsapplication.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    private val serverUrl = "http://api-aws-eu-qa-1.auto1-test.com/v1/car-types/"
    private val testKey = "wa_key"
    private val testValue = "coding-puzzle-client-449cc9d"

    private val interceptor: Interceptor = Interceptor {
        var request = it.request()
        val url = request.url().newBuilder().addQueryParameter(testKey, testValue).build()
        request = request.newBuilder().url(url).build()
        it.proceed(request)
    }

    private var api = Retrofit.Builder().baseUrl(serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getHttpClient())
            .build()
            .create(Api::class.java)

    private fun getHttpClient() = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(StethoInterceptor())!!
            .build()

    fun getManufacturers(page: Int) = api.getManufacturers(page)

    fun getMainTypes(manufacturer: String, page: Int) = api.getMainTypes(manufacturer, page)

    fun getBuildDates(manufacturer: String, mainType: String) = api.getBuildDates(manufacturer, mainType)
}