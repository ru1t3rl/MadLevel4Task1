package tech.tucano.madlevel4task1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.tucano.madlevel4task1.databinding.ItemProductBinding

class ShoppingItemAdapter (private val shoppingItems: List<Product>) :
        RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder>(){
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            private val binding = ItemProductBinding.bind(itemView)

            fun databind(shoppingItem: Product){
                binding.tvName.text = shoppingItem.name
                binding.tvAmount.text = shoppingItem.quantity.toString()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return shoppingItems.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(shoppingItems[position])
    }
}