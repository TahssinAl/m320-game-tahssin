package io.github.tbzrunner.enteties;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite {

    private static final float GRAVITY = -10f;
    private static final float MOVE_SPEED = 5f;
    private static final float JUMP_SPEED = 12f;

    private final Vector2 velocity = new Vector2();
    private final TiledMapTileLayer collisionLayer;
    private boolean isGrounded;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
    }

    public void moveLeft() {
        velocity.x = -MOVE_SPEED;
    }

    public void moveRight() {
        velocity.x = MOVE_SPEED;
    }

    public void stopHorizontal() {
        velocity.x = 0;
    }

    public void jump() {
        if (isGrounded) {
            velocity.y = JUMP_SPEED;
            isGrounded = false;
        }
    }

    public void update(float delta) {
        // Apply gravity
        velocity.y += GRAVITY * delta;

        float newX = getX() + velocity.x * delta;
        float newY = getY() + velocity.y * delta;

        // Check horizontal collision
        if (!isColliding(newX, getY())) {
            setX(newX);
        } else {
            velocity.x = 0;
        }

        // Check vertical collision
        if (!isColliding(getX(), newY)) {
            setY(newY);
            isGrounded = false;
        } else {
            // if landing set to isGrounded
            if (velocity.y < 0) {
                isGrounded = true;
            }
            velocity.y = 0;
        }
    }

    /**
     * Check collision for a single point (x,y) in tile units.
     */
    private boolean isCollisionAt(float x, float y) {
        int tileX = (int) Math.floor(x);
        int tileY = (int) Math.floor(y);

        // Out-of-bounds? Treat as no collision or handle differently
        if (tileX < 0 || tileY < 0
                || tileX >= collisionLayer.getWidth()
                || tileY >= collisionLayer.getHeight()) {
            return false;
        }

        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
        if (cell == null || cell.getTile() == null) {
            return false;
        }

        // Tile is "blocked" if it has the 'blocked' property
        return cell.getTile().getProperties().containsKey("blocked");
    }

    /**
     * Check if the 1×1 player bounding box collides at (x,y).
     */
    private boolean isColliding(float x, float y) {
        float w = getWidth();
        float h = getHeight();

        // Check corners of bounding box:
        // lower-left, lower-right, top-left, top-right
        return isCollisionAt(x, y)
                || isCollisionAt(x + w, y)
                || isCollisionAt(x, y + h)
                || isCollisionAt(x + w, y + h);
    }
}
