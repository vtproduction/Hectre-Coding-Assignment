package com.midsummer.orchardjob.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.pojo.FieldConfig
import com.midsummer.orchardjob.pojo.OrchardJob

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
@Database(entities = [OrchardJob::class, FieldConfig::class], version = Constants.DB_VERSION)
@TypeConverters(Converters::class)
abstract class OrchardJobDatabase : RoomDatabase() {

    abstract fun orchardJobDAO() : OrchardJobDAO

    companion object {
        @Volatile // to make sure that all threads have immediate access to this property
        private var instance: OrchardJobDatabase? = null

        private val lock = Any() // to make sure that there will be no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                OrchardJobDatabase::class.java,
                Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }

}