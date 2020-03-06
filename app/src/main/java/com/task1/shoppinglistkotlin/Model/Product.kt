package com.task1.shoppinglistkotlin.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Product Data class
 */

@Parcelize
@Entity(tableName = "product_table")

data class Product(
// genereted id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int

) : Parcelable