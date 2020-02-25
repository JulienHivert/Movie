package com.example.allocinoche

import android.os.Parcel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeSafe(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer<T> { t ->
        t?.let(observer)
    })
}

fun Parcel.writeIntList(input:List<Int>) {
    writeInt(input.size) // Save number of elements.
    input.forEach(this::writeInt) // Save each element.
}

fun Parcel.createIntList() : List<Int> {
    val size = readInt()
    val output = ArrayList<Int>(size)
    for (i in 0 until size) {
        output.add(readInt())
    }
    return output
}