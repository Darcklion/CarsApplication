package oleh.carsapplication.api.responses

import android.support.v4.util.ArrayMap

data class DatesResponse ( val wkda: ArrayMap<String, String>,
                           var list: ArrayList<MyPair>)