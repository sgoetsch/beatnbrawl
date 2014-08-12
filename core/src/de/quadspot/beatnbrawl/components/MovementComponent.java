package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by goetsch on 05.08.14.
 */
public class MovementComponent extends Component {
    private Vector3 velocity;
    private float maxVelocity;

    public MovementComponent(Vector3 velocity, float maxVelocity) {
        this.velocity = velocity;
        this.maxVelocity = maxVelocity;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }
}

