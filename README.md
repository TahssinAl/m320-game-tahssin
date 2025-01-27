# Project Plan and Architecture

## Planned Architecture
At the start of the project, I created a class diagram to show how the different parts of the game would work together. I used a tool like Draw.io for this. The diagram helped me stay organized and clear about the structure of the project.

## Game Overview
The game is a 2D platformer called **TBZRunner**, where the player controls a character to navigate through different levels. Each level has a goal point, and the player has to avoid obstacles and interact with NPCs to reach the goal. The game includes features like jumping, moving left or right, and interacting with the environment.

Key elements in the game:
- **Player**: Controlled by the user, with basic movement and jumping abilities.
- **NPCs**: Characters that add interaction and story to the game.
- **Levels**: Maps loaded dynamically as the player progresses.
- **Main Menu**: A welcoming screen where the player can start the game.
- **Logger**: Logs events and helps with debugging.
- **Pause Screen**: Allows the player to pause the game.

## Design Patterns Used
I used the following design patterns to structure the project:

1. **Factory Pattern**:
   - Used to load levels dynamically and validate the maps.
   - This pattern made it easier to manage and extend the game by adding new levels.

2. **Singleton Pattern**:
   - Used in the `Logger` class to ensure there's only one logger instance throughout the game.
   - This helped maintain consistent logging and debugging.

3. **MVC (Model-View-Controller)**:
   - While not strictly implemented, the game loosely follows the MVC structure.
     - The **Model** is represented by classes like `Player`, `NPC`, and `Enemy`.
     - The **View** is handled by classes like `GameScreen` and `MainScreen` for rendering.
     - The **Controller** involves input handling and logic in the `GameScreen` and other screens.

## What We Did and When

### Week 1: Starting the Project
- Set up the project and tools needed for development.
- Wrote a simple `README.md` file to explain the project.
- Created a class diagram to plan how the main parts (like `Player`, `GameScreen`, and `MainScreen`) would connect.

### Week 2: Building the Game
- Built the main features of the game, like the `Player` and `GameScreen`.
- Added other parts, like `NPC`, `Enemy`, and `Logger`, to make the game work.
- Tested the connections between the classes to make sure everything worked as planned.

### Week 3: Finishing Up
- Tested everything to make sure the game worked well.
- Fixed and improved the code based on testing.
- Updated the class diagram and the `README.md` file to match the final version of the project.

## Summary
The project was completed on time, and the design patterns helped make the game easier to develop and maintain. The final version of **TBZRunner** is fun, functional, and ready for players!
