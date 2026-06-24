# 🏋️ FitLife Gym System

A console-based **Gym Management System** built in Java, demonstrating core Object-Oriented Programming principles including abstraction, inheritance, polymorphism, custom exception handling, and JDBC database connectivity with PostgreSQL.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [OOP Concepts Used](#oop-concepts-used)
- [Database Setup](#database-setup)
- [How to Run](#how-to-run)
- [Usage Guide](#usage-guide)
- [Team Contributions](#team-contributions)

---

## Overview

FitLife Gym System allows an admin to manage gym trainers and customers through a menu-driven console application. Members can register, log in, and view their profile. All data is stored and retrieved from a PostgreSQL database using JDBC.

---

## ✅ Features

- 🔐 Admin login with ID authentication
- 👤 Member login by auto-generated ID (e.g. T001, C001)
- ➕ Register new Trainers and Customers
- 💳 Three membership tiers — Normal, VIP, Premium
- 📋 View all members, trainers only, or customers only (password protected)
- 🗄️ Full PostgreSQL database persistence via JDBC
- ⚠️ Input validation with custom exception handling

---

## 📁 Project Structure

```
Gym-Management-System/
│
├── Person.java           # Abstract base class for all members
├── GymException.java     # Custom checked exception for validation
├── Trainer.java          # Trainer subclass (extends Person)
├── Customer.java         # Customer subclass (extends Person)
├── DBManager.java        # JDBC database layer (PostgreSQL)
├── GymSystem.java        # Main entry point — login & navigation
├── MemberService.java    # Registration & listing logic
│
└── lib/
    ├── postgresql-42.7.11.jar
    └── mysql-connector-j-9.1.0.jar
```

---

## 🧠 OOP Concepts Used

| Concept | Where It's Applied |
|---|---|
| **Abstraction** | `Person` is abstract — `getDetails()` is abstract, forcing subclasses to implement it |
| **Inheritance** | `Trainer` and `Customer` both extend `Person` |
| **Polymorphism** | `Person` reference calls correct `toString()` for Trainer or Customer at runtime |
| **Encapsulation** | All fields are private with public getters |
| **Custom Exception** | `GymException` thrown by `Person.validate()` for invalid input |
| **Static members** | `trainerCount` and `customerCount` in `Person` for auto ID generation |
| **JDBC** | `DBManager` handles all database operations using `PreparedStatement` and `ResultSet` |
| **try/catch/finally** | Used in all registration methods — always prints completion message |

---

## 🗄️ Database Setup

Make sure PostgreSQL is installed and running, then create the database and tables:

```sql
CREATE DATABASE gym_db;

\c gym_db

CREATE TABLE trainers (
    id      VARCHAR(10) PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    age     INT NOT NULL,
    phone   VARCHAR(20) NOT NULL,
    specialty VARCHAR(100) NOT NULL
);

CREATE TABLE customers (
    id              VARCHAR(10) PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    age             INT NOT NULL,
    phone           VARCHAR(20) NOT NULL,
    membership_type VARCHAR(20) NOT NULL,
    price           DOUBLE PRECISION NOT NULL
);
```

Then update the credentials in `DBManager.java` if needed:

```java
private static final String URL      = "jdbc:postgresql://localhost:5432/gym_db";
private static final String USER     = "postgres";
private static final String PASSWORD = "your_password";
```

---

## ▶️ How to Run

**1. Clone the repository**
```bash
git clone https://github.com/fikernew/Gym-Management-System.git
cd Gym-Management-System
```

**2. Compile with the PostgreSQL driver**
```bash
javac -cp .:lib/postgresql-42.7.11.jar *.java
```
> On Windows use `;` instead of `:` → `-cp .;lib/postgresql-42.7.11.jar`

**3. Run the application**
```bash
java -cp .:lib/postgresql-42.7.11.jar GymSystem
```

---

## 📖 Usage Guide

```
========================================
     Welcome to FitLife Gym System
========================================

--- Main Menu ---
1. Admin Login
2. Member Login
3. Member Register
4. View Members
5. Exit
```

| Option | Description |
|---|---|
| Admin Login | Enter `ADMIN001` to access admin panel |
| Member Login | Enter your ID (e.g. `T001` or `C001`) to view your profile |
| Member Register | Register as a new Trainer or Customer |
| View Members | Password-protected list of all registered members |

**Membership Tiers:**

| Type | Price | Benefits |
|---|---|---|
| Normal | $20/month | Basic gym access |
| VIP | $50/month | Gym access + Personal Trainer |
| Premium | $100/month | Gym access + Personal Trainer + Nutrition Plan |

---

## 👥 Team Contributions

| Student | Files | Responsibility |
|---|---|---|
| Student 1 | `Person.java` + `GymException.java` | Abstract base class & validation exception |
| Student 2 | `Trainer.java` + `Customer.java` | Person subclasses (inheritance) |
| Student 3 | `DBManager.java` + `lib/` | JDBC database layer |
| Student 4 | `GymSystem.java` | App entry point, login & navigation |
| Student 5 | `MemberService.java` | Registration & member listing |

---

## 🛠️ Built With

- **Java** — Core language
- **PostgreSQL** — Database
- **JDBC** — Database connectivity
- **IntelliJ IDEA** — IDE

---

*FitLife Gym System — Group Project | Object-Oriented Programming Course*

⁠* core business logic extracting authentication handles cleanly.⁠
