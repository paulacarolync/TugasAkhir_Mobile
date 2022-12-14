package com.D121201054.task.Fragments.UpdateTask

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.D121201054.task.Model.TaskData
import com.D121201054.task.R
import com.D121201054.task.ViewModel.TasksViewModel
import com.D121201054.task.databinding.FragmentUpdateTaskBinding
import kotlinx.coroutines.launch

class UpdateTaskFragment : Fragment() {

    private var _binding: FragmentUpdateTaskBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateTaskFragmentArgs>()
    private lateinit var mTasksViewModel : TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        mTasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        binding.apply {
            updateTextTitle.setText(args.currentTask.title)
            updateTextDescription.setText(args.currentTask.description)
            updateCategory.setText(args.currentTask.category)
            updateCategory.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.Category_list, android.R.layout.simple_list_item_1))

            back.setOnClickListener {
                findNavController().navigate(R.id.action_updateTaskFragment_to_tasksListFragment)
            }
            updateButton.setOnClickListener{
                updateTask()
            }

            menuDelete.setOnClickListener {
                deleteTask()
            }

        }
        // Inflate the layout for this fragment
        return view
    }

    private fun updateTask() {
        binding.apply {
            val title = updateTextTitle.text.toString()
            val description = updateTextDescription.text.toString()
            val category = updateCategory.text.toString()

            if(inputCheck(title, description, category)) {
                lifecycleScope.launch {
                    val updatedtask = TaskData(args.currentTask.entryid, title, description, category)
                    mTasksViewModel.updateTask(updatedtask)
                    Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.action_updateTaskFragment_to_tasksListFragment)
                }
            }else{
                Toast.makeText(requireContext(), "Please Fill Out All Fields!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(title: String, description: String, category: String): Boolean {
        return !(TextUtils.isEmpty(title) && (TextUtils.isEmpty(description) && (TextUtils.isEmpty(category))))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

    }

    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mTasksViewModel.deleteTask(args.currentTask)
            Toast.makeText(
                requireContext(),
                "Successfully Removed Task",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateTaskFragment_to_tasksListFragment)
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete Task?")
        builder.setMessage("Are you sure you want to delete this task?")
        builder.create().show()
    }
}
