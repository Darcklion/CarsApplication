package oleh.carsapplication.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import oleh.carsapplication.R
import oleh.carsapplication.api.responses.MyPair

class MyAdapter(context: Activity, private val listener: OnItemClickListener): RecyclerView.Adapter<MyAdapter.MyHolder>() {

    companion object {
        const val VIEW_EVEN = 0
        const val VIEW_ODD = 1
    }

    private var inflater: LayoutInflater = LayoutInflater.from(context)
    var list: ArrayList<MyPair> = ArrayList()

    fun addData(data: ArrayList<MyPair>?) {
        list.addAll(data!!)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) VIEW_EVEN else VIEW_ODD
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        return when (viewType) {
            VIEW_ODD -> MyHolder(inflater.inflate(R.layout.odd_row, parent, false))
            else -> MyHolder(inflater.inflate(R.layout.even_row, parent, false))
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder?.label?.text = list[position].value
        holder?.itemPosition = position
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val label: TextView = view.findViewById(R.id.label)
        var itemPosition: Int = 0

        override fun onClick(view: View) {
            listener.onClick(list[itemPosition])
        }

    }
}