package com.example.counterapplication.counter

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class CounterUpdateTest {
    private val updateSpec = UpdateSpec<CounterModel, CounterEvent, Nothing>(CounterUpdate())

    @Test
    fun `when counter is incremented, then the model is incremented`() {
        updateSpec
            .given(CounterModel.ZERO)
            .whenEvent(Increment)
            .then(
                assertThatNext(
                    hasModel(CounterModel.ZERO.increment()),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when counter is decremented, then the model is decremented`() {
        updateSpec
            .given(CounterModel.ZERO)
            .whenEvent(Decrement)
            .then(
                assertThatNext(
                    hasModel(CounterModel.ZERO.decrement()),
                    hasNoEffects()
                )
            )
    }

    
}