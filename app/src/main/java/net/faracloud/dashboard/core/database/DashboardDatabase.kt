package net.faracloud.dashboard.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import net.faracloud.dashboard.core.database.doa.ComponentDao
import net.faracloud.dashboard.core.database.doa.ProviderDao
import net.faracloud.dashboard.core.database.doa.SensorDao
import net.faracloud.dashboard.core.database.doa.SensorObservationDao
import net.faracloud.dashboard.core.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [(ComponentEntity::class),
    ProviderEntity::class,
    TenantEntity::class,
    SensorEntity::class,
    SensorObservationEntity::class],
    version = 10)
abstract class DashboardDatabase : RoomDatabase() {

    abstract fun componentDao(): ComponentDao
    abstract fun providerDao(): ProviderDao
    abstract fun sensorDao(): SensorDao
    abstract fun sensorObservationDao(): SensorObservationDao
    abstract fun tenantDao(): TenantDao

    class Callback @Inject constructor(
        private val database: Provider<DashboardDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()

}