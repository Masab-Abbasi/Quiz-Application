# ☕ Java Quiz Game Application

A **console-based** Java Quiz Game developed as a Programming Fundamentals university project. This project is designed to enhance logical thinking and demonstrate real-world programming skills using Java — **without** Object-Oriented Programming (OOP).

---

## 📌 Features

- ✅ **User Registration and Login**
  - New users register with proper input validation.
  - Duplicate username check to avoid conflicts.
  - Strong password policy to ensure secure credentials.

- 🔐 **Admin Panel**
  - Admin logs in using predefined credentials.
  - Admin can:
    - Add new quiz subjects.
    - Edit existing quiz files.
    - Delete quiz subjects.
    - View all student results.

- 📚 **Subject-Wise Quiz System**
  - Quiz subjects like English, Math, etc.
  - Each subject has a dedicated file containing 20 MCQs.
  - Questions are stored and read from external files.

- ⏱️ **Timer Functionality**
  - Timer is dynamically set based on difficulty (Easy/Medium/Hard).
  - Ensures users answer within the allowed time.

- 📊 **Result Evaluation**
  - Displays user score immediately after quiz completion.
  - Stores result history for admin review.

---

## 🎯 Project Objective

> Many beginner programmers focus only on syntax memorization. This project emphasizes:
- ✅ Real-world **logic building**
- ✅ Applying **file handling** techniques
- ✅ Using **loops**, **conditions**, and **methods**
- ✅ Strengthening problem-solving through interactive applications

---

## 💻 Technologies Used

| Technology   | Description                         |
|--------------|-------------------------------------|
| Java         | Core language used (without OOP)    |
| IntelliJ IDEA| IDE for writing and debugging code  |
| File Handling| Used `.txt` files to store quiz data, credentials, and results |
| Git & GitHub | Version control and collaboration   |

---

## 🧠 Key Concepts Applied

- 🔐 **Password Strength Validation**  
- 🔁 **Looping and Conditional Logic**  
- 📂 **File Reading & Writing**  
- ⚠️ **Exception Handling**: `InputMismatchException`, `FileNotFoundException`, etc.

---

## 🧪 Method Summary

| Method Name           | Description |
|-----------------------|-------------|
| `main()`              | Entry point of the program |
| `registerUser()`      | Handles user registration and duplicate checks |
| `loginUser()`         | Verifies user credentials for login |
| `adminLogin()`        | Verifies admin credentials |
| `showUserQuizMenu()`        | Shows User Menu |
| `showAdminQuizMenu()`        | Shows Admin Menu |
| `subjects()`        | Shows a list of Subjects Available |
| `startQuix()`        | Gets Difficulty from the user & then Accordingly Load Quiz Files |
| `takeQuiz()`          | Loads quiz questions and handles user interaction |
| `isStrong()`          | Checks for Password Strength |
| `viewResults()`       | Displays stored quiz results |
| `viewAllResults()`       | Displays all stored quiz results (only allowed to Admin) |
| `addQuiz()`    | Allows admin to add a new quiz subject |
| `editQuiz()`      | Enables admin to update existing questions |
| `deleteQuiz()`     | Removes quiz subject file from system |

---

## 🚀 How to Run the Program

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Masab-Abbasi/Quiz-Application.git
   cd Quiz-Application
