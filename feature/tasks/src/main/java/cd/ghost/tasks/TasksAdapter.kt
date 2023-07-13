package cd.ghost.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cd.ghost.data.Task
import  androidx.recyclerview.widget.ListAdapter
import cd.ghost.tasks.databinding.ItemTaskBinding

interface TaskItemClickListener {
    fun itemClick(task: Task)
    fun itemCheckBoxClick(task: Task, isCompleted: Boolean)
}

class TasksAdapter(
    private val itemClickListener: TaskItemClickListener
) : ListAdapter<Task, TasksAdapter.TaskViewHolder>(ItemDiffCallBack()) {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {

                titleText.text = task.titleForList
                if (task.isCompleted) {
                    titleText.paintFlags = titleText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    titleText.paintFlags =
                        titleText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }

                completeCheckbox.isChecked = task.isCompleted
                completeCheckbox.setOnClickListener {
                    itemClickListener.itemCheckBoxClick(task, completeCheckbox.isChecked)
                }

            }
            itemView.setOnClickListener {
                itemClickListener.itemClick(task)
            }
        }
    }

    class ItemDiffCallBack : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            binding = ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}