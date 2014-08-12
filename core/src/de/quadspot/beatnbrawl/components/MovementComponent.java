package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by goetsch on 05.08.14.
 */
public class MovementComponent extends Component {
    private Vector3 velocity;
    private Vector3 maxVelocity;

    public MovementComponent(Vector3 velocity, Vector3 maxVelocity) {
        this.velocity = velocity;
        this.maxVelocity = maxVelocity;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Vector3 getMaxVelocity() {
        return maxVelocity;
    }
}

