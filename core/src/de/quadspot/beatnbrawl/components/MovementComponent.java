package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by goetsch on 05.08.14.
 */
public class MovementComponent extends Component {
    public enum State {
        WALK_LEFT, WALK_RIGHT, JUMP_LEFT, JUMP_RIGHT, STAND_LEFT, STAND_RIGHT
    }
    private Vector3 velocity;
    private Vector3 maxVelocity;
    private State state;

    public MovementComponent(Vector3 velocity, Vector3 maxVelocity) {
        this.velocity = velocity;
        this.maxVelocity = maxVelocity;
        state = State.WALK_RIGHT;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Vector3 getMaxVelocity() {
        return maxVelocity;
    }

    public State getState() {
        return state;
    }
}

