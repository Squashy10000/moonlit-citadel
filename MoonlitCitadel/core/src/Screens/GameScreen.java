package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MoonlitCitadel;

import java.util.Random;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private Texture img;
    private MoonlitCitadel game;
    //box2d
    private World world;
    private Body body;
    private Body body2;
    private Vector2 gravity;
    private float random;
    private static final short GROUND = 1;
    private static final short PLAYER = 2;
    private static final short ENEMY = 3;
    //view
    private OrthographicCamera camera;
    private Box2DDebugRenderer b2dr;

    public GameScreen(MoonlitCitadel game, SpriteBatch batch) {
        this.batch = batch;
        this.game = game;

        gravity = new Vector2(0,-1f);
        world = new World(gravity, false);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        camera.setToOrtho(false, 16, 10);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);




    }

    public Body createBody(Vector2 position, float size, float force, BodyDef.BodyType type, int bodyType, short self, short interactions){
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        switch (type) {
            case StaticBody:
                bdef.type = BodyDef.BodyType.StaticBody;
                break;
            case DynamicBody:
                bdef.type = BodyDef.BodyType.DynamicBody;
                break;
            case KinematicBody:
                bdef.type = BodyDef.BodyType.KinematicBody;
                break;
        }
        bdef.gravityScale = force;
        bdef.position.set(position.x, position.y);
        body = world.createBody(bdef);
        Shape shape;
    switch (bodyType){
        case 0:
            shape = new CircleShape();
            shape.setRadius(size/2);
            break;
        case 1:
            shape = new PolygonShape();
            ((PolygonShape)shape).setAsBox(size/2, size/2);
            break;
        default:shape = new CircleShape();
            shape.setRadius(size/2);
    }


        fdef.shape = shape;
        fdef.density = 1f;
        fdef.restitution = .75f;
        fdef.filter.categoryBits = self;
        fdef.filter.maskBits = interactions;
        body.createFixture(fdef);

        shape.dispose();

        return body;

    }

    public static final String TAG = GameScreen.class.getSimpleName();
    @Override
    public void show() {
        for(int i = 2; i<5; i++) {
            random = MathUtils.random( 1, 5);
            body = createBody(new Vector2(i, camera.viewportHeight), random, 1f, BodyDef.BodyType.DynamicBody, 0, PLAYER, (short)(GROUND|ENEMY));
        }
        for(int i = 3; i<6; i++) {
            random = MathUtils.random( 1, 5);
            body = createBody(new Vector2(i, camera.viewportHeight+10), random, 1f, BodyDef.BodyType.DynamicBody, 0, ENEMY, (short)(GROUND|PLAYER));
        }
    body2 = createBody(new Vector2(camera.viewportWidth/2, -camera.viewportHeight/2 +1), camera.viewportWidth, 0, BodyDef.BodyType.StaticBody, 1, GROUND, PLAYER);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0 , 0, 0, 1);
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
