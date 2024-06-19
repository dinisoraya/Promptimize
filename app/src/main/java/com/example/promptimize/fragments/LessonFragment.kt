package com.example.promptimize.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.promptimize.MainActivity
import com.example.promptimize.R
import com.example.promptimize.adapters.GitLessonStepAdapter
import com.example.promptimize.databinding.FragmentLessonBinding
import com.example.promptimize.models.lesson.GitLesson
import com.example.promptimize.utils.LoadData
import com.example.promptimize.utils.LoadSettings

class LessonFragment : Fragment() {

    private var _binding: FragmentLessonBinding? = null
    private val binding get() = _binding!!

    private val args: LessonFragmentArgs by navArgs()

    private lateinit var gitLessonStepAdapter: GitLessonStepAdapter
    private lateinit var gitLessonList: GitLesson

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //load settings
        LoadSettings.loadTheme(requireContext())

        // Inflate the layout for this fragment
        _binding = FragmentLessonBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        handleButtons()

        //get json data
        gitLessonList = LoadData.getGitLessonData(requireContext())!!

        gitLessonStepAdapter =
            GitLessonStepAdapter(requireContext(), gitLessonList.gitLessons[args.position])

        //set recycler view
        binding.rvLessonStep.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gitLessonStepAdapter
        }


        //update title
        binding.tvLessonTitle.text = gitLessonList.gitLessons[args.position].LessonTitle
    }

    private fun handleButtons() {
        binding.tvLessonTitle.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initUI() {
        binding.tvLessonTitle.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down_anim)
    }

    override fun onResume() {
        super.onResume()
        MainActivity.appBarLayout.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        MainActivity.appBarLayout.visibility = View.VISIBLE
        _binding = null
    }

}