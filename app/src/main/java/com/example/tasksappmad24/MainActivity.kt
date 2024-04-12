package com.example.tasksappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasksappmad24.di.InjectorUtils
import com.example.tasksappmad24.models.Task
import com.example.tasksappmad24.models.getTasks
import com.example.tasksappmad24.ui.theme.TasksAppMAD24Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksAppMAD24Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    /*
                    val db = TaskDatabase.getDatabase(LocalContext.current)
                    val repository = TaskRepository(taskDao = db.taskDao())
                    val factory = TaskViewModelFactory(repository = repository)
                    val viewModel: TaskViewModel = viewModel(factory = factory)
                    val tasksState = viewModel.tasks.collectAsState()

                     */

                    val viewModel: TaskViewModel = viewModel(factory = InjectorUtils.provideTaskViewModelFactory(
                        LocalContext.current
                    ))
                    val tasksState = viewModel.tasks.collectAsState()
                    val coroutineScope = rememberCoroutineScope()

                    Column {
                        AddTask(
                            onAddClick = {task ->
                                coroutineScope.launch {
                                    viewModel.addTask(task)
                                }
                            }
                        )
                        TaskList(
                            tasks = tasksState.value,
                            onTaskChecked = {task ->
                                coroutineScope.launch {
                                    viewModel.toggleDoneState(task)
                                }},
                            onTaskDone = { task ->
                                coroutineScope.launch {
                                    viewModel.deleteTask(task)
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun AddTask(
    onAddClick: (Task) -> Unit = {}
) {
    var label by remember {
        mutableStateOf("")
    }

    Row {
        OutlinedTextField(
            value = label,
            onValueChange = {label = it}
        )

        Button(onClick = {
            onAddClick(Task(label = label))
            label = ""
        }) {
            Text(text = "Add")
        }
    }
}

@Composable
fun TaskList(
    tasks: List<Task> = remember { getTasks() },
    onTaskChecked: (Task) -> Unit = {},
    onTaskDone: (Task) -> Unit = {}
){

    LazyColumn{
        items(items = tasks){ task ->
            TaskItem(
                taskName = task.label,
                checked = task.isDone,
                onCheckedChange = { onTaskChecked(task) },
                onClose = { onTaskDone(task) })
        }
    }
}

@Composable
fun TaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}