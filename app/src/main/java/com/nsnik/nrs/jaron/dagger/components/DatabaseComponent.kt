package com.nsnik.nrs.jaron.dagger.components

import com.nsnik.nrs.jaron.dagger.modules.DatabaseModule
import com.nsnik.nrs.jaron.dagger.scopes.ApplicationScope
import com.nsnik.nrs.jaron.util.DatabaseUtility
import dagger.Component

@ApplicationScope
@Component(modules = [(DatabaseModule::class)])
interface DatabaseComponent {
    val databaseUtility: DatabaseUtility
}