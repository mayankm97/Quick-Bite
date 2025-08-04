```markdown
# QuickBite 🍔 – Android Food Delivery App

QuickBite is a modern, modular Android application for food ordering and delivery. It connects customers with nearby restaurants and streamlines the process from discovery to checkout. Built using Kotlin, Jetpack Compose, and Ktor backend (with MySQL), it demonstrates full-stack mobile development including authentication, cart management, location handling, and payment integration.

---

## ✨ Features

### 🔑 Authentication
- Email/Password Sign-Up & Login via custom Ktor backend
- Google and Facebook Sign-In via Google Cloud

### 🧾 Ordering System
- Browse restaurants and food items
- Add to cart, update quantity, and checkout
- Real-time cart state management

### 📍 Location & Maps
- Address selection using Google Maps
- Reverse geocoding API for address details

### 💳 Payments
- Stripe integration for secure payments

### 🔄 App Architecture
- Separate modules or app variants for:
  - Customers
  - Delivery Riders
  - Restaurants

---

## 🧱 Tech Stack

| Layer         | Tech Used                        |
|--------------|----------------------------------|
| Frontend     | Kotlin, Jetpack Compose, XML     |
| Backend      | Ktor (Kotlin), MySQL             |
| Auth         | Google Cloud Auth                |
| Location     | Google Maps & Geocoding API      |
| Payments     | Stripe API                       |
| Storage      | Supabase for images              |
| State Mgmt   | ViewModel, Flow, Navigation      |
| IDE          | Android Studio + IntelliJ        |

---

## 🔄 App Variants

The project includes separate app variants (or modules) for:

- **Customer App** – to browse food, add to cart, and place orders  
- **Rider App** *(Planned)* – for delivery agents to accept and deliver orders  
- **Restaurant App** *(Planned)* – to manage menu and incoming orders

---

## 📂 Project Modules

```

QuickBite/
├── customer-app/
├── ktor-backend/
├── rider-app/            ← (Coming Soon)
├── restaurant-app/       ← (Coming Soon)
└── shared-resources/

```

---

## 🚧 Future Enhancements

- WebSocket-based rider tracking
- Notifications for order status
- Live restaurant inventory updates
- Restaurant dashboards

---

## 📜 Commits and Their Respective Changes

*This section tracks progressive development of the app:*

```

\--------Commits and their respective changes----------

1. Implemented splash screen but it didn't seem to work as intended because the default screen appears in the first go then the splash launches but when in AMF.xml, I set the theme to default one, it worked.
2. Implemented retrofit, fonts, dagger and hilt for api calls, textfont and dependency injection.
3. Did the setup of signup screen with animation and other composables, set up its viewmodel. Next is to do the backend setup for signup.
4. In this commit, I have enabled the sign-up functionality and explored various computer networking concepts as applied in this project. Key challenges and learnings at this stage include the need to pass the IP address in the base URL and verify the IP from the phone to establish a successful connection. This step is essential for enabling communication between the client (Android app) and the server (Ktor backend), reflecting how client-server architecture operates in real-world scenarios. 
5. Here the things were simple, just setup navigations across screens and implemented the signin feature via backend. Next we will change the fade in fade out animation while navigating to another screen from one.
6. Here, the signin is successfully tested, the google signin implementation is successful too as well as the fade animation is changed to left to right or similar transition, good thing is fade animation is changed easily. 
7. I fixed authentication edge cases like what happens on user doesn't behave as intended, sheet will appear popping signin failed msg. Setup token based sessions, Started homescreen implementation.
8. Implemented restaurant list their menu and shared animations.
9. Here I implemented cart fully functional, along with bottom navbar as well as food details screen. Setup the address to implement Google Maps onto it. Next to be done is Maps implementation and Payment integration.
10. Started with map integration, to be done.
11. Started with payment integration, to be done
12. Map Integration working as intended and so does Stripe payment Integration.
13. Here I implemented working of managing orders, notification screen and push notifications, managing orders means creating order screen and categorizing them into upcoming and history based on a string. Then implemented push notification to see the status of a particular order via notification and notification screen via navigation menu as well.
14. Implemented flavors and separated concerns, i.e. customer, restaurant and rider. Created restaurant app as of now, still a lot to be done.
15. Finished customer, rider and restaurant apps followed by a successful run. This project is done finally and working.

-----------------------------------

## 👨‍💻 About the Project

QuickBite was built as part of a full-stack Android learning journey. It includes complete backend integration, real-world APIs, secure authentication, and polished UI—all tailored to simulate a real production-level food delivery experience.

```

---



---------Commits and their respective changes----------
