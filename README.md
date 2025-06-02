
# Insurance Report Generator

A Spring Boot web application for generating insurance plan reports. Users can search for insurance plans by filters, view results, download reports as PDF or Excel, and optionally receive the report via email.

## ✨ Features

- 🔍 Filter insurance plans by name, status, gender, and date range
- 📄 Download search results as **PDF** or **Excel**
- 📧 Optionally send downloaded file to provided email
- 🎨 Clean UI with Thymeleaf and Bootstrap

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Thymeleaf
- Bootstrap 5
- Jakarta Mail API

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL database

### Clone and Run

```bash
git clone https://github.com/your-username/insurance-report-generator.git
cd insurance-report-generator
mvn spring-boot:run
```

### Database Configuration

Edit `application.properties` to match your PostgreSQL setup:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Email Configuration (Gmail)

Enable 2-step verification and generate an App Password:
```
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```

## 📸 UI Preview

> Add screenshots here for homepage, results table, download options

## 📂 Project Structure

- `controller/` – Web endpoints and logic
- `service/` – Business logic for export and search
- `repository/` – JPA Repositories
- `utils/` – Excel, PDF generators, and Mail sender
- `templates/` – Thymeleaf templates
- `static/` – CSS, JS (if needed)

## 📃 License

This project is licensed under the MIT License.

---

**Author:** Himansu Mallick
