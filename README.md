# 🎬 Movie App

A modern **Android Movie Browsing Application** built with **Jetpack Compose** and powered by the **TMDB API**. The app follows **Clean Architecture** principles, uses **MVVM**, and leverages **Coroutines + Flow** for reactive data handling. It also features **YouTube trailer playback** and supports both **Dark & Light themes**.

---

## ✨ Highlights

* 🚀 Built entirely with **Jetpack Compose**
* 🧱 Scalable **Clean Architecture** (Domain, Data, Presentation)
* ⚡ Reactive UI using **Coroutines & Flow**
* 🧩 Dependency Injection with **Hilt**
* 🎥 In-app **YouTube trailer viewer**
* 🌗 Supports **Dark & Light themes**

---

## 🚀 Features

### 🎬 Movies

* Browse **Trending**, **Popular**, and **Top-Rated** movies
* View detailed movie information:

    * Overview
    * Ratings
    * Genres
    * Release date
* 🔍 **Real-time movie search**
* ▶️ Watch official trailers using the **YouTube Player**

---

## 🎨 UI / UX

* Fully designed using **Jetpack Compose**
* **Material 3** design components
* Adaptive layouts for different screen sizes
* Smooth animations and modern UI patterns
* Automatic **Light / Dark mode** switching

---

## 🧱 Architecture

The app is built using **Clean Architecture** to ensure maintainability, scalability, and testability.

```
com.example.movieapp
│
├── data          # API, DTOs, repository implementations
├── domain        # Business models & use cases
├── presentation  # ViewModels & Compose UI
```

### 📐 Design Patterns

* **MVVM (Model–View–ViewModel)**
* **Repository Pattern**
* **Single Source of Truth**
* **Unidirectional Data Flow (UDF)**

---

## ⚙️ Tech Stack

* **Kotlin**
* **Jetpack Compose**
* **Coroutines & Flow**
* **Hilt** – Dependency Injection
* **Retrofit** – Networking
* **TMDB API** – Movie data
* **YouTube Android Player API** – Trailer playback
* **Material 3 Components**

---

## 🛠️ Project Setup

### 1️⃣ Clone the repository

```bash
git clone https://github.com/Prabhat-kr-Tiwari/Movie-App
```

### 2️⃣ Get TMDB API Key

* Create an account at: [https://www.themoviedb.org/](https://www.themoviedb.org/)
* Generate your API key from the dashboard

### 3️⃣ Add API key

Add the following line to your `local.properties` file:

```properties
TMDB_API_KEY=your_api_key_here
```

### 4️⃣ Build & Run

* Open the project in **Android Studio Flamingo or newer**
* Sync Gradle files
* Run the app on an emulator or physical device

---

## 📸 Screenshots

![](C:\Users\prabhat\Downloads\movie%20screenshot.png)
---

## 🔮 Future Improvements

* Offline caching with Room
* Pagination support
* Favorites & Watchlist
* User authentication
* Improved tablet support

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repository and submit a pull request.

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author

**Prabhat Kumar Tiwari**
GitHub: [@Prabhat-kr-Tiwari](https://github.com/Prabhat-kr-Tiwari)

---

⭐ If you like this project, don’t forget to star the repository!
