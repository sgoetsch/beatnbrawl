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
import de.quadspot.beatnbrawl.components.SoundComponent;
import de.quadspot.beatnbrawl.components.StateComponent;

/**
 * Created by goetsch on 15.08.14.
 */
public class SoundSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(SoundComponent.class, StateComponent.class), new Bits(), new Bits()));
    }


    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }


    @Override
    public void update(float deltaTime) {
        ComponentMapper<StateComponent> scm = ComponentMapper.getFor(StateComponent.class);
        ComponentMapper<SoundComponent> sndcm = ComponentMapper.getFor(SoundComponent.class);
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            if (scm.get(entity).getPrevState() != scm.get(entity).getState() ){

                switch (scm.get(entity).getState()) {

                    case WALK_LEFT:
                        break;
                    case WALK_RIGHT:
                        break;
                    case JUMP_LEFT:
                        break;
                    case JUMP_RIGHT:
                        break;
                    case STAND_LEFT:
                        break;
                    case STAND_RIGHT:
                        break;
                    case ATTACK_LEFT:
                        //System.out.println("play Sound"+scm.get(entity).getPrevState()+scm.get(entity).getState());
                        sndcm.get(entity).getSound_hit().play();
                        break;
                    case ATTACK_RIGHT:
                        //System.out.println("play Sound"+scm.get(entity).getPrevState()+scm.get(entity).getState());
                        sndcm.get(entity).getSound_hit().play();
                        break;
                    case DEAD_LEFT:
                        break;
                    case DEAD_RIGHT:
                        break;
                }
            }
        }
    }
}
