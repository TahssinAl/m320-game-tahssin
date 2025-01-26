package io.github.tbzrunner;

import com.badlogic.gdx.Game;
import io.github.tbzrunner.screens.GameScreen;

public class Main extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
