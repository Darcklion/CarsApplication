package oleh.carsapplication.api

import io.reactivex.Observable
import oleh.carsapplication.api.responses.MyPair

object DataManager {

    private var apiManager = ApiManager()

    fun getManufacturers(page: Int) = apiManager.getManufacturers(page)
            .map{
                val response = it
                if (response.list == null)
                    response.list = ArrayList()
                Observable.fromIterable(it.wkda.keys.toList())
                        .subscribe {
                            val value = response.wkda.get(it) as String
                            response.list.add(MyPair(it, value))
                        }
                it }!!

    fun getMainTypes(manufacturer: String, page: Int) = apiManager.getMainTypes(manufacturer, page)
            .map{
                val response = it
                if (response.list == null)
                    response.list = ArrayList()
                Observable.fromIterable(it.wkda.keys.toList())
                        .subscribe {
                            val value = response.wkda.get(it) as String
                            response.list.add(MyPair(it, value))
                        }
                it }!!

    fun getBuildDates(manufacturer: String, mainType: String) = apiManager.getBuildDates(manufacturer, mainType)
            .map{
                val response = it
                if (response.list == null)
                    response.list = ArrayList()
                Observable.fromIterable(it.wkda.keys.toList())
                        .subscribe {
                            val value = response.wkda.get(it) as String
                            response.list.add(MyPair(it, value))
                        }
                it }!!
}