# **GrubGo - Food Donation App**

## **Project Overview**  

GrubGo is a mobile application designed to reduce food wastage by connecting food donors with recipients in real time. The platform enables individuals, restaurants, NGOs, and organizations to donate surplus food while allowing recipients to request and access available food items. The app integrates location services to help users find nearby donors, ensuring a seamless and efficient food redistribution process.  

This project was developed as part of the **Mobile Application Development (MAD) course** and implements key software development principles, emphasizing user experience, security, and scalability.

![Image](https://github.com/user-attachments/assets/a72fa22a-ee5b-4d5b-b222-f81046759943)
![Image](https://github.com/user-attachments/assets/091e971b-ad13-4836-9989-0cb0682c4c05)
![Image](https://github.com/user-attachments/assets/5e3d0fd7-cc95-49db-bafb-5dc3dc820226)
![Image](https://github.com/user-attachments/assets/cc0e125a-661f-4da8-9320-ce256646a7ef)
![Image](https://github.com/user-attachments/assets/3cb37b4e-6efe-4e22-837e-c152343a5198)
![Image](https://github.com/user-attachments/assets/997c8888-34b5-4add-8c53-600dc17184b7)
![Image](https://github.com/user-attachments/assets/98c60ade-957c-42dd-b48d-cc6b59192ee0)
![Image](https://github.com/user-attachments/assets/89e67317-86e7-4f19-9223-d10b2599d99c)
![Image](https://github.com/user-attachments/assets/0761d227-924e-4939-8d3b-3ce20ff79610)
![Image](https://github.com/user-attachments/assets/9a348856-5d7f-49d4-90f6-5545977062a4)
---

## **Features and Functionality**  

### **User Authentication**  
- Users can register and log in using email and password authentication or Google Sign-In.  
- Firebase Authentication ensures secure login, session management, and password recovery.  

### **Food Donation System**  
- Donors can list surplus food with details such as food name, quantity, expiration date, and images.  
- Each donation entry is stored in a database and made available to recipients in real time.  

### **Food Receiving System**  
- Recipients can browse available food donations and submit requests based on their needs.  
- Once a request is made, donor details, including contact information, are displayed.  

### **Location-Based Services**  
- Google Maps API integration allows users to search for nearby food donors and NGOs.  
- Users can view the locations of donors on a map, improving accessibility.  

### **Order History Management**  
- Users can track their donation and request history for better record-keeping.  
- A history module provides an overview of previous transactions, with an option to clear records.  

### **User Profile Management**  
- Users can update their profile information, including name, email, and contact details.  
- Profile details are stored securely and synchronized with the Firebase database.  

### **Customer Support and Notifications**  
- Users can access customer support via a click-to-call functionality for quick assistance.  
- Real-time notifications keep users informed about new donations, requests, and updates.  

### **Security and Data Management**  
- Firebase Authentication ensures secure access control and user verification.  
- SQLite is used for local storage, allowing offline access and synchronization with Firebase when connectivity is restored.  

---

## **System Architecture**  

GrubGo follows a modular system architecture, ensuring scalability and efficiency. The major components include:

### **Frontend (User Interface)**
- Developed using **Kotlin** with **Android Studio** for a native Android experience.  
- Material Design principles are applied to ensure a clean and intuitive interface.  
- Key UI components include activity screens, RecyclerViews, and navigation elements.  

### **Backend (Database and Authentication)**  
- **Firebase Authentication**: Handles user registration, login, and session management.  
- **Firebase Firestore**: Stores real-time data such as user details, donations, and requests.  
- **SQLite Database**: Provides offline storage to enhance app usability in low-network conditions.  

### **API Integrations**
- **Google Maps API**: Enables location-based search for nearby food donors and NGOs.  
- **Firebase Cloud Messaging (FCM)**: Handles push notifications for new donations and updates.  

### **Core Functional Modules**
1. **Authentication Module** – Manages user login, registration, and session tracking.  
2. **Donation Module** – Handles food donation entries, including details and image uploads.  
3. **Receiving Module** – Enables users to browse and request available food donations.  
4. **FoodMap Module** – Uses Google Maps to show nearby food donors.  
5. **History Module** – Tracks past donations and received food requests.  
6. **Customer Care Module** – Provides direct support via phone or email.  

---

## **Implementation Details**  

### **Authentication Flow**  
- Firebase Authentication is used to verify user credentials.  
- Google Sign-In is integrated for quick authentication.  
- User sessions persist across app restarts for a seamless experience.  

### **Database Structure**  
- **Firebase Firestore**: Stores user data, donation listings, and request statuses.  
- **SQLite Local Storage**: Caches donation records for offline accessibility.  
- **Data Encryption**: Ensures security for sensitive user information.  

### **Data Flow**  
1. **User logs in or registers using Firebase Authentication.**  
2. **Donor submits a food donation entry**, which is stored in Firestore.  
3. **Recipient browses available donations**, selects an item, and submits a request.  
4. **System updates the database**, marks the donation as "Requested," and shares donor details.  
5. **Notifications are triggered for real-time updates on new donations and requests.**  

---

## **Technology Stack**  

### **Programming Language**  
- Kotlin (Android Development)  

### **Development Environment**  
- Android Studio  

### **Databases**  
- Firebase Firestore (Cloud Storage)  
- SQLite (Local Storage)  

### **APIs and Libraries**  
- Firebase Authentication (User Login & Registration)  
- Google Maps API (Location Services)  
- Firebase Cloud Messaging (Push Notifications)  
- Glide (Efficient Image Loading)  
- Retrofit (API Calls for future enhancements)  

---

## **Setup and Installation**  

### **Prerequisites**  
- Android Studio installed on your system.  
- A Firebase project set up with Firestore and Authentication enabled.  
- Google Maps API key configured for location-based services.  
