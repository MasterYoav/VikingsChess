# VikingsChess

VikingsChess is a Java-based implementation of the ancient Norse strategy game **Hnefatafl**.  
This project was developed as part of an Object-Oriented Programming (OOP) assignment at Ariel University, focusing on practicing OOP principles.

## 📌 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [Download](#-download)
- [Game Rules](#-game-rules)
- [Architecture](#-architecture)
- [Installation](#-installation)
- [Usage](#-usage)
- [License](#-license)

---

## 🧠 Overview

**Hnefatafl**, also known as Viking Chess, is a historical asymmetric strategy board game.  
The defending team tries to help their king escape to the corner of the board, while the attacking team aims to capture him.

This project recreates the game with a full GUI, object-oriented architecture, and modular design for scalability and learning.

---

## 🚀 Features
- Interactive graphical user interface using Java Swing
- Complete rule enforcement (movement, capture, king escape)
- Turn-based gameplay for two players
- Object-oriented modular design
- Easily extendable and customizable

---

## ⬇️ Download

[![Latest Release](https://img.shields.io/github/v/release/MasterYoav/VikingsChess?label=Latest%20Release&style=for-the-badge)](https://github.com/MasterYoav/VikingsChess/releases/latest)

**Ready-to-Run Downloads:**

| Platform | Download | Requirements |
|----------|----------|-------------|
| 🍎 **macOS** | [VikingsChess.app.zip](https://github.com/MasterYoav/VikingsChess/releases/latest/download/VikingsChess.app.zip) | **None!** Includes Java runtime |
| 🪟 **Windows** | [VikingsChess-windows.zip](https://github.com/MasterYoav/VikingsChess/releases/latest/download/VikingsChess-windows.zip) | Requires Java installed |
| 🐧 **Linux** | [VikingsChess-linux.tar.gz](https://github.com/MasterYoav/VikingsChess/releases/latest/download/VikingsChess-linux.tar.gz) | Requires Java installed |

**How to Run:**
- **🍎 macOS**: Download, unzip, double-click `VikingsChess.app`
- **🪟 Windows**: Download, unzip, double-click `VikingsChess.exe`
- **🐧 Linux**: Download, extract, run `./VikingsChess`

---

## 🎮 Game Rules

- **Objective**:
  - **Defenders**: Get the king to one of the corner squares
  - **Attackers**: Capture the king by surrounding him on all four sides

- **Movement**:
  - All pieces move orthogonally like a rook in chess (any number of squares)
  - Only the king may move onto the corner squares

- **Capture**:
  - A piece is captured if it is sandwiched between two enemy pieces
  - The king must be surrounded on all 4 sides to be captured

---

## 🏗️ Architecture

Main classes:
- `Main.java` – Entry point
- `GUI_for_chess_like_games.java` – Handles the GUI
- `GameLogic.java` – Implements core game logic
- `PlayableLogic.java` – Interface for logic abstraction
- `Player.java` / `ConcretePlayer.java` – Represents players
- `Piece.java` / `ConcretePiece.java` – Abstract and concrete game pieces
- `King.java`, `Pawn.java` – Piece-specific logic
- `Position.java` – Board coordinates

---

## 💻 Installation

### Option 1: Download Pre-built Executable (Recommended)
See the [Download](#download) section above for ready-to-run executables.

### Option 2: Build from Source
1. Clone the repository:
   ```bash
   git clone https://github.com/MasterYoav/VikingsChess.git
   cd VikingsChess
   ```

2. Compile:
   ```bash
   javac *.java
   ```

3. Run:
   ```bash
   java Main
   ```

---

## 📦 Usage

- Launch the program with `java Main`
- GUI will open with initial board state
- Click on pieces to select and move them
- Game ends when the king escapes or is captured

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 👨‍🎓 Author

Developed by **Yoav Peretz & Gil Elbaz**  
