package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MoonlitCitadel;

public class MenuScreen implements Screen {

    private SpriteBatch batch;
    private Texture img;
    private MoonlitCitadel game;

    public MenuScreen(MoonlitCitadel game, SpriteBatch batch) {
        this.batch = batch;
        this.game = game;
    }

    public static final String TAG = MenuScreen.class.getSimpleName();
    @Override
    public void show() {
        Gdx.app.log(TAG, "menu show");
    }

    @Override
    public void render(float delta) {
        Gdx.app.log(TAG, "menu render");
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "menu resize");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "menu pause");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "menu resume");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "menu hide");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "menu dispose");
    }
}
