@file:Suppress("HasPlatformType")

package net.faracloud.dashboard.extentions

import android.annotation.SuppressLint
import android.database.Cursor

// Please Sort Alphabetically

@SuppressLint("Range")
fun Cursor.getIntValue(key: String): Int = getInt(getColumnIndex(key))

@SuppressLint("Range")
fun Cursor.getLongValue(key: String): Long = getLong(getColumnIndex(key))

@SuppressLint("Range")
fun Cursor.getStringValue(key: String): String? = getString(getColumnIndex(key))
