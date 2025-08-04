```markdown
# QuickBite 🍔 – Android Food Delivery App

**QuickBite** is a modular, full-stack food delivery application with separate Android apps for customers, delivery riders, and restaurants. Built using Kotlin and Jetpack Compose for the frontend and Ktor with MySQL for the backend, it demonstrates real-world implementation of authentication, stateful cart management, map-based address selection, and secure payments via Stripe.

---

## ✨ Features

- 🔐 **Authentication**: Email/password via Ktor backend; Google/Facebook sign-in via Google Cloud  
- 🛒 **Ordering System**: Restaurant listing, food browsing, cart management, and checkout  
- 📍 **Maps Integration**: Google Maps for address selection and geocoding  
- 💳 **Payments**: Seamless integration with Stripe API  
- 🔔 **Notifications**: Push notifications and order updates  
- 📦 **Role-based Variants**: Separate apps for Customers, Riders, and Restaurants using product flavors  

---

## 🧱 Tech Stack

| Layer         | Tech Used                        |
|--------------|----------------------------------|
| Frontend     | Kotlin, Jetpack Compose, XML     |
| Backend      | Ktor (Kotlin), MySQL             |
| Auth         | Google Cloud Auth, JWT           |
| Location     | Google Maps & Geocoding API      |
| Payments     | Stripe API                       |
| Storage      | Supabase for image uploads       |
| State Mgmt   | ViewModel, Kotlin Flow           |
| Notifications| Firebase Cloud Messaging (FCM)   |
| IDE          | Android Studio + IntelliJ        |

---

## 📲 App Variants

QuickBite uses **product flavors** to build separate role-based apps:

- **Customer App** – Browse restaurants, order food, make payments  
- **Rider App** – View and deliver customer orders  
- **Restaurant App** – Manage food items and incoming orders  

---

## 📂 Project Structure

```

QuickBite/
├── customer-app/
├── restaurant-app/
├── rider-app/
├── ktor-backend/
└── shared-resources/

```

---

## 🚀 Future Enhancements

- 📍 WebSocket-based live rider tracking  
- 🔧 Rider app UI refinement  
- 📈 Real-time restaurant inventory management  

---

<details>
<summary><strong>📜 Commits and Their Respective Changes</strong></summary>

```

1. Implemented splash screen and fixed theme overlay issues
2. Setup Retrofit, fonts, Dagger-Hilt for DI
3. Designed Sign-Up screen and ViewModel
4. Enabled Sign-Up with backend, learned networking concepts (client-server via IP)
5. Implemented navigation and sign-in functionality
6. Completed Google sign-in and transition animations
7. Handled edge cases in authentication, JWT session setup, started Home screen
8. Implemented restaurant list, menus, shared animations
9. Completed Cart, bottom nav, food details, maps/address structure
10. Map integration initiated
11. Payment integration initiated
12. Google Maps and Stripe both functional
13. Order history/categorization + push notifications + notification UI
14. Implemented product flavors for Customer, Rider, Restaurant
15. Finalized and tested all three variants. Project is complete.

```

</details>

---

## 👨‍💻 About the Project

QuickBite was developed as part of a full-stack Android learning journey. It integrates both client and server-side technologies, emphasizing real-world development practices—from authentication and payment to modular app structuring and scalable backend design.

---

```

---

You can now paste this directly in your `README.md`. Let me know if you want badges (like “Made with Kotlin”, “MIT License”, etc.) added at the top!
