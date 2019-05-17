package Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MoonlitCitadel;

import Components.BodyComponent;
import Helpers.Figures;
import Helpers.GameInput;
import Helpers.LevelCollisionGenerator;
import Managers.CollisionManager;
import Managers.EntityManager;
import Managers.MyAssetManager;
import Systems.PhysicsDebugSystem;
import Systems.PhysicsSystem;
import Systems.PlayerControlSystem;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private AssetManager assetManager;
    public MyAssetManager myAssetManager;

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
    //level generator
    private LevelCollisionGenerator levelCollisionGenerator;
    private Entity ground;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    // temp variables
    private Vector2 tempPosition;
    private Vector2 tempDimensions;

    public GameScreen(MoonlitCitadel game, SpriteBatch batch, MyAssetManager myAssetManager) {
        Gdx.app.log(TAG, "batch mode");
        this.batch = batch;
        this.game = game;
        this.myAssetManager = myAssetManager;
        tempDimensions = new Vector2(Vector2.Zero);
        tempPosition = new Vector2(Vector2.Zero);
        Gdx.app.log(TAG, "world settings");
        gravity = new Vector2(0,-9.8f);
        world = new World(Figures.GRAVITATIONAL_FORCES, false);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        gameViewport = new FitViewport(Figures.VIRTUALWIDTH,Figures.VIRTUALHEIGHT,camera);
        camera.position.set(gameViewport.getWorldWidth()/2, gameViewport.getWorldHeight()/2,0);

        gameInput = new GameInput(gameViewport);

        engine = new PooledEngine(100,500,300,1000);
        Gdx.app.log(TAG,"init ashley systems");
        initAshleySystems();
        Gdx.app.log(TAG, "tiled map");
        map = myAssetManager.getMapAsset("TiledMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map,1/Figures.PPM,this.batch);

        Gdx.app.log(TAG, "entitymanager");
        entityManager = new EntityManager(game, world, this.batch, engine, this.myAssetManager);
        levelCollisionGenerator = new LevelCollisionGenerator(world, engine);
        Gdx.app.log(TAG, "collision manager");
        collisionManager = new CollisionManager();
        Gdx.app.log(TAG,"levelcollsiongenerator to tiledmap");
        levelCollisionGenerator.createCollisionLevel(map);
        Gdx.app.log(TAG, "worldcontactlistener");
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
        Gdx.input.setInputProcessor(gameInput);
        player = entityManager.spawnEntity("Player", 8,5);

        //testing level gen
        tempPosition.x = 0;
        tempPosition.y = 1;
        tempDimensions.x = gameViewport.getWorldWidth();
        tempDimensions.y = 1;
      //  ground = levelCollisionGenerator.createCollisionLevel(tempPosition, tempDimensions, BodyDef.BodyType.StaticBody,1);


        Gdx.input.setInputProcessor(gameInput);

    }

    @Override
    public void render(float delta) {

        camera.position.set(player.getComponent(BodyComponent.class).getBody().getPosition(),0);
        camera.update();

        Gdx.gl.glClearColor(0.5f,0.5f,0.76f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();
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
        map.dispose();

    }
}
