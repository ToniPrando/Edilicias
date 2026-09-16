package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Edilicias", appName)
  }

  @Test
  fun `sweet repository crud operations`() {
    val initialCount = com.example.data.SweetRepository.sweetItems.size
    val testSweet = com.example.model.SweetItem(
      id = "test_sweet_1",
      name = "Bolo de Teste",
      category = com.example.model.Category.CAKES,
      description = "Descricao de teste",
      basePrice = 30.0,
      imageRes = R.drawable.img_bolo,
      isCustomizable = true,
      tags = listOf("Teste"),
    )

    // Add
    com.example.data.SweetRepository.addItem(testSweet)
    assertEquals(initialCount + 1, com.example.data.SweetRepository.sweetItems.size)

    // Update
    val updatedSweet = testSweet.copy(name = "Bolo Atualizado", basePrice = 35.0)
    com.example.data.SweetRepository.updateItem(updatedSweet)
    val found = com.example.data.SweetRepository.sweetItems.firstOrNull { it.id == "test_sweet_1" }
    assertEquals("Bolo Atualizado", found?.name)
    assertEquals(35.0, found?.basePrice ?: 0.0, 0.01)

    // Delete
    com.example.data.SweetRepository.deleteItem("test_sweet_1")
    assertEquals(initialCount, com.example.data.SweetRepository.sweetItems.size)
  }

  @Test
  fun `admin default password is 123456789`() {
    assertEquals("123456789", com.example.ui.ADMIN_DEFAULT_PASSWORD)
  }
}
