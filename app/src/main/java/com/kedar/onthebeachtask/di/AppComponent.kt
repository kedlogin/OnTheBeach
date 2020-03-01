package com.kedar.onthebeachtask.di

import com.kedar.onthebeachtask.dashboard.FlightsFragment
import com.kedar.onthebeachtask.dashboard.HotelsFragment
import com.kedar.onthebeachtask.dashboard.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class,APIClientModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(): AppComponent
    }

    fun inject(activity: FlightsFragment)
    fun inject(activity: HotelsFragment)
}