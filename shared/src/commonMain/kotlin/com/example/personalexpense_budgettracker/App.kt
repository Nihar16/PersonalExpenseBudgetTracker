@file:OptIn(kotlin.time.ExperimentalTime::class)

package com.example.personalexpense_budgettracker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.time.ExperimentalTime

// --- DATA --- //

data class Expense(
    val id: Long,
    val description: String,
    val amount: Double,
    val category: String
)

val sampleExpenses = mutableStateListOf(
    Expense(id = 1, description = "Groceries", amount = 75.50, category = "Food"),
    Expense(id = 2, description = "Gas", amount = 40.00, category = "Transport"),
    Expense(id = 3, description = "Movie Tickets", amount = 25.00, category = "Entertainment")
)

enum class AppTheme {
    SYSTEM, LIGHT, DARK
}

// --- THEME --- //
private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF3700B3),
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF3700B3),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private fun getAppShapes() = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

// --- NAVIGATION --- //

sealed class Screen {
    object Dashboard : Screen()
    data class AddEditExpense(val expense: Expense? = null) : Screen()
    object Settings : Screen()
}

// --- APP ROOT --- //

@Composable
fun App() {
    var appTheme by remember { mutableStateOf(AppTheme.SYSTEM) }
    val useDarkTheme = when (appTheme) {
        AppTheme.SYSTEM -> isSystemInDarkTheme()
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
    }

    MaterialTheme(colorScheme = if (useDarkTheme) DarkColorPalette else LightColorPalette, shapes = getAppShapes()) {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Dashboard) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (val screen = currentScreen) {
                is Screen.Dashboard -> DashboardScreen(
                    expenses = sampleExpenses,
                    onAddExpense = { currentScreen = Screen.AddEditExpense() },
                    onEditExpense = { expense -> currentScreen = Screen.AddEditExpense(expense) },
                    onSettingsClicked = { currentScreen = Screen.Settings }
                )
                is Screen.AddEditExpense -> AddEditExpenseScreen(
                    expense = screen.expense,
                    onSave = { expense ->
                        if (screen.expense == null) {
                            sampleExpenses.add(expense)
                        } else {
                            val index = sampleExpenses.indexOfFirst { it.id == expense.id }
                            if (index != -1) {
                                sampleExpenses[index] = expense
                            }
                        }
                        currentScreen = Screen.Dashboard
                    },
                    onBack = { currentScreen = Screen.Dashboard }
                )
                is Screen.Settings -> SettingsScreen(
                    currentTheme = appTheme,
                    onThemeChange = { appTheme = it },
                    onBack = { currentScreen = Screen.Dashboard }
                )
            }
        }
    }
}

// --- SCREENS --- //

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    expenses: List<Expense>,
    onAddExpense: () -> Unit,
    onEditExpense: (Expense) -> Unit,
    onSettingsClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = onSettingsClicked) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpense) {
                Icon(Icons.Filled.Add, contentDescription = "Add Expense")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    PieChartPlaceholder(expenses = expenses)
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    BarChartPlaceholder()
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Recent Expenses",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(expenses) { expense ->
                    ExpenseItem(
                        expense = expense,
                        onClick = { onEditExpense(expense) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    expense: Expense?,
    onSave: (Expense) -> Unit,
    onBack: () -> Unit
) {
    var description by remember { mutableStateOf(expense?.description ?: "") }
    var amount by remember { mutableStateOf(expense?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(expense?.category ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (expense == null) "Add Expense" else "Edit Expense") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    if (it.matches(Regex("^\\d*\\.?\\d*\$"))) {
                        amount = it
                    }
                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val newAmount = amount.toDoubleOrNull()
                    if (newAmount != null && description.isNotBlank() && category.isNotBlank()) {
                        onSave(
                            Expense(
                                id = expense?.id ?: kotlin.time.Clock.System.now().toEpochMilliseconds(),
                                description = description,
                                amount = newAmount,
                                category = category
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentTheme: AppTheme,
    onThemeChange: (AppTheme) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Theme", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                AppTheme.entries.forEach { theme ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (theme == currentTheme),
                                onClick = { onThemeChange(theme) }
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (theme == currentTheme),
                            onClick = null // null because the Row is selectable
                        )
                        // FIX: This line was incomplete and is now fixed.
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = theme.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

// --- COMPONENTS --- //

@Composable
fun BarChartPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Weekly", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val barValues = listOf(0.4f, 0.7f, 0.5f, 0.9f, 0.6f)
            barValues.forEach { value ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(value)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun PieChartPlaceholder(modifier: Modifier = Modifier, expenses: List<Expense>) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Categories", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Box(contentAlignment = Alignment.Center) {
            val colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.tertiary,
                MaterialTheme.colorScheme.error,
                Color.Gray
            )

            Canvas(modifier = Modifier.size(100.dp)) {
                val total = expenses.sumOf { it.amount }.toFloat()
                if (total > 0f) {
                    var startAngle = 0f
                    expenses.forEachIndexed { index, expense ->
                        val sweepAngle = (expense.amount.toFloat() / total) * 360f
                        drawArc(
                            color = colors[index % colors.size],
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = true
                        )
                        startAngle += sweepAngle
                    }
                } else {
                    // Empty state circle
                    drawCircle(color = Color.LightGray, style = Stroke(width = 5f))
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = expense.description, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = expense.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Text(
                text = "$${expense.amount}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
