package oleh.carsapplication.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import oleh.carsapplication.api.DataManager
import oleh.carsapplication.api.responses.MyPair

class ManufacturersViewModel : ViewModel() {
    private var manufacturers: MutableLiveData<ArrayList<MyPair>>? = null
    private var allManufacturers: ArrayList<MyPair> = ArrayList()

    var currentPage: Int = 0
    var totalPages: Int = Int.MAX_VALUE
    var isLoading: Boolean = false

    fun getManufacturers(): LiveData<ArrayList<MyPair>> {
        if (allManufacturers.size == 0)
            loadManufacturers(0)

        manufacturers = MutableLiveData()
        manufacturers?.postValue(allManufacturers)

        return manufacturers as MutableLiveData<ArrayList<MyPair>>
    }

    fun loadManufacturers(page: Int) {
        isLoading = true
        DataManager.getManufacturers(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    isLoading = false
                    currentPage = it.page
                    totalPages = it.totalPageCount
                    allManufacturers.addAll(it.list)
                    manufacturers?.postValue(it.list)
                }
    }

    fun canLoadMore() = currentPage != totalPages
}