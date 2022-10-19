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

    private val _restaurantInfo: MutableLiveData<Resource<RestaurantListDataClass>> = MutableLiveData()
    val restaurantInfo: LiveData<Resource<RestaurantListDataClass>> = _restaurantInfo
    var restaurantListMain = ArrayList<RestaurantListDataClass.Restaurants>()

    private val _menuInfo: MutableLiveData<Resource<MenuListDataClass>> = MutableLiveData()
    val menuInfo: LiveData<Resource<MenuListDataClass>> = _menuInfo
    var menuListMain = ArrayList<MenuListDataClass.Menus>()

    init {
        fetchRestaurantAndMenus()
    }

    private fun fetchRestaurantAndMenus(){
        viewModelScope.launch(Dispatchers.IO){
            _restaurantInfo.postValue(Resource.loading(null))
            restaurantRepository.fetchRestaurants()
            if(!restaurantRepository.restaurantList?.restaurants.isNullOrEmpty()){
                _restaurantInfo.postValue(Resource.success(restaurantRepository.restaurantList))
                restaurantListMain = restaurantRepository.restaurantList?.restaurants!!
            }
            else {
                _restaurantInfo.postValue(Resource.error("No data found",null))
            }
            Log.d("logflags1",restaurantRepository.restaurantList.toString())
            viewModelScope.launch(Dispatchers.IO){
                restaurantRepository.fetchMenus()
                if(!restaurantRepository.menuList?.menus.isNullOrEmpty()){
                    _menuInfo.postValue(Resource.success(restaurantRepository.menuList))
                    menuListMain = restaurantRepository.menuList?.menus!!
                }
                else {
                    _menuInfo.postValue(Resource.error("No data found",null))
                }
                Log.d("logflags2",restaurantRepository.menuList.toString())
            }
        }
    }

    fun getRestaurantList() : ArrayList<RestaurantListDataClass.Restaurants> {
        return restaurantListMain
    }

    fun getMenuList() : ArrayList<MenuListDataClass.Menus> {
        return menuListMain
    }

}