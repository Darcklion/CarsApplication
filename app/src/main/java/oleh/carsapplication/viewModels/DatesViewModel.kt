package oleh.carsapplication.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import oleh.carsapplication.api.DataManager
import oleh.carsapplication.api.responses.MyPair

class DatesViewModel: ViewModel() {
    private var dates: MutableLiveData<ArrayList<MyPair>>? = null

    fun getDates(manufacturer:String, type: String): LiveData<ArrayList<MyPair>> {
        if (dates == null) {
            dates = MutableLiveData()
            loadDates(manufacturer, type)
        }

        return dates as MutableLiveData<ArrayList<MyPair>>
    }

    private fun loadDates(manufacturer:String, type: String) {
        DataManager.getBuildDates(manufacturer, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    dates?.postValue(it.list)
                }
    }
}