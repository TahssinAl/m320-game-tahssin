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

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Player player;
    private NPC npc;

    private Vector2 spawnPoint;
    private Vector2 goalPoint;

    private int currentLevel = 1 + 1;

    @Override
    public void show() {
        loadLevel(currentLevel);
    }

    private void loadLevel(int level) {
        if (map != null) map.dispose();

        map = new com.badlogic.gdx.maps.tiled.TmxMapLoader().load("maps/level-" + level + ".tmx");
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
        npc = new NPC(new Sprite(npcTexture), 10, 20);
        npc.setSize(1, 1);
        npc.setPosition(12, 10);

        goalPoint = findGoalPoint();
    }

    private Vector2 findSpawnPoint() {
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new PauseScreen(this));
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
        camera.setToOrtho(false, width / 32f, height / 32f);
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        batch.dispose();
        player.getTexture().dispose();
        npc.getTexture().dispose();
    }
}
