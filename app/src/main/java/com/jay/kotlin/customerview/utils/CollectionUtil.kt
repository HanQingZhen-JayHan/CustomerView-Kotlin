package com.jay.kotlin.customerview.utils

object CollectionUtil {

    fun isEmpty(var0: Collection<*>?): Boolean {
        return var0 == null || var0.isEmpty()
    }
}
