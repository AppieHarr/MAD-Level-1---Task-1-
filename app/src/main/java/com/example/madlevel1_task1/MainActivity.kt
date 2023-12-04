package com.example.madlevel1_task1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.madlevel1_task1.model.Reminder
import com.example.madlevel1_task1.ui.theme.MADLevel1Task1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Level3ExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    RemindersNavHost(navController = navController, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun ReminderListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        content = { padding -> ScreenContent(Modifier.padding(padding)) },
    )
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    // Context is needed for displaying a Toast message, as in level 1 example.
    val context = LocalContext.current

    // Variable to hold and save the value of the new reminder text entered by the user, as in level 1 example.
    var newReminderData by remember { mutableStateOf(String()) }

    val reminders = remember { mutableStateListOf<Reminder>() }

    //apply argument modifier on top level element, recommended since compose 1.2.0
    Column(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                // TODO Provide value, placeholder, onValueChange, and label by yourself, similar to level 1 example app.
                value = newReminderData,
                onValueChange = {
                    newReminderData = it
                },
                label = { Text(text = "Reminder") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                //TODO Complete the button definition.
                onClick = {
                    if (reminders.isEmpty()) {
                        reminders.add(Reminder(newReminderData))
                        Toast.makeText(context, "Reminder added", Toast.LENGTH_SHORT).show()
                    } else {
                        for (reminder in reminders) {
                            if (reminder.reminderData == newReminderData) {
                                Toast.makeText(
                                    context,
                                    "Reminder already exists",
                                    Toast.LENGTH_SHORT
                                ).show()
                                break
                            } else {
                                reminders.add(Reminder(newReminderData))
                                Toast.makeText(context, "Reminder added", Toast.LENGTH_SHORT).show()
                                break
                            }
                        }
                    }
                })
                    {
                        // add Text(..)
                        Text(text = "Add reminder")
                    }
        }

        LazyColumn {
            items(items = reminders, itemContent = { reminder ->
                Text(
                    text = reminder.reminderData,
                    Modifier.padding(16.dp)
                )
                Divider(
                    color = Color.LightGray, modifier = Modifier.alpha(0.5f),
                    thickness = 1.dp
                )
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MADLevel1Task1Theme {
        ReminderListScreen()
    }
}