package com.example.prm_02

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_options.*
import java.util.*
import kotlin.collections.ArrayList

class OptionsActivity : AppCompatActivity() {

    var colorsArray: MutableList<String> = ArrayList(
        listOf(
                                    "BLACK",
                                    "BLUE",
                                    "GREEN",
                                    "CYAN",
                                    "DARK_GRAY",
                                    "GRAY",
                                    "LIGHT_GRAY",
                                    "MAGENTA",
                                    "RED",
                                    "WHITE",
                                    "YELLOW")
    )

    var colorsIntArray: MutableList<Int> = ArrayList(
        listOf(
            Color.BLACK,
            Color.BLUE,
            Color.GREEN,
            Color.CYAN,
            Color.DKGRAY,
            Color.GRAY,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)


        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colorsArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        textColor.adapter = adapter
    }

    fun saveOptionsOnClick(view: View) {
        var intent = Intent()
        if(!textSize.text.toString().equals(""))
            intent.putExtra("size", textSize.text.toString().toFloat())
        if(!textColor.selectedItem.toString().equals(""))
            intent.putExtra("color", colorsIntArray.get(colorsArray.indexOf(textColor.selectedItem.toString())))
        intent.putExtra("r", notificationR.text)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}