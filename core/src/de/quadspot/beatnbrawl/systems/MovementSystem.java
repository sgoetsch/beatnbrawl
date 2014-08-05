package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;

/**
 * Created by goetsch on 05.08.14.
 */
public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.getFamilyFor(PositionComponent.class, MovementComponent.class));
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        MovementComponent movement = entity.getComponent(MovementComponent.class);

        position.getPosition().add(movement.getVelocity().cpy().scl(deltaTime));
    }
}
