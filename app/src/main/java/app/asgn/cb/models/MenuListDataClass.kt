package app.asgn.cb.models

import app.asgn.cb.util.toDecimalFormat
import com.google.gson.annotations.SerializedName

class MenuListDataClass(
    @SerializedName("menus" ) var menus : ArrayList<Menus> = arrayListOf()
) {
    data class Menus (
        @SerializedName("restaurantId" ) var restaurantId : Int?                  = null,
        @SerializedName("categories"   ) var categories   : ArrayList<Categories> = arrayListOf()
    )
    data class Categories (
        @SerializedName("id"         ) var id         : String?               = null,
        @SerializedName("name"       ) var name       : String?               = null,
        @SerializedName("menu-items" ) var menuItems : ArrayList<MenuItems> = arrayListOf()
    )
    data class MenuItems (
        @SerializedName("id"          ) var id          : String?           = null,
        @SerializedName("name"        ) var name        : String?           = null,
        @SerializedName("description" ) var description : String?           = null,
        @SerializedName("price"       ) var price       : String?           = null,
        @SerializedName("images"      ) var images      : String?           = null,
        @SerializedName("category_name")var categoryName: String?           = null
    ) {
        val formattedPrice: String
            get() = "₹${price?.toDecimalFormat()}"
    }
}