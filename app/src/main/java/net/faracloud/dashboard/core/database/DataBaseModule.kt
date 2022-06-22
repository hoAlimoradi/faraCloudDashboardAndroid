package net.faracloud.dashboard.core.database

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.faracloud.dashboard.core.database.doa.ComponentDao
import net.faracloud.dashboard.core.database.doa.ProviderDao
import net.faracloud.dashboard.core.database.doa.SensorDao
import net.faracloud.dashboard.core.database.doa.SensorObservationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: DashboardDatabase.Callback): DashboardDatabase{
        return Room.databaseBuilder(application, DashboardDatabase::class.java, "faracloud_dashboard_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideComponentDao(db: DashboardDatabase): ComponentDao {
        return db.componentDao()
    }

    @Provides
    fun provideProviderDao(db: DashboardDatabase): ProviderDao {
        return db.providerDao()
    }

    @Provides
    fun provideSensorDao(db: DashboardDatabase): SensorDao {
        return db.sensorDao()
    }

    @Provides
    fun provideSensorObservationDao(db: DashboardDatabase): SensorObservationDao {
        return db.sensorObservationDao()
    }


    @Provides
    fun provideTenantDao(db: DashboardDatabase): TenantDao{
        return db.tenantDao()
    }


}
