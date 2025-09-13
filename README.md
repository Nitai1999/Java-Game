# ass5 – Java Arcade Game (IntelliJ + Ant)

A small 2D arcade game written in Java. The project is set up for **IntelliJ IDEA** with an **Ant** build file and a single external jar: **`biuoop-1.4.jar`** (graphics & input).

> Java 8+ recommended.

---

## Project Structure

ass5/
├─ src/
│ ├─ collidables/ # collision interfaces & environment
│ │ ├─ Collidable.java
│ │ ├─ CollisionInfo.java
│ │ └─ GameEnvironment.java
│ ├─ core/ # game loop / main orchestration
│ │ └─ Game.java
│ ├─ counters/
│ │ └─ Counter.java
│ ├─ geometry/ # Point/Line/Rectangle math
│ │ ├─ Line.java
│ │ ├─ MathUtils.java
│ │ ├─ Point.java
│ │ └─ Rectangle.java
│ ├─ listeners/ # hit events & scoring
│ │ ├─ BallRemover.java
│ │ ├─ BlockRemover.java
│ │ ├─ HitListener.java
│ │ ├─ HitNotifier.java
│ │ └─ ScoreTrackingListener.java
│ ├─ sprites/ # game objects & drawables
│ │ ├─ Ball.java
│ │ ├─ Block.java
│ │ ├─ Paddle.java
│ │ ├─ ScoreIndicator.java
│ │ ├─ Sprite.java
│ │ ├─ SpriteCollection.java
│ │ └─ Velocity.java
│ └─ Ass5Game.java # entry point (main)
│
├─ biuoop-1.4.jar # external dependency
├─ build.xml # Ant build script
├─ .gitignore
└─ (out/ or bin/ created by build)


---

## Build & Run (with Ant)

From the project root:

```bash
ant clean
ant compile
ant run          # build.xml defines the main class (Ass5Game) for you

Build & Run (manual javac)

Compile sources to out/ and run the game:
Windows (PowerShell / CMD)
mkdir out
javac -cp biuoop-1.4.jar -d out -sourcepath src src/Ass5Game.java
java  -cp "out;biuoop-1.4.jar" Ass5Game

macOS / Linux
mkdir -p out
javac -cp biuoop-1.4.jar -d out -sourcepath src src/Ass5Game.java
java  -cp "out:biuoop-1.4.jar" Ass5Game

Controls (typical)

Arrow keys to move the paddle (left/right).

Esc / close window to quit.

Exact bindings come from biuoop key listeners used in your code.

Clean
ant clean
