package com.dendem.easify.presentation.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dendem.easify.MainDispatcherRule
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType
import com.dendem.easify.domain.use_case.history.GetHistoryUseCase
import com.dendem.easify.util.helper.SpotifyHelper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HistoryViewModel

    @MockK private lateinit var historyUseCase: GetHistoryUseCase
    @MockK private lateinit var spotifyHelper: SpotifyHelper
    @MockK private lateinit var billingHelper: BillingHelper

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun is_purchased_returns_correct_value() = runBlocking {
        coEvery { billingHelper.isPurchased(any()) } returns flow { emit(true) }
        every { historyUseCase.invoke(any()) } returns flowOf()
        viewModel = HistoryViewModel(historyUseCase, billingHelper, spotifyHelper)
        coVerify (exactly = 1) { viewModel.getHistory(50) }
    }

    @Test
    fun list_is_same_as_given_for_premium_users() {
        viewModel = HistoryViewModel(historyUseCase, billingHelper, spotifyHelper)
        viewModel.isPremiumUser = true

        val list = listOf(
            EasifyItem(EasifyItemType.TRACK, "Heathens", "Aurora")
        )
        val newList = viewModel.prepareItemsForView(list, "", "")
        Assert.assertEquals(list, newList)
    }

    @Test
    fun promo_view_is_added_for_non_premium_users() {
        viewModel = HistoryViewModel(historyUseCase, billingHelper, spotifyHelper)
        viewModel.isPremiumUser = false

        val list = listOf(
            EasifyItem(EasifyItemType.TRACK, "Heathens", "Aurora")
        )
        val newList = viewModel.prepareItemsForView(list, "", "")
        Assert.assertEquals(newList.size, list.size + 1)
    }
}