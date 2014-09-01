package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by goetsch on 05.08.14.
 */
public class MovementComponent extends Component {

    private Vector3 velocity;
    private Vector3 maxVelocity;
    private boolean isMovingUp;
    private boolean isMovingDown;
    private boolean isMovingLeft;
    private boolean isMovingRight;
    private boolean isStanding;
    private boolean isFacedRight=true;
    private boolean isFacedLeft;


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

    public boolean isMovingUp() {
        return isMovingUp;
    }

    public void setMovingUp(boolean isMovingUp) {
        this.isMovingUp = isMovingUp;
    }

    public boolean isMovingDown() {
        return isMovingDown;
    }

    public void setMovingDown(boolean isMovingDown) {
        this.isMovingDown = isMovingDown;
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public void setMovingLeft(boolean isMovingLeft) {
        this.isMovingLeft = isMovingLeft;
        this.isFacedLeft = true;
        this.isFacedRight = false;
        this.isMovingRight = false;
        this.isStanding = false;

    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean isMovingRight) {
        this.isMovingRight = isMovingRight;
        this.isFacedLeft = false;
        this.isFacedRight = true;
        this.isMovingLeft = false;
        this.isStanding = false;
    }

    public boolean isStanding() {
        return isStanding;
    }

    public void setStanding(boolean isStanding) {
        this.isStanding = isStanding;
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }

    public boolean isFacedLeft() {
        return isFacedLeft;
    }

    public boolean isFacedRight() {
        return isFacedRight;
    }

}

