package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MoonlitCitadel;

import Managers.MyAssetManager;

public class LoadScreen implements Screen {

    private SpriteBatch batch;
    private Texture img;
    private MoonlitCitadel game;
    private float waitTime = 2f;
    private MyAssetManager myAssetManager;

    public LoadScreen(MoonlitCitadel game, SpriteBatch batch, MyAssetManager myAssetManager) {
        this.game = game;
        this.batch = batch;
        this.myAssetManager = myAssetManager;
        img = new Texture("badlogic.jpg");
    }

    public static final String TAG = LoadScreen.class.getSimpleName();
    @Override
    public void show() {

        Gdx.app.log(TAG, "in loading screen show method");

        loadingMapAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.app.log(TAG, "in loading screen render method");
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
        waitTime-=delta;

        myAssetManager.updateAssetloading();
        if(waitTime<=0 && myAssetManager.isAssetLoaded("TiledMap.tmx")){
            game.setScreen(MoonlitCitadel.SCREENTYPE.MENU);
            waitTime=2f;
        }

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "in loading screen resize method");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "in loading screen pause method");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "in loading screen resume method");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "in loading screen hide method");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "in loading screen dispose method");
        img.dispose();
    }

    private void loadingMapAssets(){
        myAssetManager.loadMapAsset("TiledMap.tmx");
    }
}
