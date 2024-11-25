# Haxly 🔐

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/java-23-orange)](https://openjdk.java.net/)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/tionlab/Haxly)

> This program supports both Korean and English.

<div align="center">
    <img src="src\main\resources\imgs\Haxly_main.png" alt="Haxly Logo" width="250"/>
    <br/>
    <p>간단한 방법으로 다양한 해시 알고리즘을 통해 파일을 해시화하는 소프트웨어</p>
</div>

<div align="center">
    <h3>🌏 Language</h3>
    <a href="README.md">한국어</a> |
    <a href="README_en.md">English</a>
</div>

## ✨ 주요 기능

-   🎯 다양한 해시 알고리즘
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
-   🖱️ 직관적인 드래그 앤 드롭 인터페이스
-   🔄 실시간 해시 비교

## 🚀 시작하기

### 🚩 쉽게 시작하기

> ⚠️ Windows 전용

1. [GitHub Releases](https://github.com/tionlab/haxly/releases)에서 최신 버전의 `HaxlySetup.exe` 다운로드
2. 다운로드한 설치 파일 실행
3. 설치 완료 후 다음 방법으로 실행:
    - Haxly를 직접 실행
    - Windows 탐색기에서 파일 우클릭 후 "Calculate the hash" 선택

### 🛠️ 개발자 가이드

#### 사전 요구사항

-   Java 23
-   Maven (빌드용)

#### 설치

```bash
git clone https://github.com/tionlab/haxly.git
cd haxly
mvn clean install
```

#### 일반 실행

```bash
java -jar haxly.jar
```

#### 파일 지정 실행

```bash
java -jar haxly.jar [파일경로]
```

## 📸 시연

<div align="center">
    <img src="docs\1.gif" alt="우클릭 실행" width="400"/>
    <img src="docs\2.gif" alt="드래그 앤 드롭" width="400"/>
    <img src="docs\3.gif" alt="직접 선택" width="400"/>
</div>

## 🤝 기여하기

1. 저장소를 포크하세요
2. 변경사항을 커밋하세요 (`git commit -m 'feat: Add some feature'`)
3. main 브랜치에 푸시하세요 (`git push origin main`)
4. Pull Request를 열어주세요

직접 기여가 어려운 경우, [Issue](https://github.com/tionlab/haxly/issues)를 통해 의견을 제시해 주세요.

## 📄 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다.
<br/>자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

---

<div align="center">
    <br/>
    © 2024 Haxly
</div>
