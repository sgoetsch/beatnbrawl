package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by goetsch on 02.09.14.
 */
public class AIComponent extends Component {
    private float decisionTime;

    public float getDecisionTime() {
        return decisionTime;
    }

    public boolean getDecisionTime(float deltaTime) {
        if (decisionTime <= 0){
            decisionTime = MathUtils.random(0.1f, 0.5f);
            return true;
        }
        decisionTime -= deltaTime;
        return false;
    }

    public void setDecisionTime(float deltaTime) {
        this.decisionTime -= deltaTime;
    }

    public void resetDecisionTime(float time){
        this.decisionTime = time;
    }
}
