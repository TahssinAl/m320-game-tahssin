package io.github.tbzrunner.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.tbzrunner.enteties.Player;

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
        map = new TmxMapLoader().load("maps/level-1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        /**
         * Calculation of the map width and height
         */
        mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();


        Texture playerTexture = new Texture("images/player.png");
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
        player = new Player(new Sprite(playerTexture), collisionLayer);
        player.setPosition(64, 64);
    }

    @Override
    public void render(float delta) {
        /**
         * Camera follows the player and is limited
         */
        camera.position.set(
            Math.max(camera.viewportWidth / 2, Math.min(player.getX() + player.getWidth() / 2, mapWidth - camera.viewportWidth / 2)),
            Math.max(camera.viewportHeight / 2, Math.min(player.getY() + player.getHeight() / 2, mapHeight - camera.viewportHeight / 2)),
            0
        );
        camera.update();


        mapRenderer.setView(camera);
        mapRenderer.render();

        /**
         * Load the player
         */
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        /**
         * Release resources
         */
        map.dispose();
        mapRenderer.dispose();
        batch.dispose();
        player.getTexture().dispose();
    }
}
