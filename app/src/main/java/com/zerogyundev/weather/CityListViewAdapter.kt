package com.zerogyundev.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.BaseAdapter
import android.widget.ImageView

import android.widget.TextView


class CityListViewAdapter (val context: Context, val cityList: ArrayList<City>): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.city_item, null)

        val cityName = view.findViewById<TextView>(R.id.CityName)



        val city = cityList[position]

        cityName.text = "• ${city.name}"


        return view
    }

    override fun getItem(position: Int): Any {
        return cityList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return cityList.size
    }


}