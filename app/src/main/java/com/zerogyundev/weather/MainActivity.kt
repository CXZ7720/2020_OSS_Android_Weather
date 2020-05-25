package com.zerogyundev.weather

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import com.github.kittinunf.fuel.*
import com.github.kittinunf.result.Result


class MainActivity : AppCompatActivity() {

    var Seoul: City = City("Seoul", "", "")
    var Daejeon: City = City("Daejeon", "", "")
    var Daegu: City = City("Daegu", "", "")
    var Busan: City = City("Busan", "", "")

    var cityList = arrayListOf<City>(
        Seoul,
        Daejeon,
        Daegu,
        Busan
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val cityAdapter = CityListViewAdapter(this, cityList)
        inputList.adapter = cityAdapter

        inputList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as City
            val intent = Intent(this,WeatherResultActivity::class.java).apply {
                putExtra("City", selectItem.name)
            }

            startActivity(intent)
        }

        add_button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_setting, null)
            val SeoulCheck = dialogView.findViewById<CheckBox>(R.id.Seoul)
            val DaejeonCheck = dialogView.findViewById<CheckBox>(R.id.Daejeon)
            val DaeguCheck = dialogView.findViewById<CheckBox>(R.id.Daegu)
            val BusanCheck = dialogView.findViewById<CheckBox>(R.id.Busan)

            val settingBtn = builder.setView(dialogView).setPositiveButton("Confirm") { _, _ ->
                var city_setting_val: ArrayList<City> = arrayListOf()



                if(SeoulCheck.isChecked){
                    city_setting_val.add(Seoul)
                }
                if(DaejeonCheck.isChecked){
                    city_setting_val.add(Daejeon)
                }
                if(DaeguCheck.isChecked){
                    city_setting_val.add(Daegu)
                }
                if(BusanCheck.isChecked){
                    city_setting_val.add(Busan)
                }

                inputList.adapter = CityListViewAdapter(this, city_setting_val)
            }.setNegativeButton("Cancel") {dialogInterface, i ->
                //cancel button do nothing.
            }
                .show()
                .getButton(DialogInterface.BUTTON_POSITIVE)


        }


    }


}
