package oleh.carsapplication.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_dates.*
import oleh.carsapplication.R
import oleh.carsapplication.adapters.MyAdapter
import oleh.carsapplication.adapters.OnItemClickListener
import oleh.carsapplication.api.responses.MyPair
import oleh.carsapplication.viewModels.DatesViewModel

class DatesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dates)

        val key = intent.extras.get("key") as String
        val value = intent.extras.get("value") as String
        val typeKey = intent.extras.get("typeKey") as String
        val typeValue = intent.extras.get("typeValue") as String

        manufacturer.text = value
        type.text = typeValue
        val datesViewModel = ViewModelProviders.of(this).get(DatesViewModel::class.java)
        datesViewModel.getDates(key, typeKey).observe(this, Observer<ArrayList<MyPair>> {
            (datesList.adapter as MyAdapter).addData(it)
        })

        val itemClickListener: OnItemClickListener = object : OnItemClickListener {
            override fun onClick(pair: MyPair) {
                datesViewModel.getDates(key, typeKey).observe(this@DatesActivity, Observer<ArrayList<MyPair>> {
                    val myIntent = Intent(this@DatesActivity, ResultActivity::class.java)
                    myIntent.putExtra("value", value)
                    myIntent.putExtra("typeValue", typeValue)
                    myIntent.putExtra("dateValue", pair.value)
                    startActivity(myIntent)
                })
            }
        }

        datesList.adapter = MyAdapter(this, itemClickListener)
        datesList.layoutManager = LinearLayoutManager(this)
    }
}