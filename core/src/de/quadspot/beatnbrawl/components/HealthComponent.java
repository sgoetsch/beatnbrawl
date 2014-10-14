package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by goetsch on 02.09.14.
 */
public class HealthComponent extends Component {
    private Integer health = 100;
    private int strength = 100;
    private int resilience = 100;

    public HealthComponent(Integer health) {
        this.health = health;
    }

    public void countHit() {
        health -= 10;
        System.out.println(health);
    }

    public void countHit(long hitcount) {
        health -= (int)hitcount;
        System.out.println(health);
    }

    public boolean isDead() {
        return ( health <= 0 );
    }

    public int getHealth() {
        return health;
    }

    public String getHealthString() {
        return health.toString();
    }
}
