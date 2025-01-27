package io.github.tbzrunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.tbzrunner.enteties.NPC;
import io.github.tbzrunner.enteties.Player;
import io.github.tbzrunner.exceptions.InvalidMapException;
import io.github.tbzrunner.utils.Logger;

public class GameScreen extends ScreenAdapter {

    public OrthographicCamera camera;
    private SpriteBatch batch;
    public TiledMap map;
    public OrthogonalTiledMapRenderer mapRenderer;
    public Player player;
    public NPC npc;

    private float mapWidth;
    private float mapHeight;

    private Vector2 spawnPoint;
    public Vector2 goalPoint;

    public int currentLevel = 2;

    @Override
    public void show() {
        Logger.log("GameScreen", "Starting at level " + currentLevel);
        loadLevel(currentLevel);
    }

    public void loadLevel(int level) {
        if (map != null) map.dispose();

        try {
            map = new com.badlogic.gdx.maps.tiled.TmxMapLoader().load("maps/level-" + level + ".tmx");
            validateMap(map);

            mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 25, 15);
            batch = new SpriteBatch();

            Texture playerTexture = new Texture("images/player.png");
            TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);

            player = new Player(new Sprite(playerTexture), collisionLayer);
            player.setSize(1, 1);

            spawnPoint = findSpawnPoint();
            if (spawnPoint != null) {
                player.setPosition(spawnPoint.x, spawnPoint.y);
            }

            Texture npcTexture = new Texture("images/npc.png");
            npc = new NPC(new Sprite(npcTexture), 22, 36);
            npc.setSize(1, 1);
            npc.setPosition(22, 36);

            goalPoint = findGoalPoint();
            if (goalPoint != null) {
                Logger.log("GameScreen", "Goal point found at " + goalPoint);
            }
        } catch (InvalidMapException e) {
            Logger.error("GameScreen", "Invalid map: " + e.getMessage());
            Gdx.app.exit();
        }
    }

    private void validateMap(TiledMap map) throws InvalidMapException {
        if (map == null) {
            throw new InvalidMapException("The map is null and cannot be loaded.");
        }

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        if (layer == null) {
            throw new InvalidMapException("The map does not contain a valid collision layer.");
        }

        if (!map.getProperties().containsKey("width") || !map.getProperties().containsKey("height")) {
            throw new InvalidMapException("The map is missing required properties: width or height.");
        }
    }

    public Vector2 findSpawnPoint() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null && cell.getTile() != null) {
                    if (cell.getTile().getProperties().containsKey("spawnpoint")) {
                        return new Vector2(x, y);
                    }
                }
            }
        }
        Logger.error("GameScreen", "Spawn point not found.");
        return null;
    }

    private Vector2 findGoalPoint() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null && cell.getTile() != null) {
                    if (cell.getTile().getProperties().containsKey("goalPoint")) {
                        return new Vector2(x, y);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void render(float delta) {
        handleInput();

        player.update(delta);
        npc.update(delta);

        if (goalPoint != null && player.getBoundingRectangle().overlaps(new Rectangle(goalPoint.x, goalPoint.y, 1, 1))) {
            Logger.log("GameScreen", "Player reached goal at " + goalPoint);
            currentLevel++;
            if (Gdx.files.internal("maps/level-" + currentLevel + ".tmx").exists()) {
                Logger.log("GameScreen", "Loading next level: " + currentLevel);
                loadLevel(currentLevel);
            } else {
                Logger.log("GameScreen", "No more levels. Returning to MainScreen.");
                ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new MainScreen());
            }
        }

        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();

        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        npc.draw(batch);
        batch.end();
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        } else {
            player.stopHorizontal();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.jump();
            Logger.log("GameScreen", "Player jumped.");
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 32f, height / 32f);
    }

    @Override
    public void dispose() {
        Logger.log("GameScreen", "Disposing resources.");
        map.dispose();
        mapRenderer.dispose();
        batch.dispose();
        player.getTexture().dispose();
        npc.getTexture().dispose();
    }
}
