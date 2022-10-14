package com.slowerror.asteroidradar.screens.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.slowerror.asteroidradar.R
import com.slowerror.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidListener { asteroid ->
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(asteroid))
        })

        val linearLayout = LinearLayoutManager(requireContext())

        binding.asteroidRecyclerView.layoutManager = linearLayout
        binding.asteroidRecyclerView.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                viewModel.updateFilter(
                    when (menuItem.itemId) {
                        R.id.showNextWeekAsteroids -> FilterAsteroids.WEEK
                        R.id.showTodayAsteroids -> FilterAsteroids.TODAY
                        else -> FilterAsteroids.SAVED
                    }
                )
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}