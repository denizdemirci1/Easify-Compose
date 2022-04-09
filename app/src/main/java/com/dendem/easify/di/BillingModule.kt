package com.dendem.easify.di

import android.content.Context
import com.dendem.easify.EasifyApp
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BillingModule {

    @Provides
    @Singleton
    fun provideBillingHelper(
        @ApplicationContext appContext: Context,
        defaultScope: CoroutineScope
    ): BillingHelper {
        return BillingHelper(
            (appContext as EasifyApp),
            defaultScope,
            arrayOf(Constants.PREMIUM_ACCOUNT)
        )
    }
}
