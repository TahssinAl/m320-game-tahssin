package io.github.tbzrunner.enteties;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseSprite extends Sprite {

    public BaseSprite(Sprite sprite) {
        super(sprite);
    }

    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }

    public abstract void update(float delta);
}
