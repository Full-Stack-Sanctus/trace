# Customer and Product Management System

### Description

This project is a Sales Management System developed as a personal project by me.

### Features

- **Client Management**: Add, update, delete, and list clients.
- **Product Inventory**: Manage products with functionalities to add, update, delete, and list products.
- **Sales Transactions**: Handle sales operations including creating, viewing, and managing sales records.
- **User Authentication**: Secure login system for accessing the application.
- **Reporting**: Generate graphical reports for sales data analysis.
- **Database Integration**: Uses SQL databases for persistent storage of information.
- **Notifications and Alerts**: Notify administrators about product stock levels, client birthdays, and personalized messages.


### Setup Instructions

1.  **Clone the repository**:
    
```bash
    git clone https://github.com/Full-Stack-Sanctus/trace.git
    cd trace
```
    
  **Build the project**:
    
    -   Use maven to compile and build the project.
    
```bash
    mvn clean install
```
    
2.  **Run the application**:
    
    -   Execute the JAR file located in the `Sales` directory.
    
```bash
    mvn exec:java -Dexec.mainClass="com.trace.traceApp" > output.log 2>&1
```
    
