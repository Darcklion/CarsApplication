package oleh.carsapplication.api.responses

import android.util.ArrayMap

data class ManufacturersResponse (
            val page: Int,
            val pageSize: Int,
            val totalPageCount: Int,
            val wkda: ArrayMap<String, String>,
            var list: ArrayList<MyPair>
)