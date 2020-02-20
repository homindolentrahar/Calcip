package com.homindolentrahar.calcip.di.component

import android.app.Application
import com.homindolentrahar.calcip.BaseApplication
import com.homindolentrahar.calcip.di.module.ActivityBuildersModule
import com.homindolentrahar.calcip.di.module.AppModule
import com.homindolentrahar.calcip.di.module.FragmentBuildersModule
import com.homindolentrahar.calcip.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        FragmentBuildersModule::class,
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}