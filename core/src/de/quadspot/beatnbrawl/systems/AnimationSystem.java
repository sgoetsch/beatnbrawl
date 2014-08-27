package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Bits;

import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;

/**
 * Created by goetsch on 15.08.14.
 */
public class AnimationSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(AnimationComponent.class, MovementComponent.class), new Bits(), new Bits()));
    }


    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }


    @Override
    public void update(float deltaTime) {
        ComponentMapper<MovementComponent> mcm = ComponentMapper.getFor(MovementComponent.class);
        ComponentMapper<AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);
        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            System.out.println(mcm.get(entity).getState());
            switch (mcm.get(entity).getState()){

                case WALK_LEFT:
                    acm.get(entity).setCurrentAnimation(acm.get(entity).getWalkLeftAnimation());
                    break;
                case WALK_RIGHT:
                    acm.get(entity).setCurrentAnimation(acm.get(entity).getWalkRightAnimation());
                    break;
                case JUMP_LEFT:
                    acm.get(entity).setCurrentAnimation(acm.get(entity).getJumpLeftAnimation());
                    break;
                case JUMP_RIGHT:
                    acm.get(entity).setCurrentAnimation(acm.get(entity).getJumpRightAnimation());
                    break;
                case STAND_LEFT:
                    acm.get(entity).setCurrentAnimation(acm.get(entity).getStandLeftAnimation());
                    break;
                case STAND_RIGHT:
                    acm.get(entity).setCurrentAnimation(acm.get(entity).getStandRightAnimation());
                    break;
                case ATTACK_LEFT:
                    acm.get(entity).setCurrentAnimation(acm.get(entity).getAttackRightAnimation());
                    break;
                case ATTACK_RIGHT:
                    acm.get(entity).setCurrentAnimation(acm.get(entity).getAttackRightAnimation());
                    break;
            }
        }

    }
}
