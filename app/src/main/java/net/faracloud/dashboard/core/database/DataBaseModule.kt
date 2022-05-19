package net.faracloud.dashboard.core.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DashboardDatabase =
        DashboardDatabase.create(context)

    @Provides
    fun provideDao(database: DashboardDatabase): ComponentDao {
        return database.componentDao()
    }
}*/


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
    fun provideComponentDao(db: DashboardDatabase): ComponentDao{
        return db.componentDao()
    }

    @Provides
    fun provideProviderDao(db: DashboardDatabase): ProviderDao{
        return db.providerDao()
    }

    @Provides
    fun provideSensorDao(db: DashboardDatabase): SensorDao{
        return db.sensorDao()
    }

    @Provides
    fun provideSensorObservationDao(db: DashboardDatabase): SensorObservationDao{
        return db.sensorObservationDao()
    }


}
