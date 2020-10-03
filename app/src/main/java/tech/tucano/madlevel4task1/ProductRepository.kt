package tech.tucano.madlevel4task1

import android.content.Context

class ProductRepository(context: Context) {
    private val productDao: ProductDao

    init {
        val database = ShoppingListRoomDatabase.getDatabase(context)
        productDao = database!!.productDao()
    }

    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProduts()
    }

    suspend fun insertProduct(product: Product){
        productDao.instertProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun deleteAllProducts(){
        productDao.deleteAllProducts()
    }
}