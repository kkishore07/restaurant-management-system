# restaurant-management-system


📌 Project Summary

The Restaurant Management System (RMS) is a Java-based application with DBMS integration designed to streamline and automate restaurant operations. The system enables the Admin/Manager to manage menu items, staff, and generate sales reports, while Customers can place orders and view bills. Staff/Waiters can take orders, update order status, and assist customers efficiently.

By applying Object-Oriented Programming (OOP) principles such as encapsulation, inheritance, and polymorphism, the project ensures a modular and reusable design. The use of a MongoDB as databse ,which provides reliable data storage for orders, customers, bills, and staff records.

This system reduces manual errors, speeds up order processing, ensures accurate billing, and improves customer experience. It also serves as a practical demonstration of combining Java programming with database management for real-world applications.

⚙️ Features

✅ Admin can add, update, delete menu items

✅ Customers can browse menu and place orders

✅ System generates bills automatically (with tax/discount)

✅ Staff can update order status (Pending, Preparing, Completed, Delivered)

✅ Database-backed storage for reliability and consistency

✅ Reports on daily/monthly sales


🔗OOP Concepts Used

-> Encapsulation → Classes like Order, MenuItem, and Bill encapsulate fields and expose controlled methods.

-> Inheritance → Customer and Staff inherit from the User class.

-> Polymorphism → Methods like login() or viewDetails() behave differently for admin, customer, and staff.

-> Abstraction → DAO (Data Access Object) classes abstract database operations.

-> Composition → Order contains multiple OrderItem objects; Bill is generated from Order.


UML Diagram

<img width="1848" height="811" alt="Screenshot 2025-08-26 144341" src="https://github.com/user-attachments/assets/435610e7-5a02-4eb7-8458-72a6e2047ca4" />

