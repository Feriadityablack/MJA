package com.feri.murahjaya.firebase

import android.content.Context
import com.feri.murahjaya.adapter.ProductGridAdapter
import com.feri.murahjaya.adapter.ProductHorizontalAdapter
import com.feri.murahjaya.model.Cart
import com.feri.murahjaya.model.Product
import com.feri.murahjaya.utils.CATEGORY
import com.feri.murahjaya.utils.createToast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Firestore(private val context: Context){

    private val mFirestore = FirebaseFirestore.getInstance()
    private lateinit var cartRef: CollectionReference
    private lateinit var orderRef: CollectionReference
    private lateinit var addressRef: CollectionReference

    fun initReference(user: FirebaseUser) {
        cartRef = mFirestore.collection("users").document(user.uid).collection("cart")
        orderRef = mFirestore.collection("orders")
        addressRef = mFirestore.collection("users").document(user.uid).collection("address")
    }

    fun addProductToCart(cart: Cart) {
        val docRef = cartRef.document(cart.product.uid!!).set(cart)
        docRef.addOnSuccessListener {
            createToast(context, "Produk ditambahkan ke keranjang")
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun removeProductToCart(cart: Cart) {
        val docRef = cartRef.document(cart.product.uid!!).delete()
        docRef.addOnSuccessListener {
            createToast(context, "Produk dihapus dari keranjang")
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun updateCartItem(cart: Cart) {
        val update = mapOf(
            "qty" to cart.qty,
            "total" to cart.total
        )

        val docRef = cartRef.document(cart.product.uid!!).update(update)
        docRef.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun getAllProduct(): ProductGridAdapter {
        val query = mFirestore.collection("products").orderBy("nama", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java).build()

        return ProductGridAdapter(context, options)
    }

    fun getProductForHome(): ProductHorizontalAdapter {
        val query = mFirestore.collection("products").orderBy("nama", Query.Direction.ASCENDING)
            .limit(5)
        val options = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java).build()

        return ProductHorizontalAdapter(context, options)
    }

    fun getProductByCategory(category: String): ProductGridAdapter {
        val query = mFirestore.collection("products").whereEqualTo("kategori", category).orderBy("nama", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java).build()

        return ProductGridAdapter(context, options)
    }

}
