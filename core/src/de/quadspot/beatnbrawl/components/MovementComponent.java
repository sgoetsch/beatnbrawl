package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by goetsch on 05.08.14.
 */
public class MovementComponent extends Component {
    private Vector2 velocity = new Vector2(100,0);
    private float maxVelocity = 0;

    public MovementComponent(Vector2 velocity, float maxVelocity) {
        this.velocity = velocity;
        this.maxVelocity = maxVelocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }
}

