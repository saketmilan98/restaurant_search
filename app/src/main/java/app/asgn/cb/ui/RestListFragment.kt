package app.asgn.cb.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import app.asgn.cb.viewmodels.MainViewModel
import app.asgn.cb.R
import app.asgn.cb.adapter.RestDataAdapter
import app.asgn.cb.databinding.FragmentRestListBinding
import app.asgn.cb.util.Status

class RestListFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentRestListBinding

    private val restDataAdapter by lazy { RestDataAdapter(requireContext(),
        onItemClicked = { restItem, position ->
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.toolbarTitleTv.text = getString(R.string.app_name)
        binding.toolbarLayout.toolbarBackIv.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.cutlery, requireActivity().theme))
        binding.restListRv.adapter = restDataAdapter
        binding.searchView1.onActionViewExpanded()
        binding.searchView1.queryHint = resources.getString(R.string.query_hint)

        binding.searchView1.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSearchResultRestList(newText?:"")
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        viewModel.restaurantMenuCombinedInfo.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { mainInfo ->
                        restDataAdapter.setRestItemList(mainInfo)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        })

        viewModel.searchResultRestMenuInfo.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.noResultTv.isVisible = false
                    binding.progressBar.isVisible = false
                    binding.restListRv.isVisible = true
                    it.data?.let { mainInfo ->
                        restDataAdapter.setRestItemList(mainInfo)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.noResultTv.isVisible = true
                    binding.restListRv.isVisible = false
                }
            }
        })
    }
}