package com.easycoding.todou.util

/**
 * Handy util to make sure that for example `when` process all cases
 * It's very useful when you add new items in Enum/Sealed classes and
 * DO NOT want to forget to process brand-new cases in the whole app
 * Cause compilation error if `when` doesn't process all defined cases
 * You can use `else` branch to avoid compilation errors
 * */
val <T> T.exclusive: T
    get() = this