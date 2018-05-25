package oleh.carsapplication.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import oleh.carsapplication.api.DataManager
import oleh.carsapplication.api.responses.MyPair

class TypesViewModel: ViewModel() {

    private var types: MutableLiveData<ArrayList<MyPair>>? = null
    private var allLoadedTypes: ArrayList<MyPair> = ArrayList()

    var currentPage: Int = 0
    var totalPages: Int = Int.MAX_VALUE
    var isLoading: Boolean = false

    fun getTypes(manufacturer:String): LiveData<ArrayList<MyPair>> {
        if (allLoadedTypes.size == 0)
            loadTypes(manufacturer, 0)

        types = MutableLiveData()
        types?.postValue(allLoadedTypes)

        return types as MutableLiveData<ArrayList<MyPair>>
    }

    fun loadTypes(manufacturer:String, page: Int) {
        isLoading = true
        DataManager.getMainTypes(manufacturer, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    isLoading = false
                    currentPage = it.page
                    totalPages = it.totalPageCount
                    allLoadedTypes.addAll(it.list)
                    types?.postValue(it.list)
                }
    }

    fun canLoadMore() = currentPage != totalPages
}