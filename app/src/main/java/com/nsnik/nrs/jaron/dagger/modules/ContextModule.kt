package com.nsnik.nrs.jaron.dagger.modules

import android.content.Context
import com.nsnik.nrs.jaron.dagger.qualifiers.ApplicationQualifier
import com.nsnik.nrs.jaron.dagger.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    @Provides
    @ApplicationQualifier
    @ApplicationScope
    internal fun provideContext(): Context {
        return this.context
    }
}