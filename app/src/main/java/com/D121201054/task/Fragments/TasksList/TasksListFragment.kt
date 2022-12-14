package com.D121201054.task.Fragments.TasksList

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.D121201054.task.ViewModel.TasksViewModel
import com.D121201054.task.R
import com.D121201054.task.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment() {

    private var _binding: FragmentTasksListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mTasksViewModel : TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTasksListBinding.inflate(inflater, container, false)
        val view = binding.root

        //View Model
        val adapter = TasksAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        mTasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        mTasksViewModel.observeTasks.observe(viewLifecycleOwner) { task ->
            adapter.setData(task)
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_tasksListFragment_to_insertTaskFragment)
        }

        binding.menuDelete.setOnClickListener {
            deleteAllTasks()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    private fun deleteAllTasks() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mTasksViewModel.deleteAllTasks()
            Toast.makeText(
                requireContext(),
                "Successfully Removed All Tasks",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete All Tasks?")
        builder.setMessage("Are you sure you want to delete all tasks?")
        builder.create().show()
    }




}