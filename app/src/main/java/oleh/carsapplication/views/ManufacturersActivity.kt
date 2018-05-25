package oleh.carsapplication.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_manufacturers.*
import oleh.carsapplication.R
import oleh.carsapplication.adapters.MyAdapter
import oleh.carsapplication.adapters.OnItemClickListener
import oleh.carsapplication.viewModels.ManufacturersViewModel
import android.content.Intent
import oleh.carsapplication.api.responses.MyPair

class ManufacturersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manufacturers)

        val manufacturersViewModel = ViewModelProviders.of(this).get(ManufacturersViewModel::class.java)
        manufacturersViewModel.getManufacturers().observe(this, Observer<ArrayList<MyPair>> {
            (list.adapter as MyAdapter).addData(it)
        })

        val itemClickListener: OnItemClickListener = object : OnItemClickListener {
            override fun onClick(pair: MyPair) {
                val myIntent = Intent(this@ManufacturersActivity, TypesActivity::class.java)
                myIntent.putExtra("key", pair.key)
                myIntent.putExtra("value", pair.value)
                startActivity(myIntent)
            }
        }

        list.adapter = MyAdapter(this, itemClickListener)
        list.layoutManager = LinearLayoutManager(this)
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!manufacturersViewModel.canLoadMore() || manufacturersViewModel.isLoading)
                    return
                val firstVisible = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val total = recyclerView.adapter.itemCount
                if (total - firstVisible <= 11) {
                    manufacturersViewModel.loadManufacturers(manufacturersViewModel.currentPage + 1)
                }
            }
        })
    }
}
