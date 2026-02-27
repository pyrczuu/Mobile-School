package com.example.mobileschool1

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileschool1.models.TodoItem
import com.example.mobileschool1.ui.theme.MobileSchool1Theme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileSchool1Theme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "ToDoList"
                    )
                    {
                        composable("ToDoList"){
                            TodoListScreen(modifier = Modifier.padding(innerPadding), navController = navController)
                        }
                        composable("ToDoDetails"){
                            Text(text = "Placeholder", modifier = Modifier.padding())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemCard(
    todo: TodoItem,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggleComplete() }
            )
            Text(
                modifier = Modifier.weight(1f),
                text = todo.name,
                fontWeight = FontWeight.Bold,
                textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null,
            )
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun TodoListScreen(modifier: Modifier = Modifier, navController: NavController) {
    var todoText by remember { mutableStateOf("") }
    val todoItems = remember { mutableStateListOf<TodoItem>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = todoText,
                onValueChange = { todoText = it },
                placeholder = { Text("Add a TODO :P") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                if (todoText.isNotBlank()) {
                    todoItems.add(
                        TodoItem(
                            id = System.currentTimeMillis(),
                            name = todoText,
                            isCompleted = false
                        )
                    )
                    todoText = ""
                }
            }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add a todo",
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(todoItems) { todoItem ->
                TodoItemCard(todoItem, onToggleComplete = {
                    val index = todoItems.indexOf(todoItem)
                    todoItems[index] = todoItem.copy(isCompleted = !todoItem.isCompleted )
                }, onDelete = {
                    todoItems.remove(todoItem)
                },
                    onClick = {
                        navController.navigate("ToDoDetails")
                    })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemCardPreview() {
    MobileSchool1Theme {
        TodoItemCard(
            todo = TodoItem(id = 1, name = "test", isCompleted = false),
            onToggleComplete = {},
            onDelete = {},
            onClick = {})
    }
}