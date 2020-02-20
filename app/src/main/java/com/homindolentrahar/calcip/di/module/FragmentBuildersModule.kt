package com.homindolentrahar.calcip.di.module

import com.homindolentrahar.calcip.main.fragment.MainFragment
import com.homindolentrahar.calcip.main.fragment.ResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeResultFragment(): ResultFragment
}