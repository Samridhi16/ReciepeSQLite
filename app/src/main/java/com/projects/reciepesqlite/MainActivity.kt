package com.projects.reciepesqlite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.projects.reciepesqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reciepeHelper = ReciepeHelper(this,null)

        reciepeHelper.insertData("Rice","2 lbs",20.0)
        reciepeHelper.insertData("XYZ","ABC",60.0)
        reciepeHelper.updateData("XYZ", "ABC", 19.99)
//        reciepeHelper.deleteData(2)
        val cursor = reciepeHelper.getData()

        var result = ""
        while (cursor.moveToNext()){
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ReciepeHelper.NAME_COL))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow(ReciepeHelper.PRICE_COL))
            result += "NAME: $name PRICE: $price\n"
        }

        binding.tvResult.text = result

    }
}