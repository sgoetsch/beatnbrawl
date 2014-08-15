package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;

/**
 * Created by goetsch on 05.08.14.
 */
public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.getFor(PositionComponent.class, MovementComponent.class, AnimationComponent.class));
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ComponentMapper<PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper<MovementComponent> mcm = ComponentMapper.getFor(MovementComponent.class);

        pcm.get(entity).getPosition().add(mcm.get(entity).getVelocity().cpy().scl(deltaTime));

        //pcm.get(entity).getPosition().set(pcm.get(entity).getPosition().x,pcm.get(entity).getPosition().z,pcm.get(entity).getPosition().z);
    }
}
