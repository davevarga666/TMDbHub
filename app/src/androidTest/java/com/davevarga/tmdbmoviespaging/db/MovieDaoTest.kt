package com.davevarga.tmdbmoviespaging.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.davevarga.tmdbmoviespaging.getOrAwaitValue
import com.davevarga.tmdbmoviespaging.models.Movie
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.movieDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMovie() = runBlockingTest {
        val movie = Movie(-1, "1", 1, false, "", "",
            false, "", "", "","","", 0.0, "")
        val movieCollection = dao.getCollection().getOrAwaitValue()

        assertThat(movieCollection).contains(movie)
    }

    @Test
    fun deleteMovie() = runBlockingTest {
        val movie = Movie(-1, "1", 1, false, "", "",
            false, "", "", "","","", 0.0, "")
        dao.insertMovie(movie)
        dao.deleteMovies()

        val movieCollection = dao.getCollection().getOrAwaitValue()

        assertThat(movieCollection).doesNotContain(movie)
    }

}