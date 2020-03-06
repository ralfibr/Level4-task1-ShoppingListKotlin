package com.task1.shoppinglistkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task1.shoppinglistkotlin.Data.ProductRepository
import com.task1.shoppinglistkotlin.Model.Product
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Raeef Ibrahim
 * Student Nr 500766393
 */
class MainActivity : AppCompatActivity() {

    private val shoppingList = arrayListOf<Product>()
    private val productAdapter = ProductAdapter(shoppingList)
    private lateinit var productRepository: ProductRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //App Title
        supportActionBar?.title = "Shopping List Kotlin"

        productRepository = ProductRepository(this)
        initViews()
    }

    private fun initViews() {
        rvShopingList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvShopingList.adapter = productAdapter
        rvShopingList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvShopingList)
        getShoppingListFromDatabase()

        fab.setOnClickListener { addProduct() }
    }

// Get the room database
    private fun getShoppingListFromDatabase() {
        mainScope.launch {
            // call product repository
            val shoppingList = withContext(Dispatchers.IO) {
                productRepository.getAllProducts()
            }
            this@MainActivity.shoppingList.clear()
            this@MainActivity.shoppingList.addAll(shoppingList)
            this@MainActivity.productAdapter.notifyDataSetChanged()
        }
    }
// check the input
    private fun validateFields(): Boolean {
        return if (editTextWhat.text.toString().isNotBlank() && editTextHowmany.text.toString().isNotBlank() &&  valdiateThatManyIsOnlyNumbers()) {
            true
        } else {
            Toast.makeText(this, getString(R.string.incorect), Toast.LENGTH_SHORT).show()
            false
        }


}

    // check if the input is only nimber in how mant input filed
    private fun valdiateThatManyIsOnlyNumbers(): Boolean {
        return if (editTextHowmany.text.toString().matches(
                Regex("^[0-9]*\$")
            )
        ) {
            true
        } else {
            Toast.makeText(this, getString(R.string.fillNumber), Toast.LENGTH_SHORT).show()
            false
        }

    }


// add product to the database
    private fun addProduct() {
        if (validateFields()) {
            mainScope.launch {
                val product = Product(
                    name = editTextWhat.text.toString(),
                    quantity = editTextHowmany.text.toString().toInt()
                )
                withContext(Dispatchers.IO) {
                    productRepository.insertProduct(product)
                }

                getShoppingListFromDatabase()
            }
        }

    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val productToDelete = shoppingList[position]
                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        productRepository.deleteProduct(productToDelete)
                    }
                    getShoppingListFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
    }
// delete all list
    private fun deleteShoppingList() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                productRepository.deleteAllProducts()
            }
            getShoppingListFromDatabase()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_shopping_list -> {
                deleteShoppingList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
