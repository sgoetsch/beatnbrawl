package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by goetsch on 31.08.14.
 */
public class ActionComponent extends Component {
    private boolean isAttacking;

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
}
