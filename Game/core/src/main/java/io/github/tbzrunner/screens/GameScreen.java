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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import io.github.tbzrunner.enteties.Player;
import io.github.tbzrunner.enteties.NPC;

public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Player player;
    private NPC npc;

    private float mapWidth;
    private float mapHeight;

    private Vector2 spawnPoint;

    @Override
    public void show() {
        map = new com.badlogic.gdx.maps.tiled.TmxMapLoader().load("maps/level-2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);

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
        } else {
            player.setPosition(4, 7);
        }

        Texture npcTexture = new Texture("images/npc.png");
        npc = new NPC(new Sprite(npcTexture), 23, 27);
        npc.setSize(1, 1);
        npc.setPosition(22, 36);


        /**
         * Add later on new character
         */
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

    @Override
    public void render(float delta) {
        handleInput();

        // Update player and NPC
        player.update(delta);
        npc.update(delta);

        // Check collision between player and NPC
        if (checkCollision(player.getBoundingRectangle(), npc.getBoundingRectangle())) {
            // Reset player to spawn point upon collision
            player.setPosition(spawnPoint.x, spawnPoint.y);
        }

        // Update camera to follow player
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

    private boolean checkCollision(Rectangle rect1, Rectangle rect2) {
        return rect1.overlaps(rect2);
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
