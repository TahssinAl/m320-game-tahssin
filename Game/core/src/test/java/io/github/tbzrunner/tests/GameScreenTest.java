package io.github.tbzrunner.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.tbzrunner.screens.GameScreen;
import io.github.tbzrunner.enteties.Player;
import io.github.tbzrunner.enteties.NPC;
import io.github.tbzrunner.exceptions.InvalidMapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameScreenTest {

    private GameScreen gameScreen;

    @Mock
    private TiledMap mockMap;
    @Mock
    private TiledMapTileLayer mockLayer;
    @Mock
    private OrthogonalTiledMapRenderer mockMapRenderer;
    @Mock
    private OrthographicCamera mockCamera;
    @Mock
    private Player mockPlayer;
    @Mock
    private NPC mockNPC;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock dependencies
        when(mockMap.getLayers()).thenReturn((MapLayers) mock(TiledMapTileLayer[].class));
        when(mockMap.getLayers().get(0)).thenReturn(mockLayer);

        gameScreen = new GameScreen();

        // Inject mocked dependencies
        gameScreen.map = mockMap;
        gameScreen.mapRenderer = mockMapRenderer;
        gameScreen.camera = mockCamera;
        gameScreen.player = mockPlayer;
        gameScreen.npc = mockNPC;
    }

    @Test
    public void testLoadLevel_withValidMap_shouldInitializeResources() throws InvalidMapException {
        when(mockMap.getProperties().containsKey("width")).thenReturn(true);
        when(mockMap.getProperties().containsKey("height")).thenReturn(true);

        gameScreen.loadLevel(1);

        verify(mockMapRenderer).setMap(mockMap);
        assertNotNull(gameScreen.player, "Player should not be null after loading a level.");
        assertNotNull(gameScreen.npc, "NPC should not be null after loading a level.");
    }

    @Test
    public void testLoadLevel_withInvalidMap_shouldThrowException() {
        when(mockMap.getProperties().containsKey("width")).thenReturn(false);

        assertThrows(InvalidMapException.class, () -> gameScreen.loadLevel(1));
    }

    @Test
    public void testFindSpawnPoint_shouldReturnCorrectSpawnPoint() {
        when(mockLayer.getWidth()).thenReturn(10);
        when(mockLayer.getHeight()).thenReturn(10);

        TiledMapTileLayer.Cell mockCell = mock(TiledMapTileLayer.Cell.class);
        when(mockLayer.getCell(anyInt(), anyInt())).thenReturn(mockCell);
        when(mockCell.getTile().getProperties().containsKey("spawnpoint")).thenReturn(true);

        Vector2 spawnPoint = gameScreen.findSpawnPoint();
        assertNotNull(spawnPoint, "Spawn point should not be null if found.");
    }

    @Test
    public void testHandleInput_withKeyPressed_shouldMovePlayer() {
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(false);
        when(Gdx.input.isKeyJustPressed(Input.Keys.W)).thenReturn(false);

        gameScreen.handleInput();

        verify(mockPlayer).moveLeft();
        verify(mockPlayer, never()).moveRight();
        verify(mockPlayer, never()).jump();
    }

    @Test
    public void testRender_whenPlayerReachesGoal_shouldLoadNextLevel() {
        Vector2 mockGoalPoint = new Vector2(5, 5);
        when(mockPlayer.getBoundingRectangle()).thenReturn(new Rectangle(5, 5, 1, 1));
        gameScreen.goalPoint = mockGoalPoint;

        gameScreen.render(1f);

        assertEquals(2, gameScreen.currentLevel, "Level should increment when goal is reached.");
    }

    @Test
    public void testDispose_shouldCleanUpResources() {
        gameScreen.dispose();

        verify(mockMap).dispose();
        verify(mockMapRenderer).dispose();
        verify(mockPlayer).getTexture();
        verify(mockNPC).getTexture();
    }
}
