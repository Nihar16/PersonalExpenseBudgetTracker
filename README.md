![PersonalExpenseBudgetTracker](https://socialify.git.ci/Nihar16/PersonalExpenseBudgetTracker/image?name=1&pattern=Circuit+Board&theme=Auto)
# Personal Expense - Budget Tracker

A cross-platform application to manage your personal finances, track expenses, and stick to your budget. Built with Kotlin Multiplatform and Compose Multiplatform, this app is available on Android, iOS, Desktop, and Web.

## Overview

In today's fast-paced world, keeping track of your expenses is crucial for financial health. The Personal Expense - Budget Tracker app provides a simple and intuitive interface to monitor your spending, helping you stay on top of your budget and achieve your financial goals.

## Features

*   **Expense Tracking:** Easily add and manage your daily expenses.
*   **Categorization:** Organize your expenses into categories (e.g., Food, Transport, Entertainment).
*   **Budgeting:** Set monthly budgets for different categories to control your spending.
*   **Visualization:** Interactive charts and graphs to give you a clear view of your spending habits.
*   **History:** View a detailed history of all your transactions.
*   **Cross-Platform:** Seamless experience across Android, iOS, Desktop, and Web.

## How to Use the App

1.  **Adding an Expense:**
    *   Tap the '+' button on the main screen.
    *   Enter the expense amount, select a category, add a description, and set the date.
    *   Save the expense.

2.  **Setting a Budget:**
    *   Navigate to the 'Budgets' screen.
    *   Create a new budget for a specific category.
    *   Set the monthly budget amount.
    *   The app will track your spending against the set budget.

3.  **Viewing Reports:**
    *   Go to the 'Reports' screen.
    *   View your spending breakdown by category or over time.
    *   Analyze the charts to understand where your money is going.

## Screenshots

*(Here you can add screenshots of your application)*

![App Screenshot 1](path/to/screenshot1.png)
![App Screenshot 2](path/to/screenshot2.png)

## Technologies Used

This project is built with modern technologies to ensure a seamless experience across all platforms:

*   [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) for sharing business logic across Android, iOS, Desktop, and Web.
*   [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) for building a consistent and declarative UI for all the target platforms.
*   [Ktor](https://ktor.io/) for handling server-side logic and creating a robust backend.
*   [SQLDelight](https://github.com/cashapp/sqldelight) for a reliable local database to store and manage your financial data.
*   [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for managing asynchronous operations and ensuring the app is responsive.
*   [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) for efficient data serialization and deserialization.

## Build and Run

This is a Kotlin Multiplatform project. You can build and run it on different platforms as follows:

### Android Application

To build and run the development version of the Android app:
- Use the `composeApp` run configuration from the run widget in your IDE’s toolbar.
- Or run from the terminal:
  ```shell
  # on macOS/Linux
  ./gradlew :composeApp:assembleDebug

  # on Windows
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Desktop (JVM) Application

To build and run the development version of the desktop app:
- Use the `composeApp` run configuration from the run widget in your IDE’s toolbar.
- Or run from the terminal:
  ```shell
  # on macOS/Linux
  ./gradlew :composeApp:run

  # on Windows
  .\gradlew.bat :composeApp:run
  ```

### Server

To build and run the development version of the server:
- Use the `server` run configuration from the run widget in your IDE’s toolbar.
- Or run from the terminal:
  ```shell
  # on macOS/Linux
  ./gradlew :server:run

  # on Windows
  .\gradlew.bat :server:run
  ```

### Web Application

To build and run the development version of the web app:
- Use the `composeApp` run configuration from the run widget in your IDE's toolbar.
- Or run from the terminal:
    - For the Wasm target (faster, modern browsers):
      ```shell
      # on macOS/Linux
      ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
      
      # on Windows
      .\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
      ```
    - For the JS target (slower, supports older browsers):
      ```shell
      # on macOS/Linux
      ./gradlew :composeApp:jsBrowserDevelopmentRun

      # on Windows
      .\gradlew.bat :composeApp:jsBrowserDevelopmentRun
      ```

### iOS Application

To build and run the development version of the iOS app:
- Use the `iosApp` run configuration from the run widget in your IDE’s toolbar.
- Or open the `[/iosApp](./iosApp)` directory in Xcode and run it from there.

## Contributing

Contributions are welcome! If you have any ideas, suggestions, or find any bugs, feel free to open an issue or create a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
