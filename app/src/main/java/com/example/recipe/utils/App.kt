package com.example.recipe.utils

import android.app.Application
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


class App : Application() {
    //Calligraphy
    override fun onCreate() {
        super.onCreate()
        ViewPump.init(
            ViewPump.builder().addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/atlas_regular.ttf")
                            .build()
                    )
                ).build()
        )
    }
}