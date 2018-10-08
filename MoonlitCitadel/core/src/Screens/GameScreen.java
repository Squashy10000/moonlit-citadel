package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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

        camera.setToOrtho(false, 32, 20);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    public Body createBody(Vector2 position, float size){
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(position.x, position.y);
        body = world.createBody(bdef);

        CircleShape shape = new CircleShape();
        shape.setRadius(size/2);
        fdef.shape = shape;
        fdef.density = 1f;
        body.createFixture(fdef);

        shape.dispose();

        return body;

    }

    public static final String TAG = GameScreen.class.getSimpleName();
    @Override
    public void show() {
    body = createBody(new Vector2(camera.viewportWidth/2, camera.viewportHeight), 10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0 , 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        world.step(delta,6,2);

        b2dr.render(world, camera.combined);

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
    b2dr.dispose();
    world.dispose();
    }
}
