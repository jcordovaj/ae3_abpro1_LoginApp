package com.mod6.ae3_abpro1_loginapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Función auxiliar para obtener el valor actual de un LiveData durante las pruebas unitarias.
 * Espera hasta que LiveData emita un valor, o lanza TimeoutException si no ocurre.
 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    // Observer para capturar el valor
    this.observeForever(observer)

    // Espera hasta que LiveData emita un valor
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("Nunca se estableció el valor de LiveData " +
                "dentro del tiempo de espera.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}