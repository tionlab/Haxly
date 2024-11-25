# Haxly 🔐

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/java-23-orange)](https://openjdk.java.net/)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/tionlab/Haxly)

> This program supports both Korean and English.

<div align="center">
    <img src="src\main\resources\imgs\Haxly_main.png" alt="Haxly Logo" width="250"/>
    <br/>
    <p>Software that hashes files through various hash algorithms in a simple way</p>
</div>

<div align="center">
    <h3>🌏 Language</h3>
    <a href="README.md">한국어</a> |
    <a href="README_en.md">English</a>
</div>

## ✨ Key Features

-   🎯 Various Hash Algorithms
    ```
    - SHA-256
    - SHA-1
    - MD5
    - CRC32
    - BLAKE2B-512
    - BLAKE2S-256
    - WHIRLPOOL
    - RIPEMD160
    - TIGER
    - SHA-512
    ```
-   🖱️ Intuitive Drag and Drop Interface
-   🔄 Real-time Hash Comparison

## 🚀 Getting Started

### 🚩 Quick Start

> ⚠️ Windows Only

1. Download `HaxlySetup.exe` from [GitHub Releases](https://github.com/tionlab/haxly/releases)
2. Run the downloaded installer
3. After installation, run by either:
    - Launching Haxly directly
    - Right-clicking a file in Windows Explorer and selecting "Calculate the hash"

### 🛠️ Developer's Guide

#### Prerequisites

-   Java 23
-   Maven (for building)

#### Installation

```bash
git clone https://github.com/tionlab/haxly.git
cd haxly
mvn clean install
```

#### Normal Execution

```bash
java -jar haxly.jar
```

#### File-specific Execution

```bash
java -jar haxly.jar [file_path]
```

## 📸 Demo

<div align="center">
    <img src="docs\1.gif" alt="Right-click execution" width="400"/>
    <img src="docs\2.gif" alt="Drag and drop" width="400"/>
    <img src="docs\3.gif" alt="Direct selection" width="400"/>
</div>

## 🤝 Contributing

1. Fork the repository
2. Commit your changes (`git commit -m 'feat: Add some feature'`)
3. Push to the main branch (`git push origin main`)
4. Open a Pull Request

If direct contribution is difficult, please share your thoughts through an [Issue](https://github.com/tionlab/haxly/issues).

## 📄 License

This project is licensed under the MIT License.
<br/>See the [LICENSE](LICENSE) file for details.

---

<div align="center">
    <br/>
    © 2024 Haxly
</div>
