package com.example.counterapplication.counter

data class CounterModel(val value: Int) {

    companion object {
        val ZERO = CounterModel(0)
    }

    fun increment(): CounterModel {
        return copy(value = value + 1)
    }

    fun decrement(): CounterModel {
        return copy(value = value - 1)
    }
}
