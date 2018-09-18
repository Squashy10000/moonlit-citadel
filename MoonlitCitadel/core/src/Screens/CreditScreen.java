package Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MoonlitCitadel;

public class CreditScreen implements Screen {

    private SpriteBatch batch;
    private Texture img;
    private MoonlitCitadel game;

    public CreditScreen(MoonlitCitadel game, SpriteBatch batch) {
        this.batch = batch;
        this.game = game;
    }

    public static final String TAG = CreditScreen.class.getSimpleName();
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
