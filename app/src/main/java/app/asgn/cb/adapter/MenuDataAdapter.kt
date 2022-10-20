package app.asgn.cb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.asgn.cb.models.RestaurantListDataClass
import app.asgn.cb.R
import app.asgn.cb.databinding.MenuItemRvLayoutBinding
import app.asgn.cb.models.MenuListDataClass
import app.asgn.cb.util.loadImage
import java.util.*

class MenuDataAdapter(val context : Context, val onItemClicked: (MenuListDataClass.MenuItems, Int) -> Unit) : RecyclerView.Adapter<MenuDataAdapter.MenuViewHolder>() {

    private var menuDataa: ArrayList<MenuListDataClass.MenuItems>? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MenuViewHolder {
        val cartRvLayoutBinding: MenuItemRvLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.menu_item_rv_layout, viewGroup, false
        )
        return MenuViewHolder(cartRvLayoutBinding)
    }

    override fun onBindViewHolder(menuViewHolder: MenuViewHolder, i: Int) {
        val currentItem: MenuListDataClass.MenuItems = menuDataa!![i]
        menuViewHolder.menuListItemBinding.menuItem = currentItem
        menuViewHolder.menuListItemBinding.iv1Mirl.loadImage(currentItem.images)
        menuViewHolder.itemView.setOnClickListener {
            onItemClicked(currentItem, i)
        }
    }

    override fun getItemCount(): Int {
        return if (menuDataa != null) {
            menuDataa!!.size
        } else {
            0
        }
    }

    fun setMenuItemList(sizes: ArrayList<MenuListDataClass.MenuItems>?) {
        this.menuDataa = sizes
        notifyDataSetChanged()
    }

    inner class MenuViewHolder(menuItemRvLayoutBinding: MenuItemRvLayoutBinding) :
        RecyclerView.ViewHolder(menuItemRvLayoutBinding.root) {
        val menuListItemBinding: MenuItemRvLayoutBinding

        init {
            menuListItemBinding = menuItemRvLayoutBinding
        }
    }
}