package com.sebix.unittesting.di;

import android.app.Application;

import com.sebix.unittesting.BaseApplication;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                AcitvityBuildersModule.class,
                ViewModelFactoryModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface  Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
