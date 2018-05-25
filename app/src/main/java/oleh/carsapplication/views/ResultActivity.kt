package oleh.carsapplication.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*
import oleh.carsapplication.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val value = intent.extras.get("value") as String
        val typeValue = intent.extras.get("typeValue") as String
        val dateValue = intent.extras.get("dateValue") as String

        manufacturer.text = value
        type.text = typeValue
        date.text = dateValue

        button.setOnClickListener{
            val myIntent = Intent(this@ResultActivity, ManufacturersActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(myIntent)
        }
    }
}
