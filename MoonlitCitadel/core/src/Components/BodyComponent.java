package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class BodyComponent implements Component, Pool.Poolable {

    private Body body;
    private boolean dead = false;


    @Override
    public void reset() {

    }
}
