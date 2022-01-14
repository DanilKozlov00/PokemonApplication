package com.example.myapplication.di



import android.app.Application
import android.content.Context
import com.example.myapplication.Dao.AppDataBase
import com.example.myapplication.Dao.PokemonInfoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (val application: Application) {


    @Singleton
    @Provides
    fun getPokemonDao(appDataBase: AppDataBase): PokemonInfoDao {
        return appDataBase.getPokemonDao()
    }


    @Singleton
    @Provides
    fun getRoomDbInstance(): AppDataBase {
        return AppDataBase.getAppDataBaseInstance(provideAppContext())
    }


    @Singleton
    fun provideAppContext():Context {
        return application.applicationContext
    }
}