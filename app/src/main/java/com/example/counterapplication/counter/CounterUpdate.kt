package com.example.counterapplication.counter

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

class CounterUpdate : Update<CounterModel, CounterEvent, Nothing> {

    override fun update(model: CounterModel, event: CounterEvent): Next<CounterModel, Nothing> {
        return when (event) {
            Increment -> next(model.increment())
            Decrement -> next(model.decrement())
        }
    }
}
