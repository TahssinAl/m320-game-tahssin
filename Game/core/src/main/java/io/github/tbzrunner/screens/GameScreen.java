package io.github.tbzrunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.tbzrunner.enteties.Player;

import java.util.Iterator;

public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Player player;

    private float mapWidth;
    private float mapHeight;

    @Override
    public void show() {
        map = new com.badlogic.gdx.maps.tiled.TmxMapLoader().load("maps/level-2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f); // Skalierung für Tile-Einheiten

        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 25, 15); // Kamera zeigt 25x15 Tiles
        batch = new SpriteBatch();

        Texture playerTexture = new Texture("images/player.png");
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(new Sprite(playerTexture), collisionLayer);
        player.setPosition(64, 64);
    }

    @Override
    public void render(float delta) {
        handleInput();

        player.update(delta);

        /**
         * Cam will follow the player
         */
        camera.position.set(
            player.getX() + player.getWidth() / 2,
            player.getY() + player.getHeight() / 2,
            0
        );
        camera.update();

        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        } else {
            player.stopHorizontal();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.jump();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 32f;
        camera.viewportHeight = height / 32f;
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        batch.dispose();
        player.getTexture().dispose();
    }

    private void logAllTileProperties() {
        /**
         * Take th e first layer of the TiledMap Layer
         */
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null && cell.getTile() != null) {
                    MapProperties properties = cell.getTile().getProperties();
                    if (properties != null) {
                        System.out.println("Tile at (" + x + ", " + y + "):");
                        for (Iterator<String> it = properties.getKeys(); it.hasNext(); ) {
                            String key = it.next();
                            System.out.println("  " + key + " = " + properties.get(key));
                        }
                    } else {
                        System.out.println("Tile at (" + x + ", " + y + ") has no properties.");
                    }
                } else {
                    System.out.println("No tile at (" + x + ", " + y + ").");
                }
            }
        }
    }
}
