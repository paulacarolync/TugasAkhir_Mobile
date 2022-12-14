package com.D121201054.task.Fragments.TasksList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.D121201054.task.Model.TaskData
import com.D121201054.task.ViewModel.TasksViewModel
import com.D121201054.task.Fragments.InsertTask.InsertTaskFragment
import com.D121201054.task.R

@Suppress("DEPRECATION")
class TasksAdapter: RecyclerView.Adapter<TasksAdapter.TasksviewHolder>() {

    private var taskList = emptyList<TaskData>()
    private lateinit var tasksViewModel : TasksViewModel
    private var context: Context? = null

    class TasksviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView.findViewById(R.id.title)
        val description:TextView = itemView.findViewById(R.id.description)
        val category:TextView = itemView.findViewById(R.id.category)
        val layout:ConstraintLayout = itemView.findViewById(R.id.rowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksviewHolder {
        context = parent.context
        tasksViewModel = ViewModelProvider(context as FragmentActivity)[TasksViewModel::class.java]
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_card_view, parent, false)
        return TasksviewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasksviewHolder, position: Int) {
        val currentTask = taskList[position]

        holder.title.text = currentTask.title
        holder.description.text = currentTask.description
        holder.category.text = currentTask.category

        when(currentTask.category) {
            context!!.resources.getStringArray(R.array.Category_list)[0] -> {
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.red))
            }
            context!!.resources.getStringArray(R.array.Category_list)[1] -> {
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.orange))
            }
            context!!.resources.getStringArray(R.array.Category_list)[2] -> {
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.yellow))
            }
            context!!.resources.getStringArray(R.array.Category_list)[3] -> {
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.green))
            }
        }
        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context,InsertTaskFragment::class.java)
            intent.putExtra("id",position)
            holder.itemView.context.startActivity(intent)
        }

        holder.layout.setOnClickListener{
            val action = TasksListFragmentDirections.actionTasksListFragmentToUpdateTaskFragment(currentTask)
            holder.itemView.findNavController().navigate(action)
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(task: List<TaskData>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}