package tech.tucano.madlevel4task1

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import kotlinx.coroutines.*
import tech.tucano.madlevel4task1.databinding.FragmentShoppingListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ShoppingListFragment : Fragment() {
    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var productRepository: ProductRepository

    private var products = arrayListOf<Product>()
    private var productAdapter = ShoppingItemAdapter(products)

    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShoppingListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productRepository = ProductRepository(requireContext())

        initRv()

        fabAdd.setOnClickListener{
            showAddProductDialog()
        }
    }

    private fun initRv(){
        binding.rvProducts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvProducts.adapter = productAdapter
        binding.rvProducts.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.
                VERTICAL
            ))
    }

    private suspend fun getShoppingListFromDatabase() {
        mainScope.launch {
            val shoppingList = withContext(Dispatchers.IO) {
                productRepository.getAllProducts()
            }
            this@ShoppingListFragment.products.clear()
            this@ShoppingListFragment.products.addAll(shoppingList)
            this@ShoppingListFragment.productAdapter.notifyDataSetChanged()
        }
    }

    private fun showAddProductDialog(){
        // Building the dialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.add_product_dialog_title))
        val dialogLayout = layoutInflater.inflate(R.layout.add_producct_dialog, null)

        // Reading the EditText values
        val productName = dialogLayout.findViewById<EditText>(R.id.txt_product_name)
        val amount = dialogLayout.findViewById<EditText>(R.id.txt_amount)

        builder.setView(dialogLayout)
        builder.setPositiveButton(R.string.dialog_ok_btn){
            DialogInterface, _: Int ->
            addProduct(productName, amount)
        }

        builder.show()
    }

    private fun addProduct(txtProductName: EditText, txtAmount: EditText){
        if(validateFields(txtProductName, txtAmount)){
            mainScope.launch {
                val product = Product (
                    name = txtProductName.text.toString(),
                    quantity = txtAmount.text.toString().toInt()
                )

                withContext(Dispatchers.IO){
                    productRepository.insertProduct(product)
                }

                getShoppingListFromDatabase()
            }
        } else {
            Toast.makeText(activity, getString(R.string.empty_field), Toast.LENGTH_LONG).show()
        }
    }

    private fun validateFields(txtProductName: EditText, txtAmount: EditText): Boolean{
        return txtProductName.text.toString().isNotBlank() &&
                txtAmount.text.toString().isNotBlank()
    }
}