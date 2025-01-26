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

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        map = new TmxMapLoader().load("maps/level-1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        Texture playerTexture = new Texture("images/player.png");
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
        player = new Player(new Sprite(playerTexture), collisionLayer);
        player.setPosition(64, 64);
    }

    @Override
    public void render(float delta) {
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
