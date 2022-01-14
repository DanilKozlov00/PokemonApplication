package com.example.myapplication.Dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.myapplication.Data.PokemonInfo

@Database(entities = [PokemonInfo::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getPokemonDao(): PokemonInfoDao

   companion object {
       private var db_instance: AppDataBase?=null
       fun getAppDataBaseInstance(context: Context): AppDataBase {
           if (db_instance == null) {
               db_instance = Room.databaseBuilder<AppDataBase>(
                   context.applicationContext, AppDataBase::class.java, "app_db"
               )
                   .allowMainThreadQueries()
                   .build()
           }
           return db_instance!!
       }
   }

}