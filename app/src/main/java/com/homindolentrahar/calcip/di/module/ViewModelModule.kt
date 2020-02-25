package com.homindolentrahar.calcip.di.module

import androidx.lifecycle.ViewModelProvider
import com.homindolentrahar.calcip.util.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelProviderFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
//    In case you need to inject ViewModel,provide it here with ViewModelKey
}