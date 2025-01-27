# Project Plan and Architecture

## Planned Architecture
At the start of the project, I created a class diagram to plan how the different parts of the game would work together. I used a tool like Draw.io for this. This diagram helped me organize the structure of the project and keep everything clear as I worked on the game.

## Game Overview
The game is a 2D platformer called **TBZRunner**, created with **LibGDX**. The game uses **Tiled** maps for the levels, allowing me to design dynamic, interactive maps with spawn points, goals, and collision layers.

### Key Features:
- **Player**: A character controlled by the user, with movement and jumping abilities.
- **NPCs**: Non-playable characters that add interaction and depth to the game.
- **Levels**: Built with Tiled maps and loaded dynamically as the player progresses.
- **Main Menu**: A starting screen with smooth animations and options to begin the game.
- **Logger**: A custom logging tool for tracking events and debugging.
- **Pause Screen**: Allows the player to pause and resume the game at any time.

### Tools and Libraries:
- **LibGDX**: For building the entire game and managing graphics, input, and logic.
- Project generation
- ![Screenshot 2025-01-13 160047](https://github.com/user-attachments/assets/858c7968-9c1f-4fad-8bd3-b14ddcdc23ec)

- **Tiled**: Used for designing maps with layers and properties for collision, spawn points, and goals.
- ![image](https://github.com/user-attachments/assets/c858c645-1536-40c8-8191-0aca915f7d2a)

- **IntelliJ IDEA**: For writing and testing the code.
- **Draw.io**: For creating the class diagram.

### Project Structure:
The project is organized into the following main packages:
1. `io.github.tbzrunner.screens` – Contains screens like `GameScreen`, `MainScreen`, and `PauseScreen`.
2. `io.github.tbzrunner.enteties` – Contains entities like `Player`, `NPC`, and `Enemy`.
3. `io.github.tbzrunner.utils` – Contains utility classes like `Logger`.
4. `io.github.tbzrunner.exceptions` – Handles game-specific exceptions like `InvalidMapException`.

## Design Patterns Used
I used the following design patterns to keep the project structured and maintainable:

1. **Factory Pattern**:
   - Used to load and validate Tiled maps for each level.
   - This made it easier to add new levels by simply creating more `.tmx` files.

2. **Singleton Pattern**:
   - Used in the `Logger` class to ensure consistent logging throughout the game.

3. **MVC (Model-View-Controller)**:
   - The project loosely follows MVC principles:
     - **Model**: Includes classes like `Player`, `NPC`, and `Enemy` for the core data and logic.
     - **View**: Handled by `GameScreen`, `MainScreen`, and other screens for rendering the UI.
     - **Controller**: Managed input handling and level logic in the `GameScreen`.

## Solo Development
This project was developed entirely by me as a solo developer. Working alone gave me full control over the code and design, but it also meant managing all aspects of the project myself, from planning to testing.

## What I Did and When

### Week 1: Planning
- Set up the project and tools (LibGDX, Tiled, IntelliJ IDEA).
- Created a simple class diagram to organize the structure.
- Started writing the basic game loop and input handling.

### Week 2: Development
- Built core functionality, including player movement, NPCs, and map loading.
- Integrated Tiled maps and added collision detection.
- Worked on different screens like the `MainScreen` and `PauseScreen`.

### Week 3: Testing and Finalization
- Polished gameplay features and fixed bugs.
- Added the ability to transition between levels dynamically.
- Updated the class diagram and cleaned up the project structure.

### Structure
![image](https://github.com/user-attachments/assets/49f00d74-9aad-4815-8aff-a6fb4603b823)


## Summary
Using **LibGDX** and **Tiled**, I built a fully functional 2D platformer in 3 weeks. The use of design patterns helped me structure the project and made development smoother. I'm proud of completing this project on my own and delivering a fun and functional game.
