package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by goetsch on 05.08.14.
 */
public class PositionComponent extends Component{

    private Vector3 position;
    private Vector3 oldPosition = new Vector3(0,0,0);

    public Vector3 getOldPosition() {
        return oldPosition;
    }

    public PositionComponent(Vector3 position) {
        this.position = position;
    }

    public Vector3 getPosition() {
        return position;
    }
}
