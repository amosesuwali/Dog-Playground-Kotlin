package co.zw.amosesuwali.dogplayground.database.app

import android.app.Application

class DogPlayGroundApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}