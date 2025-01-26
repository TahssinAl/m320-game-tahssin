package io.github.tbzrunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    private float alpha; // For text fading
    private boolean fadingIn; // Direction of fade animation
    private float colorTimer; // Timer for color cycling

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getData().setScale(2);

        alpha = 0f;
        fadingIn = true;
        colorTimer = 0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // Fade effect
        if (fadingIn) {
            alpha += delta;
            if (alpha >= 1f) {
                alpha = 1f;
                fadingIn = false;
            }
        } else {
            alpha -= delta;
            if (alpha <= 0f) {
                alpha = 0f;
                fadingIn = true;
            }
        }

        // Update color cycling
        colorTimer += delta * 2;
        if (colorTimer > 1f) {
            colorTimer -= 1f;
        }

        Color textColor = new Color(1, 0, 0, alpha); // Start with red
        if (colorTimer < 0.33f) {
            textColor.set(1, colorTimer * 3, 0, alpha); // Red to orange
        } else if (colorTimer < 0.66f) {
            textColor.set(1 - (colorTimer - 0.33f) * 3, 1, 0, alpha); // Orange to green
        } else {
            textColor.set(0, 1 - (colorTimer - 0.66f) * 3, (colorTimer - 0.66f) * 3, alpha); // Green to blue
        }

        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render text with glowing effect
        batch.begin();
        font.setColor(textColor);
        font.draw(batch, "Hallo und Willkommen zum TBZRunner!",
            Gdx.graphics.getWidth() / 2f - 220,
            Gdx.graphics.getHeight() / 2f);
        font.draw(batch, "Drücke ENTER, um zu starten",
            Gdx.graphics.getWidth() / 2f - 220,
            Gdx.graphics.getHeight() / 2f - 50);
        font.draw(batch, "Presented by Tahssin Al-Khatib",
            Gdx.graphics.getWidth() / 2f - 220,
            Gdx.graphics.getHeight() / 2f - 150);
        batch.end();

        // Input handling
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
