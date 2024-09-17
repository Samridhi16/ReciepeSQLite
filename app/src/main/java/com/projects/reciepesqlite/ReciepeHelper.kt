package com.projects.reciepesqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class ReciepeHelper(context:Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + "$ID_COL INTEGER PRIMARY KEY, " +
                "$NAME_COL TEXT," +
                "$DESCRIPTION_COL TEXT," +
                "$PRICE_COL DOUBLE" + ")")
        db.execSQL(query)
        Log.i("msg","CREATED")
    }
     fun insertData(name: String,description: String, price: Double){
            val values = ContentValues()
            values.put(NAME_COL,name)
            values.put(DESCRIPTION_COL,description)
            values.put(PRICE_COL,price)
            // here we are creating a writable variable of our database as we want to insert value in our database
            val db = this.writableDatabase
            db.insert(TABLE_NAME,null,values)
            Log.i("msg","ADDED")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun updateData(name: String, description: String, newPrice: Double){
        val db = this.writableDatabase
        val values = ContentValues()

        // Set the new value for the price
        values.put(PRICE_COL, newPrice)

        // Update the row where both name and description match
        db.update(
            TABLE_NAME,
            values,
            "$NAME_COL = ? AND $DESCRIPTION_COL = ?",
            arrayOf(name, description)
        )
    }
    fun deleteData(id: Int): Int {
        val db = this.writableDatabase

        // Delete the row where the ID matches the given id
        val rowsDeleted = db.delete(TABLE_NAME, "$ID_COL = ?", arrayOf(id.toString()))

        // Log the result for debugging purposes
        if (rowsDeleted > 0) {
            Log.i("msg", "Record deleted successfully")
        } else {
            Log.i("msg", "No matching record found")
        }

        return rowsDeleted
    }



    fun getData(): Cursor{
        val db = this.readableDatabase
        return db.rawQuery("SELECT $NAME_COL,$PRICE_COL FROM $TABLE_NAME",null)
    }

    companion object{
        private val DATABASE_NAME = "recipedb"
        private val DATABASE_VERSION = 5
        val TABLE_NAME = "PRODUCTS"
        val ID_COL = "id"
        val NAME_COL = "NAME"
        val DESCRIPTION_COL ="DESCRIPTION"
        val PRICE_COL = "PRICE"
    }
}