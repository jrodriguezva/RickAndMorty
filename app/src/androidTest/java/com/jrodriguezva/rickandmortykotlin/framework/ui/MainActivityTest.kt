package com.jrodriguezva.rickandmortykotlin.framework.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.jrodriguezva.rickandmortykotlin.R
import com.jrodriguezva.rickandmortykotlin.di.RepositoryModule
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import com.jrodriguezva.rickandmortykotlin.framework.di.FakeRepository
import com.jrodriguezva.rickandmortykotlin.framework.local.RickAndMortyDatabase
import com.jrodriguezva.rickandmortykotlin.framework.utils.childAtPosition
import com.jrodriguezva.rickandmortykotlin.ui.main.MainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var hiltRule = HiltAndroidRule(this)
    val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @BindValue
    @JvmField
    val repository: RickAndMortyRepository = FakeRepository()

    @Inject
    lateinit var okHttp: OkHttpClient

    @Inject
    lateinit var database: RickAndMortyDatabase

    @After
    fun tearDown() {
        database.close()
    }

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @get:Rule
    val testRule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Test
    fun clickCharacter_OpensCharacterDetail() {
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        val textView = onView(
            Matchers.allOf(
                withId(R.id.name),
                ViewMatchers.withParent(ViewMatchers.withParent(withId(R.id.characterCard))),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(matches(withText("Rick Sanchez")))
    }

    @Test
    fun clickButtonFavorite_ShowFavorite_ClickOnFavorite_OpenCharacterDetail() {
        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.favoriteFragment), ViewMatchers.withContentDescription("Favorites"),
                childAtPosition(
                    childAtPosition(withId(R.id.bottom_nav), 0),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        val textView = onView(
            Matchers.allOf(
                withId(R.id.name),
                ViewMatchers.withParent(ViewMatchers.withParent(withId(R.id.characterCard))),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(matches(withText("Rick Sanchez")))
    }
}
