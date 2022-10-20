package app.asgn.cb.models

import com.google.gson.annotations.SerializedName

data class RestaurantListDataClass (
    @SerializedName("restaurants" ) var restaurants : ArrayList<Restaurants> = arrayListOf()
){
    data class Restaurants (
        @SerializedName("id"                        ) var id             : Int?                     = null,
        @SerializedName("name"                      ) var name           : String?                  = null,
        @SerializedName("neighborhood"              ) var neighborhood   : String?                  = null,
        @SerializedName("photograph"                ) var photograph     : String?                  = null,
        @SerializedName("address"                   ) var address        : String?                  = null,
        @SerializedName("latlng"                    ) var latlng         : Latlng?                  = Latlng(),
        @SerializedName("cuisine_type"              ) var cuisineType    : String?                  = null,
        @SerializedName("operating_hours"           ) var operatingHours : OperatingHours?          = OperatingHours(),
        @SerializedName("reviews"                   ) var reviews        : ArrayList<Reviews>       = arrayListOf(),
        @SerializedName("menus"                     ) var menus          : MenuListDataClass.Menus? = null,
        @SerializedName("search_result_menu_items"  ) var searchedMenus  : ArrayList<MenuListDataClass.MenuItems>? = arrayListOf()
    )
    data class Latlng (
        @SerializedName("lat" ) var lat : Double? = null,
        @SerializedName("lng" ) var lng : Double? = null
    )
    data class OperatingHours (
        @SerializedName("Monday"    ) var Monday    : String? = null,
        @SerializedName("Tuesday"   ) var Tuesday   : String? = null,
        @SerializedName("Wednesday" ) var Wednesday : String? = null,
        @SerializedName("Thursday"  ) var Thursday  : String? = null,
        @SerializedName("Friday"    ) var Friday    : String? = null,
        @SerializedName("Saturday"  ) var Saturday  : String? = null,
        @SerializedName("Sunday"    ) var Sunday    : String? = null
    )
    data class Reviews (
        @SerializedName("name"     ) var name     : String? = null,
        @SerializedName("date"     ) var date     : String? = null,
        @SerializedName("rating"   ) var rating   : Int?    = null,
        @SerializedName("comments" ) var comments : String? = null
    )
}