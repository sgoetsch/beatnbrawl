package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by goetsch on 05.08.14.
 */
public class PositionComponent extends Component{

    private Vector2 position;

    public PositionComponent(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }
}
