# 🌍 JavaFX language detector
## 📑 Table of Contents
- [About](#-about)
- [Features](#-features)
- [Installation](#-installation)


## 🚀 About
I'm sharing a project I created while learning JavaFX — a simple application that detects the language of a given text. The application works in conjunction with the [DetectLanguage API](https://detectlanguage.com/documentation).  

Used technologies:  
☕ [JavaFX](https://openjfx.io/)  
🗣️ [detectlanguage.com](https://detectlanguage.com/documentation)
## ⚙ Features
<p align="center">
      <img src="https://github.com/user-attachments/assets/ab17c34b-1be0-4ef0-8e48-16649219f73c" />
    </p>

## 🛠 Installation
- **Register on [detectlanguage.com](https://detectlanguage.com/)**
- **Get your API key**
- **Install [JavaFX](https://openjfx.io/openjfx-docs/)**
- **Downolad project file `Main.java`**
- **Enter your API key in code**
  
  ```sh
                String query = URLEncoder.encode(userInput, "UTF-8");
                String apiKey = "your API key"; <---- Here
                URL url = new URL("https://ws.detectlanguage.com/0.2/detect?q=" + query + "&key=" + apiKey);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  ```
- **Run the application**
  
