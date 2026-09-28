package com.kukurodev.minddrop

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kukurodev.minddrop.data.local.database.AppDatabase
import com.kukurodev.minddrop.data.local.entity.ItemEntity
import com.kukurodev.minddrop.domain.model.ItemType
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder<AppDatabase>(
            context
        )
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun insertAndReadItem() = runTest {
        val item = ItemEntity(
            title = "Kahve bitti",
            type = ItemType.INBOX,
            isCompleted = false,
            createdAt = 123L
        )

        val id = database.itemDao().insert(item)

        val result = database.itemDao().getItem(id)

        assertNotNull(result)
        assertEquals("Kahve bitti", result.title)
        assertEquals(ItemType.INBOX, result.type)
        assertEquals(false, result.isCompleted)
    }

    @After
    fun tearDown() {
        database.close()
    }
}