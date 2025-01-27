package io.github.tbzrunner.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.tbzrunner.enteties.Player;
import io.github.tbzrunner.exceptions.InvalidMapException;

public class PlayerTest {

    private Sprite spriteMock;
    private TiledMapTileLayer collisionLayerMock;
    private Player player;

    @BeforeEach
    public void setUp() throws InvalidMapException {
        spriteMock = Mockito.mock(Sprite.class);
        collisionLayerMock = Mockito.mock(TiledMapTileLayer.class);
        Mockito.when(collisionLayerMock.getWidth()).thenReturn(10);
        Mockito.when(collisionLayerMock.getHeight()).thenReturn(10);
        player = new Player(spriteMock, collisionLayerMock);
    }

    @Test
    public void testMoveLeft() {
        player.moveLeft();
        assertEquals(-8f, player.velocity.x, "Player should move left with -MOVE_SPEED velocity.");
    }

    @Test
    public void testMoveRight() {
        player.moveRight();
        assertEquals(8f, player.velocity.x, "Player should move right with MOVE_SPEED velocity.");
    }

    @Test
    public void testStopHorizontal() {
        player.moveRight();
        player.stopHorizontal();
        assertEquals(0f, player.velocity.x, "Player should stop horizontal movement when stopHorizontal is called.");
    }

    @Test
    public void testJumpWhenGrounded() {
        player.isGrounded = true;
        player.jump();
        assertEquals(15f, player.velocity.y, "Player should jump with JUMP_SPEED velocity when grounded.");
        assertFalse(player.isGrounded, "Player should no longer be grounded after jumping.");
    }

    @Test
    public void testJumpWhenNotGrounded() {
        player.isGrounded = false;
        player.jump();
        assertEquals(0f, player.velocity.y, "Player should not change vertical velocity when not grounded.");
    }

    @Test
    public void testUpdateGravity() {
        float initialYVelocity = player.velocity.y;
        player.update(1f);
        assertEquals(initialYVelocity - 10f, player.velocity.y, "Gravity should be applied to the player's vertical velocity.");
    }

    @Test
    public void testConstructorThrowsExceptionWhenCollisionLayerIsNull() {
        assertThrows(InvalidMapException.class, () -> new Player(spriteMock, null), "Constructor should throw InvalidMapException if collision layer is null.");
    }
}
