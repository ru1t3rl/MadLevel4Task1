package tech.tucano.madlevel4task1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    var name: String,
    var quantity: Int
): Parcelable