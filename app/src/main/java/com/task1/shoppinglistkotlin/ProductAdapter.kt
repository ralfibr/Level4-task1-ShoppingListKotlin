package com.task1.shoppinglistkotlin
import kotlinx.android.synthetic.main.product_item.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task1.shoppinglistkotlin.Model.Product


class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.tvProduct.text = product.name
            itemView.tvQuantity.text = product.quantity.toString()
        }
    }
}