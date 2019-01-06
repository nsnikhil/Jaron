package com.nsnik.nrs.jaron.dagger.modules

import android.content.Context
import androidx.room.Room
import com.nsnik.nrs.jaron.dagger.qualifiers.ApplicationQualifier
import com.nsnik.nrs.jaron.dagger.qualifiers.DatabaseName
import com.nsnik.nrs.jaron.dagger.scopes.ApplicationScope
import com.nsnik.nrs.jaron.data.ExpenseDatabase
import dagger.Module
import dagger.Provides

@Module(includes = [(ContextModule::class)])
class DatabaseModule {

    internal val databaseName: String
        @Provides
        @DatabaseName
        @ApplicationScope
        get() = DATABASE_NAME

    @Provides
    @ApplicationScope
    internal fun getNoteDatabase(@ApplicationQualifier context: Context, @DatabaseName @ApplicationScope databaseName: String): ExpenseDatabase =
        Room.databaseBuilder(context, ExpenseDatabase::class.java, databaseName)
            .fallbackToDestructiveMigration()
            .build()


    companion object {
        private const val DATABASE_NAME = "expenseDatabase"
    }

}