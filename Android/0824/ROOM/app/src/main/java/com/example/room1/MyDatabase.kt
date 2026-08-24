package com.example.room1

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

// 1. Entity 定義
@Entity(tableName = "customer_table")
data class CustomerData(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,

    @NonNull
    @ColumnInfo(name = "customer_name", defaultValue = "")
    val name: String,
    val email: String,
    val phone: String

)

@Dao
interface DataDao{

    @Query(value = "Select * from customer_table")
    suspend fun getAll(): List<CustomerData>

    @Query(value = "Select * from customer_table where customer_name = :name")
    suspend fun getCustomer(name: String) : List<CustomerData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomer( customer: CustomerData) : Long

    @Update
    suspend fun update(customer: CustomerData) : Int

    @Delete
    suspend fun delete(customer: CustomerData) : Int
//  //Teacher Vesrion:
//    @Delete
//    fun delete(customer: CustomerData) : Int

}

// 3. Database 定義
@Database(entities = [CustomerData::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "customer_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}