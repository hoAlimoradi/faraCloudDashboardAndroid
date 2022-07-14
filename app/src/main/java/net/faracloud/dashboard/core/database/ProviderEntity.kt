package net.faracloud.dashboard.core.database


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import net.faracloud.dashboard.core.DateUtil
import java.util.*

@Parcelize
@Entity(tableName = "provider_table")
data class ProviderEntity(
   // @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @PrimaryKey(autoGenerate = false)
    var providerId: String,
    val tenantName: String,
    var authorizationToken: String,
    var createDate: String,
    var enable: Boolean,
    var lastUpdateDevice: String
): Parcelable {
    val formattedCreateDate : String get() {
        createDate?.let {
            return DateUtil.changeDateFormat(createDate)
        }
        return ""
    }
}

