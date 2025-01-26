package io.github.tbzrunner.enteties;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NPC extends Sprite {

    private float speed = 2f;
    private float startX, endX;
    private boolean movingRight = true;

    public NPC(Sprite sprite, float startX, float endX) {
        super(sprite);
        this.startX = startX;
        this.endX = endX;
        setPosition(startX, getY());
    }

    public void update(float delta) {
        float newX = getX() + (movingRight ? speed : -speed) * delta;

        // Reverse direction if the NPC reaches bounds
        if (newX > endX) {
            newX = endX;
            movingRight = false;
        } else if (newX < startX) {
            newX = startX;
            movingRight = true;
        }

        setX(newX);
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
