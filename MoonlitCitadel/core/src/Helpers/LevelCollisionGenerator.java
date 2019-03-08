package Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import Components.BodyComponent;
import Components.TransformComponent;
import Components.TypeComponent;

public class LevelCollisionGenerator {

    public static final String TAG = LevelCollisionGenerator.class.getSimpleName();
    private World world;
    private PooledEngine engine;
    private TiledMap map;
    private Array<Body>levelBodies;
    private Array<Entity>levelEntities;
    private static final String COLLISION_LAYER = "COLLISION_LAYER";


    public LevelCollisionGenerator(World world, PooledEngine engine) {
        this.world = world;
        this.engine = engine;
        levelBodies = new Array<Body>();
        levelEntities = new Array<Entity>();
    }
    public void createCollisionLevel(TiledMap map) {
        Gdx.app.log(TAG,"setting map");
        this.map = map;
        Gdx.app.log(TAG,"Getting collision layer");
        MapLayer layer = map.getLayers().get(COLLISION_LAYER);
        Gdx.app.log(TAG, "getting map objects");
        for(MapObject object: layer.getObjects()){
        LevelGeometry geometry = null;
        if(object instanceof TextureMapObject){
            continue;
        }
            Gdx.app.log(TAG,"creating shape variable, getting bodytypes");
            Shape shape;
            BodyDef bdef = new BodyDef();
            String type = object.getProperties().get("Type",String.class);
            switch (type) {
                case "StaticBody":
                    bdef.type = BodyDef.BodyType.StaticBody;
                    break;
                case "DynamicBody":
                    bdef.type = BodyDef.BodyType.DynamicBody;
                    break;
                case "KinematicBody":
                    bdef.type = BodyDef.BodyType.KinematicBody;
                    break;
            }
            Gdx.app.log(TAG,"map shapes");
            if(object instanceof RectangleMapObject){
                Gdx.app.log(TAG, "rectangle");
                geometry = getRectangle((RectangleMapObject)object);
                shape = geometry.getShape();
            }

            else if(object instanceof PolylineMapObject){
                Gdx.app.log(TAG,"polyline");
                geometry = getPolyline((PolylineMapObject)object);
                shape = geometry.getShape();
            }
            else if(object instanceof PolygonMapObject){
                Gdx.app.log(TAG,"polygon");
                geometry = getPolygon((PolygonMapObject)object);
                shape = geometry.getShape();
            }
            else if(object instanceof CircleMapObject){
                Gdx.app.log(TAG,"circle");
                geometry = getCircle((CircleMapObject)object);
                shape = geometry.getShape();
            }
            else {
                Gdx.app.log(TAG, "Unrecognised map shape "+object.toString());
                continue;
            }
            Gdx.app.log(TAG,"fixturedef");
            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            fdef.isSensor = false;
            fdef.density = 1f;
            fdef.restitution = 0f;
            fdef.friction = 0;
            Gdx.app.log(TAG,"categorybits");
            fdef.filter.categoryBits = Figures.LEVEL;
            fdef.filter.maskBits = Figures.PLAYER | Figures.ENEMY;
            Body body = world.createBody(bdef);
            body.createFixture(fdef);

            Entity levelEntity = engine.createEntity();

            BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
            bodyComponent.setBody(body);
            bodyComponent.getBody().setUserData(levelEntity);

            TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
            typeComponent.setType(Figures.LEVEL);

            TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
            transformComponent.setPosition(body.getLocalCenter());

            levelEntity.add(bodyComponent);
            levelEntity.add(typeComponent);
            levelEntity.add(transformComponent);




            engine.addEntity(levelEntity);
            fdef.shape = null;
            shape.dispose();
            //levelBodies.add(body);
            levelEntities.add(levelEntity);
        }






    }

    private LevelGeometry getRectangle(RectangleMapObject rectangleMapObject){
        Rectangle rectangle = rectangleMapObject.getRectangle();
        PolygonShape polygon = new PolygonShape();

        Vector2 size = new Vector2(rectangle.x+rectangle.width,rectangle.y+rectangle.height);
        polygon.setAsBox(rectangle.width/2,rectangle.height/2,size,0.0f);

        return new LevelGeometry(polygon);
    }
    private LevelGeometry getPolygon(PolygonMapObject polygonMapObject){
        PolygonShape polygon = new PolygonShape();
        float[]vertices = polygonMapObject.getPolygon().getTransformedVertices();

        polygon.set(vertices);


        return new LevelGeometry(polygon);
    }
    private LevelGeometry getPolyline(PolylineMapObject polylineMapObject){

        float[] vertices = polylineMapObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for(int i=0;i<vertices.length;i++){
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i*2];
            worldVertices[i].y = vertices[i*2+1];
        }
        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);

        return new LevelGeometry(chain);
    }
    private LevelGeometry getCircle(CircleMapObject circleMapObject){
        Circle circle = circleMapObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius);
        circleShape.setPosition(new Vector2(circle.x,circle.y));

        return new LevelGeometry(circleShape);
    }



    public static class LevelGeometry{
        private Shape shape;

        public LevelGeometry(Shape shape) {
            this.shape = shape;
        }

        public Shape getShape() {
            return shape;
        }
    }
    public void dispose(){
        for(Entity entity: levelEntities){
            BodyComponent bodyComponent = entity.getComponent(BodyComponent.class);
            if(bodyComponent.getBody()!=null){
                world.destroyBody(bodyComponent.getBody());
            }
            engine.removeEntity(entity);
        }
        levelEntities.clear();
    }
}
