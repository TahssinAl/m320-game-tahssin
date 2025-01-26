package io.github.tbzrunner.enteties;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends BaseSprite {
    private Vector2 velocity;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.velocity = new Vector2(0, 0);
        setSize(32, 32);
    }


    @Override
    public void update(float delta) {
        /**
         *  Basic update logic (no movement or collision detection yet)
         *  Future movement and collision implementation
         */

    }
}
