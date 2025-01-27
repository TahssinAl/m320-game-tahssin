package io.github.tbzrunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private final ScreenAdapter previousScreen;

    public PauseScreen(ScreenAdapter previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2.0f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Pause Menu", Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() / 2f + 40);
        font.draw(batch, "Press R to Respawn", Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() / 2f);
        font.draw(batch, "Press M for Main Menu", Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() / 2f - 20);
        font.draw(batch, "Press Q to Quit", Gdx.graphics.getWidth() / 2f - 60, Gdx.graphics.getHeight() / 2f - 40);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(previousScreen);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new MainScreen());
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
