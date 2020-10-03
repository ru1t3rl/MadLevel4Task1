package tech.tucano.madlevel4task1

import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table")
    suspend fun getAllProduts(): List<Product>

    @Insert
    suspend fun instertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()
}