package com.example.lab7

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class StudyTask(
    val id: Int,
    val title: String,
    val course: String,
    val isDone: Boolean = false
)

data class PlannerUiState(
    val tasks: List<StudyTask> = emptyList()
) {
    val totalTasks: Int
        get() = tasks.size

    val doneTasksCount: Int
        get() = tasks.count { it.isDone }
}

@Composable
fun TaskItem(
    task: StudyTask,
    onTaskClick: (Int) -> Unit,
    onDoneChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        onClick = { onTaskClick(task.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.course,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { checked ->
                    onDoneChange(task.id, checked)
                }
            )
        }
    }
}

@Composable
fun PlannerHomeScreen(
    uiState: PlannerUiState,
    onTaskClick: (Int) -> Unit,
    onDoneChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Progress: ${uiState.doneTasksCount} of ${uiState.totalTasks} done",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = uiState.tasks,
                key = { it.id }
            ) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = onTaskClick,
                    onDoneChange = onDoneChange
                )
            }
        }
    }
}

@Composable
fun PlannerHomeRoute(
    onNavigateToDetail: (Int) -> Unit
) {
    var uiState by remember {
        mutableStateOf(
            PlannerUiState(
                tasks = listOf(
                    StudyTask(1, "Mobile App Lab", "CSC 402", false),
                    StudyTask(2, "Image Processing Assignment", "ARTI 404", false),
                    StudyTask(3, "HCI Project Proposal", "CSC", true),
                    StudyTask(4, "Study for Midterm", "CSC 402", false),
                    StudyTask(5, "Read Research Paper", "ARTI", false)
                )
            )
        )
    }

    PlannerHomeScreen(
        uiState = uiState,
        onTaskClick = { taskId ->
            onNavigateToDetail(taskId)
        },
        onDoneChange = { taskId, isDone ->
            uiState = uiState.copy(
                tasks = uiState.tasks.map { task ->
                    if (task.id == taskId) task.copy(isDone = isDone) else task
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TaskItemNotDonePreview() {
    MaterialTheme {
        TaskItem(
            task = StudyTask(1, "Mobile App Lab", "CSC 402", false),
            onTaskClick = {},
            onDoneChange = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemDonePreview() {
    MaterialTheme {
        TaskItem(
            task = StudyTask(2, "Image Processing Assignment", "ARTI 404", true),
            onTaskClick = {},
            onDoneChange = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlannerHomeScreenPreview() {
    MaterialTheme {
        PlannerHomeScreen(
            uiState = PlannerUiState(
                tasks = listOf(
                    StudyTask(1, "Task 1", "Course A", false),
                    StudyTask(2, "Task 2", "Course B", true),
                    StudyTask(3, "Task 3", "Course C", false),
                    StudyTask(4, "Task 4", "Course D", false),
                    StudyTask(5, "Task 5", "Course E", true)
                )
            ),
            onTaskClick = {},
            onDoneChange = { _, _ -> }
        )
    }
}
