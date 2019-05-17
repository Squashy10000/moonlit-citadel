package Managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MoonlitCitadel;

import Components.AnimationComponent;
import Components.BodyComponent;
import Components.CollisionComponent;
import Components.PlayerComponent;
import Components.StateComponent;
import Components.TextureComponent;
import Components.TypeComponent;
import Helpers.BodyGenerator;
import Helpers.Figures;

import static com.mygdx.game.MoonlitCitadel.TAG;

public class EntityManager {
    private MoonlitCitadel moonlitCitadel;
    private World world;
    private SpriteBatch batch;
    private PooledEngine engine;
    private MyAssetManager myAssetManager;
    private TextureAtlas atlas;
    private BodyGenerator generator;
    private Vector2 tempPositionVector;
    private Vector2 tempDimensionVector;

    public EntityManager(MoonlitCitadel moonlitCitadel, World world, SpriteBatch batch, PooledEngine engine, MyAssetManager myAssetManager){
        this.moonlitCitadel = moonlitCitadel;
        this.world = world;
        this.batch = batch;
        this.engine = engine;
        this.myAssetManager = myAssetManager;
        Gdx.app.log(TAG, "Setting atlas");
        atlas = myAssetManager.getTextureAsset("/sprites/Output/MoonlitCitadelAtlas.atlas");
        tempPositionVector = new Vector2(Vector2.Zero);
        tempDimensionVector = new Vector2(Vector2.Zero);
        generator = new BodyGenerator(world);
    }

    public Entity spawnEntity(String entityName, int x, int y){
        Entity entity = engine.createEntity();
        switch(entityName) {
            case "Player":
            addBodyComponent(entity, entityName, x, y);
            addTypeComponent(entity, entityName);
            addCollisionComponent(entity);
            addPlayerComponent(entity);
            addStateComponent(entity, entityName);
            addAnimationComponent(entity, entityName);
            addTextureComponent(entity, entityName);
            break;
            case "Enemy":
                addBodyComponent(entity, entityName, x, y);
                addTypeComponent(entity, entityName);
                addCollisionComponent(entity);
                addStateComponent(entity, entityName);
                break;
            case "Object":
                addBodyComponent(entity, entityName,x,y);
                addTypeComponent(entity, entityName);
                addTextureComponent(entity, entityName);

        }
        engine.addEntity(entity);
        return entity;
    }

    private Entity addStateComponent(Entity entity, String entityName){
        StateComponent stateComponent = engine.createComponent(StateComponent.class);

        switch(entityName){
            case "Player":
                stateComponent.setDirection(StateComponent.DIRECTION.DOWN);
                stateComponent.setState(StateComponent.STATE.IDLE);
                break;
        }
        entity.add(stateComponent);
        return entity;
    }

    private Entity addPlayerComponent(Entity entity){
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        entity.add(playerComponent);
        return entity;
    }

    private Entity addCollisionComponent(Entity entity){
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        entity.add(collisionComponent);
        return entity;
    }


    private Entity addBodyComponent(Entity entity, String entityName, int x, int y){

        tempPositionVector.x = x;
        tempPositionVector.y = y;
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        FixtureDef fdef = new FixtureDef();

        //method to build body
        switch(entityName){
            case "Player":
                fdef.filter.categoryBits = Figures.PLAYER;
                fdef.filter.maskBits = Figures.ENEMY | Figures.LEVEL | Figures.OBJECT;
                tempDimensionVector.x = 1;
                tempDimensionVector.y = 1;
                bodyComponent.setBody(generator.createBody(entity, tempPositionVector,
                        tempDimensionVector, BodyDef.BodyType.DynamicBody, 1, fdef));

                bodyComponent.setActive(true);
                bodyComponent.getBody().setLinearDamping(3f);
                bodyComponent.getBody().setUserData(entity);
                break;
            case "Enemy":
                fdef.filter.categoryBits = Figures.ENEMY;
                fdef.filter.maskBits = Figures.PLAYER | Figures.LEVEL | Figures.OBJECT;
                tempDimensionVector.x = 1;
                tempDimensionVector.y = 1;
                bodyComponent.setBody(generator.createBody(entity, tempPositionVector,
                        tempDimensionVector, BodyDef.BodyType.DynamicBody, 1, fdef));

                bodyComponent.setActive(true);
                bodyComponent.getBody().setLinearDamping(3f);
                bodyComponent.getBody().setUserData(entity);
                break;
            case "Object":
                fdef.filter.categoryBits = Figures.OBJECT;
                fdef.filter.maskBits = Figures.ENEMY | Figures.LEVEL | Figures.PLAYER;
                tempDimensionVector.x = 1;
                tempDimensionVector.y = 1;
                bodyComponent.setBody(generator.createBody(entity, tempPositionVector,
                        tempDimensionVector, BodyDef.BodyType.DynamicBody, 1, fdef));

                bodyComponent.setActive(true);
                bodyComponent.getBody().setLinearDamping(3f);
                bodyComponent.getBody().setUserData(entity);
                break;
        }




        entity.add(bodyComponent);
        return entity;
    }

    private Entity addTypeComponent(Entity entity, String entityName){
        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        short type;
        switch (entityName){
            case "Player":
                type = Figures.PLAYER;
                break;

            case "Enemy":
                type = Figures.ENEMY;
                break;
            case "Object":
                type = Figures.OBJECT;
                break;
            default:
                    type = Figures.OTHER;

        }
        typeComponent.setType(type);
        entity.add(typeComponent);
        return entity;
    }
    private Entity addAnimationComponent(Entity entity, String entityName){
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);

        switch (entityName){
            case "Player":
               animationComponent.addAnimation(AnimationComponent.ANIMATIONSTATE.UP,
                       new Animation(0.37f, atlas.findRegions("NiraUp")))
                       .addAnimation(AnimationComponent.ANIMATIONSTATE.DOWN,
                               new Animation(0.37f, atlas.findRegions("NiraDown")))
                       .addAnimation(AnimationComponent.ANIMATIONSTATE.LEFT,
                               new Animation(0.37f, atlas.findRegions("NiraLeft")))
                       .addAnimation(AnimationComponent.ANIMATIONSTATE.RIGHT,
                               new Animation(0.37f, atlas.findRegions("NiraRight")));
               break;
        }
        entity.add(animationComponent);
        return entity;
    }

    private Entity addTextureComponent(Entity entity, String entityName){
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);

        switch (entityName){
            case "Player":
                textureComponent.setRegion((TextureRegion) entity
                        .getComponent(AnimationComponent.class)
                        .getAnimation(AnimationComponent.ANIMATIONSTATE.DOWN).getKeyFrames()[1] );
                break;
            case "Object":
                textureComponent.setRegion(new TextureRegion(atlas.findRegion("coindrop")));
                break;


        }
        entity.add(textureComponent);
        return entity;
    }

}
