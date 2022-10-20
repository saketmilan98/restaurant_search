package app.asgn.cb.viewmodels

import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import app.asgn.cb.models.MenuListDataClass
import app.asgn.cb.models.RestaurantListDataClass
import app.asgn.cb.repository.RestaurantRepository
import app.asgn.cb.util.Resource
import app.asgn.cb.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val restaurantRepository = RestaurantRepository()

    private val _restaurantMenuCombinedInfo: MutableLiveData<Resource<ArrayList<RestaurantListDataClass.Restaurants>>> = MutableLiveData()
    val restaurantMenuCombinedInfo: LiveData<Resource<ArrayList<RestaurantListDataClass.Restaurants>>> = _restaurantMenuCombinedInfo
    var restaurantMenuCombinedListMain = ArrayList<RestaurantListDataClass.Restaurants>()

    private val _searchResultRestMenuInfo: MutableLiveData<Resource<ArrayList<RestaurantListDataClass.Restaurants>>> = MutableLiveData()
    val searchResultRestMenuInfo: LiveData<Resource<ArrayList<RestaurantListDataClass.Restaurants>>> = _searchResultRestMenuInfo


    init {
        fetchRestaurantAndMenus()
    }

    private fun fetchRestaurantAndMenus(){
        viewModelScope.launch(Dispatchers.IO){
            var restaurantListTemp = ArrayList<RestaurantListDataClass.Restaurants>()
            _restaurantMenuCombinedInfo.postValue(Resource.loading(null))
            restaurantRepository.fetchRestaurants()
            if(!restaurantRepository.restaurantList?.restaurants.isNullOrEmpty()){
                restaurantListTemp = restaurantRepository.restaurantList?.restaurants!!
            }
            viewModelScope.launch(Dispatchers.IO){
                var menuListTemp = ArrayList<MenuListDataClass.Menus>()
                restaurantRepository.fetchMenus()
                if(!restaurantRepository.menuList?.menus.isNullOrEmpty()){
                    menuListTemp = restaurantRepository.menuList?.menus!!
                }
                val tempRestaurants = ArrayList<RestaurantListDataClass.Restaurants>()
                restaurantListTemp.forEach { it1->
                    val tempMenu = menuListTemp.find { it2-> it2.restaurantId ==  it1.id}
                    if(tempMenu != null){
                        tempMenu.categories.forEach { categoryItem ->
                            categoryItem.menuItems.forEach { menuItemss ->
                                menuItemss.categoryName = categoryItem.name
                            }
                        }
                        tempRestaurants.add(it1.copy(menus = tempMenu))
                    }
                }
                if(tempRestaurants.isNotEmpty()){
                    _restaurantMenuCombinedInfo.postValue(Resource.success(tempRestaurants))
                    restaurantMenuCombinedListMain = tempRestaurants
                }
                else {
                    _restaurantMenuCombinedInfo.postValue(Resource.error("No data found",null))
                }
            }
        }
    }

    fun getSearchResultRestList(query : String){
        viewModelScope.launch{
            val filteredRestaurantList = restaurantMenuCombinedListMain.filterIndexed { position, restItem->
                val filteredMenu = ArrayList<MenuListDataClass.MenuItems>()
                restItem.menus?.categories?.forEach { categoryItem ->
                    val filteredMenuList = categoryItem.menuItems.filter { menuItems ->
                        menuItems.categoryName = categoryItem.name
                        (menuItems.name?:"").contains(query, ignoreCase = true)
                    }
                    filteredMenu.addAll(filteredMenuList)
                }
                restItem.searchedMenus = filteredMenu

                if(query.isEmpty()){
                    restItem.searchedMenus = ArrayList<MenuListDataClass.MenuItems>()
                }
                (restItem.cuisineType?:"").contains(query, ignoreCase = true) ||
                    (restItem.neighborhood?:"").contains(query, ignoreCase = true) ||
                    (restItem.address?:"").contains(query, ignoreCase = true) ||
                    (restItem.name?:"").contains(query, ignoreCase = true) ||
                    !(restItem.menus?.categories?.filter { categoryItem-> (categoryItem.name?:"").contains(query, ignoreCase = true) }.isNullOrEmpty()) ||
                        (filteredMenu.isNotEmpty())
            } as ArrayList<RestaurantListDataClass.Restaurants>

            if(filteredRestaurantList.isNotEmpty()){
                _searchResultRestMenuInfo.postValue(Resource.success(filteredRestaurantList))
            }
            else {
                _searchResultRestMenuInfo.postValue(Resource.error("no result found", null))
            }
        }
    }
}