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
        WALK_LEFT ("LEFT"), WALK_RIGHT ("RIGHT"), JUMP_LEFT ("LEFT"), JUMP_RIGHT ("RIGHT"), STAND_LEFT ("LEFT"), STAND_RIGHT ("RIGHT");
        private final String dir;
        State(String dir) {
            this.dir = dir;
        }
        public String dir() {return dir;}
    }
    private Vector3 velocity;
    private Vector3 maxVelocity;
    private State state;
    private State prevState;

    public MovementComponent(Vector3 velocity, Vector3 maxVelocity) {
        this.velocity = velocity;
        this.maxVelocity = maxVelocity;
        state = State.STAND_RIGHT;
        this.prevState = this.state;
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

    public void setState(State state) {
        this.prevState = this.state;
        this.state = state;
    }

    public State getPrevState() {
        return prevState;
    }
}

