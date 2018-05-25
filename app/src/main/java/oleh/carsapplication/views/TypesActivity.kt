package oleh.carsapplication.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_types.*
import oleh.carsapplication.R
import oleh.carsapplication.adapters.MyAdapter
import oleh.carsapplication.adapters.OnItemClickListener
import oleh.carsapplication.api.responses.MyPair
import oleh.carsapplication.viewModels.TypesViewModel

class TypesActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_types)

        val key = intent.extras.get("key") as String
        val value = intent.extras.get("value") as String
        manufacturer.text = value
        val typesViewModel = ViewModelProviders.of(this).get(TypesViewModel::class.java)
        typesViewModel.getTypes(key).observe(this, Observer<ArrayList<MyPair>> {
            (typesList.adapter as MyAdapter).addData(it)
        })

        val itemClickListener: OnItemClickListener = object : OnItemClickListener {
            override fun onClick(pair: MyPair) {
                typesViewModel.getTypes(key).observe(this@TypesActivity, Observer<ArrayList<MyPair>> {
                    val myIntent = Intent(this@TypesActivity, DatesActivity::class.java)
                    myIntent.putExtra("key", key)
                    myIntent.putExtra("value", value)
                    myIntent.putExtra("typeKey", pair.key)
                    myIntent.putExtra("typeValue", pair.value)
                    startActivity(myIntent)
                })
            }
        }

        typesList.adapter = MyAdapter(this, itemClickListener)
        typesList.layoutManager = LinearLayoutManager(this)
        typesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!typesViewModel.canLoadMore() || typesViewModel.isLoading)
                    return
                val firstVisible = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val total = recyclerView.adapter.itemCount
                if (total - firstVisible <= 11) {
                    typesViewModel.loadTypes(key, typesViewModel.currentPage + 1)
                }
            }
        })
    }
}