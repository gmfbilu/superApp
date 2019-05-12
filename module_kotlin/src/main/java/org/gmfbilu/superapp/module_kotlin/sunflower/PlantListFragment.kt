package org.gmfbilu.superapp.module_kotlin.sunflower

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.orhanobut.logger.Logger
import org.gmfbilu.superapp.module_kotlin.R
import org.gmfbilu.superapp.module_kotlin.databinding.ModuleKotlinFragmentPlantlistBinding
import org.gmfbilu.superapp.module_kotlin.sunflower.adapters.PlantAdapter
import org.gmfbilu.superapp.module_kotlin.sunflower.utilities.InjectorUtils
import org.gmfbilu.superapp.module_kotlin.sunflower.viewmodels.PlantListViewModel

class PlantListFragment : Fragment() {

    private lateinit var viewModel: PlantListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ModuleKotlinFragmentPlantlistBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        val factory = InjectorUtils.providePlantListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(PlantListViewModel::class.java)
        val adapter = PlantAdapter()
        binding.plantList.adapter = adapter
        subscribeUi(adapter)
        setHasOptionsMenu(true)
        Logger.d("PlantListFragment onCreateView")
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.filter_zone -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * TODO 主线程
     */
    private fun subscribeUi(adapter: PlantAdapter) {
        viewModel.plants.observe(viewLifecycleOwner, Observer { plants ->
            Logger.d("PlantListFragment subscribeUi")
            if (plants != null) adapter.submitList(plants)
        })
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearGrowZoneNumber()
            } else {
                setGrowZoneNumber(9)
            }
        }
    }
}