
package app.asgn.cb.repository

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.asgn.cb.R
import app.asgn.cb.app.MyApp
import app.asgn.cb.models.MenuListDataClass
import app.asgn.cb.models.RestaurantListDataClass
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RestaurantRepository() {

    var restaurantList: RestaurantListDataClass? = null
    suspend fun fetchRestaurants() {
        withContext(Dispatchers.IO) {
            val text = MyApp.getInstance()?.resources?.openRawResource(R.raw.restaurant)?.bufferedReader().use { it?.readText() } // reading moviedata json from raw folder and storing it in a string
            restaurantList = Gson().fromJson(text, RestaurantListDataClass::class.java)
        }
    }

    var menuList: MenuListDataClass? = null
    suspend fun fetchMenus() {
        withContext(Dispatchers.IO) {
            val text = MyApp.getInstance()?.resources?.openRawResource(R.raw.menus)?.bufferedReader().use { it?.readText() } // reading moviedata json from raw folder and storing it in a string
            menuList = Gson().fromJson(text, MenuListDataClass::class.java)
        }
    }

}

