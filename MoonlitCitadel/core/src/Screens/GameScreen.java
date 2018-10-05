package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MoonlitCitadel;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private Texture img;
    private MoonlitCitadel game;
    //box2d
    private World world;
    private Body body;
    private Vector2 gravity;
    //view
    private OrthographicCamera camera;
    private Box2DDebugRenderer b2dr;

    public GameScreen(MoonlitCitadel game, SpriteBatch batch) {
        this.batch = batch;
        this.game = game;

        gravity = new Vector2(0,-9.8f);
        world = new World(gravity, false);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static final String TAG = GameScreen.class.getSimpleName();
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
