package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by goetsch on 31.08.14.
 */
public class StateComponent extends Component {
    public enum State {
        WALK_LEFT ("LEFT"), WALK_RIGHT ("RIGHT"), JUMP_LEFT ("LEFT"), JUMP_RIGHT ("RIGHT"), STAND_LEFT ("LEFT"), STAND_RIGHT ("RIGHT"), ATTACK_RIGHT ("RIGHT"), ATTACK_LEFT ("LEFT");
        private final String dir;
        State(String dir) {
            this.dir = dir;
        }
        public String dir() {return dir;}
    }

    private State state;
    private State prevState;
    private float stateTime;

    public StateComponent() {
        state = State.STAND_RIGHT;
        this.prevState = this.state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (!this.state.equals(state)) {
            this.prevState = this.state;
            this.state = state;
            this.stateTime = 0;
        }
    }

    public State getPrevState() {
        return prevState;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float deltaTime) {
        this.stateTime += deltaTime;
    }
}