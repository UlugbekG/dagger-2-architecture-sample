package cd.ghost.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


/**
 * Immutable model class for a Task. In order to compile with Room, we can't use @JvmOverloads to
 * generate multiple constructors.
 *
 * @param title       title of the task
 * @param description description of the task
 * @param isCompleted whether or not this task is completed
 * @param id          id of the task
 */
@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "completed") var isCompleted: Boolean = false,
    @PrimaryKey @ColumnInfo(name = "entryid") val id: String = UUID.randomUUID().toString()
) {
    val titleForList: String
        get() = title.ifEmpty { description }

    val isActive
        get() = !isCompleted

    val isEmpty
        get() = title.isEmpty() || description.isEmpty()
}