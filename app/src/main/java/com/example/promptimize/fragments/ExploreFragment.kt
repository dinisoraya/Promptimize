package com.example.promptimize.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.promptimize.adapters.GitInfoAdapter
import com.example.promptimize.databinding.FragmentExploreBinding
import com.example.promptimize.models.GitCommand
import com.example.promptimize.models.GitCommandItem
import com.example.promptimize.utils.LoadData
import com.example.promptimize.utils.LoadSettings
import java.util.Locale

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var gitInfoAdapter: GitInfoAdapter
    private lateinit var gitCommandList: GitCommand

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //load settings
        LoadSettings.loadTheme(requireContext())

        // Inflate the layout for this fragment
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get data
        gitCommandList = LoadData.getGitCommandData(requireContext())!!

        gitInfoAdapter = GitInfoAdapter(requireContext(), gitCommandList.gitCommands)

        //set recycler view
        binding.rvGitInfo.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gitInfoAdapter
        }

        //search data
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<GitCommandItem>()
            for (i in gitCommandList.gitCommands) {
                if (i.command.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isNotEmpty()) {
                gitInfoAdapter.setFilteredList(filteredList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}