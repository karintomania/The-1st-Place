package com.bedroomcomputing.tobecomethebest.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "settings")
class Settings(
        @PrimaryKey val id: Int = 0,
        @ColumnInfo(name = "user_name") var userName: String = "",
        @ColumnInfo(name = "ranking_title") var rankingTitle: String = "Ranking",
        @ColumnInfo(name = "outstanding_rate") var outstandingRate: Int = 100,
        @ColumnInfo(name = "auto_play") var autoPlay: Boolean = false
): Parcelable
