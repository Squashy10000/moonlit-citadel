package Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MoonlitCitadel;

import Helpers.Figures;
import Helpers.GameInput;
import Managers.CollisionManager;
import Managers.EntityManager;
import Systems.PhysicsDebugSystem;
import Systems.PhysicsSystem;
import Systems.PlayerControlSystem;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private Texture img;
    private MoonlitCitadel game;
    //box2d
    private World world;
    private Body body;
    private Vector2 gravity;
    private CollisionManager collisionManager;
    //view
    private OrthographicCamera camera;
    private Viewport gameViewport;
    private Box2DDebugRenderer b2dr;
    //controls
    private GameInput gameInput;
    //ashley
    private PooledEngine engine;
    private PhysicsSystem physicsSystem;
    private PhysicsDebugSystem physicsDebugSystem;
    private PlayerControlSystem playerControlSystem;
    //entity manager
    private EntityManager entityManager;
    private Entity player;

    public GameScreen(MoonlitCitadel game, SpriteBatch batch) {
        this.batch = batch;
        this.game = game;

        gravity = new Vector2(0,-9.8f);
        world = new World(Figures.GRAVITATIONAL_FORCES, false);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        gameViewport = new FitViewport(Figures.VIRTUALWIDTH,Figures.VIRTUALHEIGHT,camera);
        camera.position.set(gameViewport.getWorldWidth()/2, gameViewport.getWorldHeight()/2,0);

        gameInput = new GameInput(gameViewport);

        engine = new PooledEngine(100,500,300,1000);

        initAshleySystems();
        entityManager = new EntityManager(game, world, this.batch, engine);
        collisionManager = new CollisionManager();
        world.setContactListener(collisionManager);
    }


    public void initAshleySystems() {
        physicsSystem = new PhysicsSystem(world);
        physicsDebugSystem = new PhysicsDebugSystem(world, camera);
        playerControlSystem = new PlayerControlSystem(gameInput);

        engine.addSystem(physicsSystem);
        engine.addSystem(physicsDebugSystem);
        engine.addSystem(playerControlSystem);
    }

    public static final String TAG = GameScreen.class.getSimpleName();
    @Override
    public void show() {
        player = entityManager.spawnEntity("Player", 8,5);
        Gdx.input.setInputProcessor(gameInput);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.4f,0.75f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
    gameViewport.update(width, height);
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
        world.dispose();

    }
}
