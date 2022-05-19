package net.faracloud.dashboard.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import net.faracloud.dashboard.core.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [(ComponentEntity::class),
    ProviderEntity::class,
    SensorEntity::class,
    SensorObservationEntity::class],
    version = 6)
abstract class DashboardDatabase : RoomDatabase() {

    abstract fun componentDao(): ComponentDao
    abstract fun providerDao(): ProviderDao
    abstract fun sensorDao(): SensorDao
    abstract fun sensorObservationDao(): SensorObservationDao

    class Callback @Inject constructor(
        private val database: Provider<DashboardDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()

   /* companion object {
        fun create(context: Context): DashboardDatabase {

            return Room.databaseBuilder(
                context,
                DashboardDatabase::class.java,
                DB_NAME
            ).build()
        }
    }*/
}