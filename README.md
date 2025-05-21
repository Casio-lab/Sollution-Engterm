#  Jumping Jack

This is a 2D side-scrolling platformer game developed in Java using the LibGDX game framework.  
It was built as a software engineering project to demonstrate the practical application of key **object-oriented design patterns**, modular code structure, and clean architecture.

The game features two playable levels, enemies with different AI behaviors, jumping physics, coin collection, health system, win/lose conditions, and a pause feature.


---

##  Key Features

-  Platforming with smooth movement and jumping
-  Collectible coins per level
-  Lives and enemy damage system
-  Basic AI movement using strategy patterns
-  Multiple levels (Level 1 & 2)
-  Win and Game Over screens
-  Pause / Resume functionality

---

##  Software Design Patterns Implemented

| Pattern           | Description                                                                 | Where Used                         |
|------------------|-----------------------------------------------------------------------------|------------------------------------|
| **Singleton**     | Provides a single access point to global game objects                       | `MainGame` for `SpriteBatch`       |
| **Factory Method**| Simplifies creation of different game entities                              | `EntityFactory` for coins, enemies |
| **Strategy**      | Enables flexible enemy movement behavior                                    | `FullPatrolStrategy`, `LimitedPatrolStrategy` |
| **Observer**      | Encapsulates and centralizes input handling                                 | `InputHandler`                     |
| **State**         | Manages transitions between different game states (playing, paused, win, etc.) | `GameStateManager` and state classes |

---

##  Controls

| Key             | Function                     |
|------------------|------------------------------|
| `A / D` or `← / →` | Move player left / right      |
| `SPACE`         | Jump                          |
| `P`             | Toggle pause / resume         |
| `R`             | Restart game (after win/loss) |



---

##  Assets Required

Place these files in your `core/assets/` directory:

- `background.png` — Background image for levels  
- `player.png` — Player sprite  
- `enemy.png` — Enemy sprite  
- `coin.png` — Collectible coin  
- `heart.png` — Life indicator  
- `platform.png` — Platform blocks  
- `font.otf` or `font.ttf` — Custom font for UI text

You can use placeholders or create your own pixel assets.
