package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MoonlitCitadel;

import Helpers.GameInput;
import Managers.MyAssetManager;

public class MenuScreen implements Screen {

    private SpriteBatch batch;
    private Texture img;
    private MoonlitCitadel game;
    private float waitTime = 2f;
    private MyAssetManager myAssetManager;


    private GameInput gameInput;

    public MenuScreen(MoonlitCitadel game, SpriteBatch batch, MyAssetManager myAssetManager) {
        this.batch = batch;
        this.game = game;
        this.myAssetManager = myAssetManager;
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
        waitTime-=delta;
        if(waitTime<=0){
            game.setScreen(MoonlitCitadel.SCREENTYPE.GAME);
            waitTime=2f;
        }
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
