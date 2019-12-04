package com.example.counterapplication.counter

sealed class CounterEvent

object Increment : CounterEvent()

object Decrement : CounterEvent()
