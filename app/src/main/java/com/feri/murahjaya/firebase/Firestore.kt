package com.feri.murahjaya.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.feri.murahjaya.adapter.*
import com.feri.murahjaya.model.*
import com.feri.murahjaya.utils.ADDRESSHANDLER
import com.feri.murahjaya.utils.ITEMTYPE
import com.feri.murahjaya.utils.STATUS
import com.feri.murahjaya.utils.createToast
import com.feri.murahjaya.view.MainActivity
import com.feri.murahjaya.view.PaymentActivity
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class Firestore(private val context: Context){

    private val mFirestore = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()

    private val activity = context as Activity

    private val cartRef=
        mFirestore.collection("users").document(mAuth.currentUser?.uid!!).collection("cart")

    val orderRef= mFirestore.collection("orders")

    private val addressRef=
        mFirestore.collection("users").document(mAuth.currentUser?.uid!!).collection("address")

    private val favoriteRef = mFirestore.collection("users").document(mAuth.currentUser?.uid!!).collection("favorite")

    fun addProductToCart(cart: Cart) {
        val docRef = cartRef.document(cart.product?.uid!!).set(cart)
        docRef.addOnSuccessListener {
            createToast(context, "Produk ditambahkan ke keranjang")
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun updateCartItem(cart: Cart) {
        val update = mapOf(
            "qty" to cart.qty,
            "total" to cart.total
        )

        val docRef = cartRef.document(cart.product?.uid!!).update(update)
        docRef.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun getAllCart(): CartAdapter {
        val query = cartRef.orderBy("product.nama", Query.Direction.ASCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<Cart>().setQuery(query, Cart::class.java).build()

        return CartAdapter(context, options)
    }

    fun saveOrder(order: Order) {
        val docRef = orderRef.document()
        order.uid = docRef.id

        cartRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.delete()
            }
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }

        docRef.set(order).addOnSuccessListener {
            createToast(context, "Order Ditambahkan")
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra("TAG", "checkout")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun deleteOrder(order: Order) {
        val docRef = orderRef.document(order.uid!!)
        docRef.delete().addOnSuccessListener {
            createToast(context, "Order dibatalkan")
            activity.finish()
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
            activity.finish()
        }
    }

    fun getAllOrderByStatus(query: Query): OrderAdapter {
        val options =
            FirestoreRecyclerOptions.Builder<Order>().setQuery(query, Order::class.java).build()

        return OrderAdapter(context, options)
    }

    fun getAllProduct(): ProductGridAdapter {
        val query = mFirestore.collection("products").orderBy("nama", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java).build()

        return ProductGridAdapter(context, options, ITEMTYPE.GENERAL, null)
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

        return ProductGridAdapter(context, options, ITEMTYPE.GENERAL, null)
    }

    fun addToFavorite(product: Product) {
        favoriteRef.document(product.uid!!).set(product).addOnSuccessListener {
            createToast(context, "Ditambahkan ke favorit")
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun removeFromFavorite(product: Product) {
        favoriteRef.document(product.uid!!).delete().addOnSuccessListener {
            createToast(context, "Dihapus dari favorit")
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun getCurrentUserFavorite(emptyView: View?): ProductGridAdapter {
        val query = favoriteRef.orderBy("nama", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java).build()

        return ProductGridAdapter(context, options, ITEMTYPE.FAVORITE, emptyView)
    }

    fun addAddress(address: Address) {
        val docRef = addressRef.document()
        address.uid = docRef.id

        docRef.set(address).addOnSuccessListener {
            createToast(context, "Alamat ditambahkan")
            activity.finish()
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun removeAddress(address: Address) {
        val docRef = addressRef.document(address.uid!!)
        docRef.delete().addOnSuccessListener {
            createToast(context, "Alamat dihapus")
            activity.finish()
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun editAddress(address: Address) {
        val docRef = addressRef.document(address.uid!!)
        val update = mapOf(
            "name" to address.name,
            "address" to address.address,
            "kecamatan" to address.kecamatan,
            "desa" to address.desa,
            "noTelp" to address.noTelp
        )

        docRef.update(update).addOnSuccessListener {
            createToast(context, "Alamat diubah")
            activity.finish()
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

    fun getUserAddress(handler: ADDRESSHANDLER): AddressAdapter {
        val query = addressRef.orderBy("address", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Address>().setQuery(query, Address::class.java)
            .build()

        return AddressAdapter(context, handler, options)
    }

    fun uploadBuktiPembayaran(imageUri: Uri, order: Order) {
        createToast(context, "Mengunggah Gambar")
        val docRef = orderRef.document(order.uid!!)
        val uploadRef = FirebaseStorage.getInstance().reference.child("order/${order.uid}.jpg")
        val uploadTask = uploadRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.apply {
                    throw this
                }
            }

            uploadRef.downloadUrl
        }.addOnSuccessListener {
            val update = mapOf(
                "buktiPembayaran" to it.toString(),
                "status" to STATUS.PAYED
            )

            docRef.update(update).addOnSuccessListener {
                createToast(context, "Bukti pembayaran telah diunggah")
                activity.finish()
            }.addOnFailureListener { e ->
                createToast(context, e.localizedMessage)
            }
        }.addOnFailureListener {
            createToast(context, it.localizedMessage)
        }
    }

}
