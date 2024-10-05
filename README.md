# Employee Time Tracker

## Overview
The **Employee Time Tracker** is a web application designed to help employees log, edit, and manage their daily tasks while tracking their working hours. It features user role-based access and visual data representations for easy tracking.

## Features
- **Add, Edit, and Delete Tasks:** Employees can log daily tasks including task name, description, and time duration.
- **Role-Based Access Control (RBAC):** User roles include Admin and Associate with permissions based on the role.
- **Work Hour Summaries:** Visual charts for daily, weekly, and monthly work summaries using dynamic graphs.
- **Task Validations:** Tasks are restricted to a maximum of 8 hours, and duplicate entries are prevented.
- **Admin Dashboard:** Admin users can view task reports of all employees or specific projects over different timeframes.

## Technologies Used
- **Backend:** Java, JSP, Servlets
- **Frontend:** HTML, CSS, JavaScript (for charts)
- **Database:** MySQL
- **Server:** Apache Tomcat
- **Libraries:** Gson (for JSON handling), Chart.js (for data visualization)

## Database Schema
The application uses a normalized database schema with the following key tables:
- **Employee:** Stores employee details including roles.
- **Task:** Logs task information, including the task name, description, date, and working hours.

## Installation & Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/employee-time-tracker.git
