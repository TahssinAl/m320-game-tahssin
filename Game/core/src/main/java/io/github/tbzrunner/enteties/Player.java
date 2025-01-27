package io.github.tbzrunner.enteties;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import io.github.tbzrunner.exceptions.InvalidMapException;

public class Player extends Sprite {

    private static final float GRAVITY = -10f;
    private static final float MOVE_SPEED = 8f;
    private static final float JUMP_SPEED = 15f;

    public final Vector2 velocity = new Vector2();
    private final TiledMapTileLayer collisionLayer;
    public boolean isGrounded;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) throws InvalidMapException {
        super(sprite);

        if (collisionLayer == null) {
            throw new InvalidMapException("Collision layer cannot be null for the player.");
        }

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

    public void update(float delta)  {
        velocity.y += GRAVITY * delta;

        float newX = getX() + velocity.x * delta;
        float newY = getY() + velocity.y * delta;

        if (!isColliding(newX, getY())) {
            setX(newX);
        } else {
            velocity.x = 0;
        }

        if (!isColliding(getX(), newY)) {
            setY(newY);
            isGrounded = false;
        } else {
            if (velocity.y < 0) {
                isGrounded = true;
            }
            velocity.y = 0;
        }
    }

    private boolean isCollisionAt(float x, float y) {
        int tileX = (int) Math.floor(x);
        int tileY = (int) Math.floor(y);

        if (tileX < 0 || tileY < 0 || tileX >= collisionLayer.getWidth() || tileY >= collisionLayer.getHeight()) {
            return false;
        }

        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
        if (cell == null || cell.getTile() == null) {
            return false;
        }

        return cell.getTile().getProperties().containsKey("blocked");
    }

    private boolean isColliding(float x, float y) {
        float w = getWidth();
        float h = getHeight();

        return isCollisionAt(x, y)
            || isCollisionAt(x + w, y)
            || isCollisionAt(x, y + h)
            || isCollisionAt(x + w, y + h);
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
