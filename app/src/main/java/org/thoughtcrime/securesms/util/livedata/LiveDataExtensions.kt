package org.thoughtcrime.securesms.util.livedata

import androidx.lifecycle.LiveData

fun <T, R> LiveData<T>.distinctUntilChanged(selector: (T) -> R): LiveData<T> = LiveDataUtil.distinctUntilChanged(this, selector)
