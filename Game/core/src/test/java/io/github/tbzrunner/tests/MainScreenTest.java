package io.github.tbzrunner.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import io.github.tbzrunner.screens.GameScreen;
import io.github.tbzrunner.screens.MainScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MainScreenTest {

    private MainScreen mainScreen;

    @Mock
    private Game mockGame; // Mock für die Navigation zwischen Screens
    @Mock
    private SpriteBatch mockBatch; // Mock für das Rendern
    @Mock
    private BitmapFont mockFont; // Mock für Schriftart
    @Mock
    private OrthographicCamera mockCamera;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Hauptscreen vorbereiten und Abhängigkeiten mocken
        mainScreen = new MainScreen();
        mainScreen.batch = mockBatch;
        mainScreen.font = mockFont;
        mainScreen.camera = mockCamera;

        // Ersetzen des Game ApplicationListeners
        Gdx.app = (com.badlogic.gdx.Application) mock(ApplicationListener.class);
        when(Gdx.app.getApplicationListener()).thenReturn(mockGame);
    }

    @Test
    public void testRender_drawsText() {
        // Testet, ob der Text korrekt gerendert wird
        mainScreen.render(1f);

        verify(mockBatch).begin();
        verify(mockFont).draw(mockBatch, "Hallo und Willkommen zum TBZRunner!",
            Gdx.graphics.getWidth() / 2f - 240, Gdx.graphics.getHeight() / 2f);
        verify(mockFont).draw(mockBatch, "Drücke ENTER, um zu starten...",
            Gdx.graphics.getWidth() / 2f - 240, Gdx.graphics.getHeight() / 2f - 50);
        verify(mockBatch).end();
    }

    @Test
    public void testRender_enterKeySwitchesToGameScreen() {
        // Simuliere, dass die ENTER-Taste gedrückt wird
        when(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)).thenReturn(true);

        mainScreen.render(1f);

        // Prüfen, ob die `setScreen`-Methode von Game aufgerufen wurde
        verify(mockGame).setScreen(any(GameScreen.class));
    }

    @Test
    public void testDispose_releasesResources() {
        mainScreen.dispose();

        verify(mockBatch).dispose();
        verify(mockFont).dispose();
    }

    @Test
    public void testResize_updatesCamera() {
        int newWidth = 1920;
        int newHeight = 1080;

        mainScreen.resize(newWidth, newHeight);

        verify(mockCamera).setToOrtho(false, newWidth, newHeight);
    }
}
