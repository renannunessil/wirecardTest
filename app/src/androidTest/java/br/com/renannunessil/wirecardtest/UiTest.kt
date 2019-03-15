package br.com.renannunessil.wirecardtest

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import br.com.renannunessil.wirecardtest.adapter.OrdersRecyclerViewAdapter
import br.com.renannunessil.wirecardtest.ui.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTest {
    @Rule
    @JvmField
    val rule = IntentsTestRule(LoginActivity::class.java)

    @Test
    fun uiTest() {
        Thread.sleep(5000)
        onView(withId(R.id.et_user)).perform(clearText(), typeTextIntoFocusedView("moip-test-developer@moip.com.br"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.et_password)).perform(click(), clearText(), typeTextIntoFocusedView("testemoip123"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.bt_login)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.rv_orders_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<OrdersRecyclerViewAdapter.OrdersViewHolder>(0, click()))
        Thread.sleep(5000)
        onView(withId(R.id.tv_customer_label)).check(ViewAssertions.matches(withText(R.string.customer)))
    }
}