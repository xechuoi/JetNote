package com.dungngminh.jetpack_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dungngminh.jetpack_2.model.ToDo
import com.dungngminh.jetpack_2.ui.theme.Jetpack_2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContent {
            Jetpack_2Theme {
                // A surface container using the 'background' color from the theme
                Scaffold(

                ) {
                    Column() {
                        Header()
                        Spacer(modifier = Modifier.height(8.dp))
                        NotePanel(viewModel = viewModel)
                    }


                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotePanel(viewModel: MainViewModel) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val stateScroll = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val list: State<List<ToDo>> = viewModel.listTodos.collectAsState(listOf())
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        OutlinedTextField(
            title,
            onValueChange = { title = it },
            label = { Text("Title") },
            placeholder = { Text("Enter a title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            description,
            onValueChange = { description = it },
            label = { Text("Add a note") },
            placeholder = { Text("Add a note") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),

            )
        Spacer(modifier = Modifier.height(16.dp))
        Button(shape = RoundedCornerShape(size = 20.dp), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), onClick = {
            if (title.isNotEmpty() && description.isNotEmpty()) {
                val date = Date.from(
                    LocalDateTime.now()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                )
                val todo = ToDo(null, title = title.trim(), description = description.trim(), dateAdded = date)
                viewModel.viewModelScope.launch {
                    viewModel.insertTodo(todo)
                }
                title = ""
                description = ""
                keyboardController?.hide()
            }
        }) {
            Text("Save", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(state = stateScroll) {
            coroutineScope.launch {
                stateScroll.animateScrollToItem(0,0)
            }
            items(list.value, key = { todo -> todo.id!! }) { todo ->

                NoteTile(
                    title = todo.title,
                    description = todo.description,
                    dateAdded = todo.dateAdded
                )

            }
        }

    }
}


@Composable
fun Header() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffaee8de))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "JetNote",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            )
            Icon(Icons.Rounded.Notifications, contentDescription = "")
        }
    }
}

@Composable
fun NoteTile(title: String, description: String, dateAdded: Date) {
    val date = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(dateAdded)
    Card(
        shape = RoundedCornerShape(bottomStart = 30.dp, topEnd = 30.dp),
        backgroundColor = Color(0xffaee8de),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(title, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(date, fontSize = 18.sp)
        }
    }
}