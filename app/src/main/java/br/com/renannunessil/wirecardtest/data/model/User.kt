package br.com.renannunessil.wirecardtest.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    var login: String,
    var password: String,
    var token: String
): Parcelable