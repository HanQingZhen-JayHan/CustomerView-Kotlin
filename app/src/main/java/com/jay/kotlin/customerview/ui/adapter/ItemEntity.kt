package com.jay.kotlin.customerview.ui.adapter

import android.app.Activity

data class ItemEntity(
    var cls: Class<*>,
    var image: Int,
    var title: String,
    var description: String
)