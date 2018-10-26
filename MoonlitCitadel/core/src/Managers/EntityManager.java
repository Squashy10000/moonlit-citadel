package Managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MoonlitCitadel;

import Helpers.BodyGenerator;

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

    }
}
