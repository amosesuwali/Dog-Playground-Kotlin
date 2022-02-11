package co.zw.amosesuwali.dogplayground.database.app

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedDao
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedEntity

@Database(entities = [(FavBreedEntity::class)],
    version = 2, )

abstract class AppDatabase: RoomDatabase() {

    abstract fun favBreedDao(): FavBreedDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}