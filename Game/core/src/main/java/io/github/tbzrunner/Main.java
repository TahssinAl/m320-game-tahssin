package io.github.tbzrunner;

import com.badlogic.gdx.Game;

import io.github.tbzrunner.screens.MainScreen;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new MainScreen());
    }
}
