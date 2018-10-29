package Managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MoonlitCitadel;

import Components.BodyComponent;
import Helpers.BodyGenerator;
import Helpers.Figures;

public class EntityManager {
    private MoonlitCitadel moonlitCitadel;
    private World world;
    private SpriteBatch batch;
    private PooledEngine engine;
    private BodyGenerator generator;

    public EntityManager(MoonlitCitadel moonlitCitadel, World world, SpriteBatch batch, PooledEngine engine){
        this.moonlitCitadel = moonlitCitadel;
        this.world = world;
        this.batch = batch;
        this.engine = engine;

        generator = new BodyGenerator(world);
    }

    public Entity spawnEntity(String entityName, int x, int y){
        Entity entity = engine.createEntity();
    }


    private Entity addBodyComponent(Entity entity, String entityName, int x, int y){
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        FixtureDef fdef = new FixtureDef();

        //method to build body
        switch(entityName){
            case "Player":
                fdef.filter.categoryBits = Figures.PLAYER;
                fdef.filter.maskBits = Figures.ENEMY | Figures.LEVEL;

                bodyComponent.setBody(generator.createBody(entity));
        }




        entity.add(bodyComponent);
    }
}
