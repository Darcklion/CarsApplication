package oleh.carsapplication.adapters

import oleh.carsapplication.api.responses.MyPair

interface OnItemClickListener {
    fun onClick(pair: MyPair)
}