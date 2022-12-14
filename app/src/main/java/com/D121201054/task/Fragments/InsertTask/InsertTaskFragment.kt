package com.D121201054.task.Fragments.InsertTask

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.D121201054.task.Model.TaskData
import com.D121201054.task.R
import com.D121201054.task.ViewModel.TasksViewModel
import com.D121201054.task.databinding.FragmentInsertTaskBinding
import kotlinx.coroutines.launch


class InsertTaskFragment : Fragment() {

    private var _binding: FragmentInsertTaskBinding? = null
    private val binding get() = _binding!!

    lateinit var taskCategory: ArrayAdapter<CharSequence>
    private lateinit var mTasksViewModel : TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsertTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        mTasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        setDropDown()
        binding.apply {
            back.setOnClickListener {
                findNavController().navigate(R.id.action_insertTaskFragment_to_tasksListFragment)
            }
            addButton.setOnClickListener{
                insertDataToDatabase()
            }
        }

        return view
    }

    private fun setDropDown() {
        taskCategory = ArrayAdapter.createFromResource(requireContext(),R.array.Category_list, android.R.layout.simple_list_item_1)
        binding.autoCategory.setAdapter(taskCategory)
    }
    private fun insertDataToDatabase() {
        binding.apply {
            val title = addTextTitle.text.toString()
            val description = addTextDescription.text.toString()
            val category = autoCategory.text.toString()

            if(inputCheck(title, description, category)) {
                lifecycleScope.launch {
                    val task = TaskData(0, title, description, category)
                    mTasksViewModel.insertTask(task)
                    Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.action_insertTaskFragment_to_tasksListFragment)
                }
            }else{
                Toast.makeText(requireContext(), "Please Fill Out All Fields!", Toast.LENGTH_LONG).show()
            }


        }
    }

    private fun inputCheck(title: String, description: String, category: String): Boolean {
        return !(TextUtils.isEmpty(title) || (TextUtils.isEmpty(description) || (TextUtils.isEmpty(category))))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }
}

