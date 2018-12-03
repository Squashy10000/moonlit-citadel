package Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import Components.BodyComponent;

public class BodyGenerator {
    private World world;
    public BodyGenerator(World world){
        this.world = world;
    }


    public Body createBody(Entity entity, Vector2 position, Vector2 dimensions,
                           BodyDef.BodyType type, int bodyType, FixtureDef fixtureDef){
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = fixtureDef;

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

        bdef.gravityScale = 1;
        Shape shape;
        switch (bodyType){
            case 0:
            default:
                shape = new CircleShape();
                shape.setRadius(dimensions.x/2);
                bdef.position.set(position.x+dimensions.x/2, position.y+dimensions.y/2);
                break;
            case 1:
                shape = new PolygonShape();
                ((PolygonShape)shape).setAsBox(dimensions.x/2, dimensions.y/2);
                bdef.position.set(position.x+dimensions.x/2, position.y+dimensions.y/2);
                break;
        }

        body = world.createBody(bdef);

        fdef.shape = shape;
        fdef.density = 1f;
        fdef.restitution = 0f;
        fdef.friction = 0;
        body.createFixture(fdef);



        shape.dispose();

        return body;

    }


}

