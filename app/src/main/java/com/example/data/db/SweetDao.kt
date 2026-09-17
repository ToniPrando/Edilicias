package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SweetDao {
  @Query("SELECT * FROM sweets ORDER BY name ASC")
  suspend fun getAllSweets(): List<SweetEntity>

  @Query("SELECT COUNT(*) FROM sweets")
  suspend fun getCount(): Int

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(sweets: List<SweetEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(sweet: SweetEntity)

  @Update
  suspend fun update(sweet: SweetEntity)

  @Query("DELETE FROM sweets WHERE id = :id")
  suspend fun deleteById(id: String)

  @Query("DELETE FROM sweets")
  suspend fun clearAll()
}
