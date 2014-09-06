package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by goetsch on 31.08.14.
 */
public class ActionComponent extends Component {
    private boolean isAttacking;
    private boolean isKnockedOut;

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isKnockedOut() {
        return isKnockedOut;
    }

    public void setKnockedOut(boolean isKnockedOut) {
        this.isKnockedOut = isKnockedOut;
    }
}
