# Khomasi 🏟️

**Khomasi** is a modern Android application designed to streamline the process of booking sports fields and venues. Whether you're looking for a football pitch, a tennis court, or a basketball arena, Khomasi connects athletes with the perfect play space.

## 🚀 Features

- **Discover Venues:** Browse and search for various sports fields near you.
- **Easy Booking:** Seamlessly book your preferred time slots with a few taps.
- **User Authentication:** Secure login, registration, and OTP verification.
- **Manage Bookings:** Keep track of your upcoming and past bookings in one place.
- **Favorites:** Save your most-loved venues for quick access.
- **Notifications:** Stay updated with booking confirmations and reminders.
- **AI Integration:** Enhanced user experience with AI-powered features.
- **Face Detection:** Integrated ML Kit for identity verification or profile customization.
- **Interactive UI:** Built entirely with Jetpack Compose for a smooth and modern feel.

## 🛠️ Tech Stack

- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3.
- **Architecture:** MVVM with Clean Architecture principles (Data, Domain, and Presentation layers).
- **Dependency Injection:** [Hilt](https://dagger.dev/hilt/).
- **Networking:** [Retrofit](https://square.github.io/retrofit/) & OkHttp for REST API communication.
- **Local Database:** [Room](https://developer.android.com/training/data-storage/room) for caching and offline support.
- **Asynchronous Programming:** Kotlin Coroutines and Flow.
- **Navigation:** Jetpack Compose Navigation.
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/).
- **AI/ML:** Google ML Kit (Face Detection).
- **Media:** Media3 ExoPlayer for video content.
- **Animations:** Lottie for rich interactive animations.
- **Local Storage:** DataStore for preferences.

## 🏗️ Architecture

The project follows **Clean Architecture** to ensure scalability, maintainability, and testability:

- **Presentation Layer:** Contains UI components (Compose) and ViewModels.
- **Domain Layer:** Contains Business Logic, Use Cases, and Repository Interfaces.
- **Data Layer:** Contains Repository Implementations, Data Sources (Remote & Local), and Mappers.

## 📸 Screenshots

<img width="220" height="450" alt="onboarding1" src="https://github.com/user-attachments/assets/db256975-815b-45e4-8262-b3518c36509b" />
<img width="220" height="450" alt="onboarding2" src="https://github.com/user-attachments/assets/3525ae58-e431-43a8-b10c-b1ce8d66c206" />
<img width="220" height="450" alt="login_or_register" src="https://github.com/user-attachments/assets/760d287f-b861-4955-9035-336c947fb724" />
<img width="220" height="450" alt="register1" src="https://github.com/user-attachments/assets/5a825005-a0ac-4918-bacf-5f991f159c5e" />
<img width="220" height="450" alt="register2" src="https://github.com/user-attachments/assets/62dee5d9-df74-4c80-a281-b8fed03fd874" />
<img width="220" height="450" alt="home" src="https://github.com/user-attachments/assets/71bd7ad5-b67d-46ef-8622-81ccb2784d59" />
<img width="220" height="450" alt="playround_reviews" src="https://github.com/user-attachments/assets/2f9cce6a-5ca5-45d5-af10-1729ac022616" />
<img width="220" height="450" alt="booking_calender" src="https://github.com/user-attachments/assets/873f3508-a2e5-4ff7-9918-e9d43ba57e19" />
<img width="220" height="450" alt="confirmation_booking" src="https://github.com/user-attachments/assets/0eb9dd3c-9fea-4bf5-ab5c-f5b24ddb9b51" />
<img width="220" height="450" alt="payment_visa" src="https://github.com/user-attachments/assets/194d54c1-76a0-48a0-9176-e0fcc0028803" />
<img width="220" height="450" alt="payment_fawry" src="https://github.com/user-attachments/assets/d130d715-c623-4b30-8ebc-27f5afd510fa" />
<img width="220" height="450" alt="payment_coins" src="https://github.com/user-attachments/assets/4596d6f7-a63e-443a-8f2b-9c07e08f2025" />
<img width="220" height="450" alt="playgrounds" src="https://github.com/user-attachments/assets/586924eb-ab6d-4523-a362-013097ed5a6c" />
<img width="220" height="450" alt="playgrounds_filter" src="https://github.com/user-attachments/assets/1079958c-1638-4b72-aa30-390454f50b3e" />
<img width="220" height="450" alt="playgrounds_result" src="https://github.com/user-attachments/assets/e75d2505-61c5-4f86-9607-e0991e15b2e4" />
<img width="220" height="450" alt="upload_ai_video" src="https://github.com/user-attachments/assets/4b816909-44a3-488d-8815-ede749afe337" />
<img width="220" height="450" alt="choose_sheet_ai_video" src="https://github.com/user-attachments/assets/53f660d0-226e-4a16-aa4d-8134d566068e" />
<img width="220" height="450" alt="camera_permission" src="https://github.com/user-attachments/assets/e93d5bc9-7278-4bf3-b3ee-fa3c17d71223" />
<img width="220" height="450" alt="profile" src="https://github.com/user-attachments/assets/018ced19-0168-4518-bbc9-93d907ca3b5a" />
<img width="220" height="450" alt="feedback" src="https://github.com/user-attachments/assets/033ee149-7b54-423d-81f6-77e901797125" />
<img width="220" height="450" alt="logout" src="https://github.com/user-attachments/assets/161ada9a-3e08-43c5-bca3-04f74a48c6f6" />


## 🚦 Getting Started

### Prerequisites
- Android Studio Iguana or newer.
- JDK 17.
- Android SDK 24+.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Android-Geeks/khomasi.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## 🧪 Testing

The project includes both unit and instrumentation tests:
- **Unit Tests:** Located in `src/test`.
- **Instrumentation Tests:** Located in `src/androidTest`.
- Uses JUnit, Google Truth, and MockWebServer.
