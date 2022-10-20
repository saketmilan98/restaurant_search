package app.asgn.cb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.asgn.cb.models.RestaurantListDataClass
import app.asgn.cb.R
import app.asgn.cb.databinding.RestItemRvLayoutBinding
import app.asgn.cb.models.MenuListDataClass
import app.asgn.cb.util.loadImage
import java.util.*

class RestDataAdapter(val context : Context, val onItemClicked: (RestaurantListDataClass.Restaurants, Int) -> Unit) : RecyclerView.Adapter<RestDataAdapter.RestViewHolder>() {
    private var restDataa: ArrayList<RestaurantListDataClass.Restaurants>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RestViewHolder {
        val cartRvLayoutBinding: RestItemRvLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.rest_item_rv_layout, viewGroup, false
        )
        return RestViewHolder(cartRvLayoutBinding)
    }

    override fun onBindViewHolder(restViewHolder: RestViewHolder, i: Int) {
        val currentItem: RestaurantListDataClass.Restaurants = restDataa!![i]
        restViewHolder.restListItemBinding.restItem = currentItem
        restViewHolder.restListItemBinding.iv1Rirl.loadImage(currentItem.photograph)
        restViewHolder.restListItemBinding.searchMenuCl.isVisible = !currentItem.searchedMenus.isNullOrEmpty()
        restViewHolder.restListItemBinding.iv1Rirl.isVisible = currentItem.searchedMenus.isNullOrEmpty()
        val menuDataAdapter = MenuDataAdapter(context,
            onItemClicked = { restItem, position ->

            })
        restViewHolder.restListItemBinding.searchMenuRv.adapter = menuDataAdapter
        if(currentItem.searchedMenus.isNullOrEmpty()){
            restViewHolder.restListItemBinding.tv6Rirl.text = context.getString(R.string.dishes)
            val tempMenu = ArrayList<MenuListDataClass.MenuItems>()
            currentItem.menus?.categories?.forEach { categoryItem ->
                tempMenu.addAll(categoryItem.menuItems)
            }
            menuDataAdapter.setMenuItemList(tempMenu)
            restViewHolder.restListItemBinding.iv4Rirl.isVisible = true
            restViewHolder.restListItemBinding.iv4Rirl.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            restViewHolder.itemView.setOnClickListener {
                if(restViewHolder.restListItemBinding.searchMenuCl.isVisible){
                    restViewHolder.restListItemBinding.searchMenuCl.isVisible = false
                    restViewHolder.restListItemBinding.iv4Rirl.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                }
                else {
                    restViewHolder.restListItemBinding.searchMenuCl.isVisible = true
                    restViewHolder.restListItemBinding.iv4Rirl.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                }
                onItemClicked(currentItem, i)
            }
        }
        else {
            restViewHolder.itemView.setOnClickListener(null)
            restViewHolder.restListItemBinding.tv6Rirl.text = context.getString(R.string.matching_dishes)
            restViewHolder.restListItemBinding.iv4Rirl.isVisible = false
            menuDataAdapter.setMenuItemList(currentItem.searchedMenus)
        }



    }

    override fun getItemCount(): Int {
        return if (restDataa != null) {
            restDataa!!.size
        } else {
            0
        }
    }

    fun setRestItemList(sizes: ArrayList<RestaurantListDataClass.Restaurants>?) {
        this.restDataa = sizes
        notifyDataSetChanged()
    }

    inner class RestViewHolder(restItemRvLayoutBinding: RestItemRvLayoutBinding) :
        RecyclerView.ViewHolder(restItemRvLayoutBinding.root) {
        val restListItemBinding: RestItemRvLayoutBinding

        init {
            restListItemBinding = restItemRvLayoutBinding
        }
    }
}