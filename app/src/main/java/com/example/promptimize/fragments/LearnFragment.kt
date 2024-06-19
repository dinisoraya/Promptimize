package com.example.promptimize.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.promptimize.adapters.GitLessonAdapter
import com.example.promptimize.databinding.FragmentLearnBinding
import com.example.promptimize.models.lesson.GitLesson
import com.example.promptimize.utils.LoadData
import com.example.promptimize.utils.LoadSettings

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    private lateinit var gitLessonAdapter: GitLessonAdapter
    private lateinit var gitLessonList: GitLesson

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //load settings
        LoadSettings.loadTheme(requireContext())

        // Inflate the layout for this fragment
        _binding = FragmentLearnBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get json data
        gitLessonList = LoadData.getGitLessonData(requireContext())!!

        gitLessonAdapter =
            GitLessonAdapter(
                requireContext(),
                gitLessonList.gitLessons,
                ::onLessonItemClicked
            )

        //set recycler view
        binding.rvGitInfo.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = gitLessonAdapter
        }

    }

    private fun onLessonItemClicked(currentLesson: Int) {
        val action = MainFragmentDirections.actionMainFragmentToLessonFragment(currentLesson)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}