# Turn-Based RPG Game (Java Swing)

A desktop turn-based Role-Playing Game (RPG) developed in Java utilizing Object-Oriented Programming (OOP) principles and Java Swing for an interactive, graphical user interface.

## Contributors
- Amna Mirza
- Muhammad Afrasyab Asif
- Raffay Irfan

---

## Game Overview
Choose your hero class (Warrior, Mage, etc.) and embark on an adventure fighting various monsters (Bats, Goblins, Dragons) in turn-based combat. Manage your health, utilize special skills, and defeat the enemies to survive!

### Game Screenshots
Here are previews of the game interfaces:
- **Main Menu**: [rpg user interface one.png](file:///c:/Users/Lenovo/Desktop/git%20repo/RPG-Game/screenshots/rpg%20user%20interface%20one.png)
- **Character Select**: [rpg two .png](file:///c:/Users/Lenovo/Desktop/git%20repo/RPG-Game/screenshots/rpg%20two%20.png)
- **Battle Screen**: [rpg three.png](file:///c:/Users/Lenovo/Desktop/git%20repo/RPG-Game/screenshots/rpg%20three.png)
- **Settings Screen**: [rpg four .png](file:///c:/Users/Lenovo/Desktop/git%20repo/RPG-Game/screenshots/rpg%20four%20.png)
- **Victory/Defeat Screen**: [rpg five.png](file:///c:/Users/Lenovo/Desktop/git%20repo/RPG-Game/screenshots/rpg%20five.png)

---

## Project Structure
The project is organized in packages under the `rpg` namespace:
```
RPG-Game/
├── src/
│   └── rpg/
│       ├── core/         # Game logic (Warrior, Mage, Enemy, Game, etc.)
│       ├── gui/          # Swing GUI classes (MainMenu, GameScreen, SettingsMenu, etc.)
│       ├── interfaces/   # OOP Interfaces (Defendable.java)
│       ├── utils/        # Utility classes (DamageCalculator, Soundmanager, imageload, projectile)
│       └── assets/       # Embedded image and audio assets
├── screenshots/          # Gameplay screenshots
├── README.md             # Project documentation
└── .gitignore            # Git ignore rules
```

---

## How to Compile & Run

### Prerequisites
- Java Development Kit (JDK 8 or higher) installed.

### Using Command Line
Navigate to the root of the project and execute:

1. **Compile all files**:
   ```bash
   javac -d bin src/rpg/core/*.java src/rpg/interfaces/*.java src/rpg/utils/*.java src/rpg/gui/*.java
   ```

2. **Copy assets to the bin folder**:
   Java looks for assets on the classpath relative to the package structure. Copy the assets directory:
   - On Windows (Command Prompt/PowerShell):
     ```powershell
     xcopy /E /I src\rpg\assets bin\rpg\assets
     ```
   - On Linux/macOS:
     ```bash
     mkdir -p bin/rpg/assets && cp -r src/rpg/assets/* bin/rpg/assets/
     ```

3. **Run the Game**:
   ```bash
   java -cp bin rpg.gui.MainGUI
   ```
