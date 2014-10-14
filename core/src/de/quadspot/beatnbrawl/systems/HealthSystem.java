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
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.HealthComponent;
import de.quadspot.beatnbrawl.components.StateComponent;

/**
 * Created by goetsch on 14.10.14.
 */
public class HealthSystem extends EntitySystem {
    private ComponentMapper<CollisionComponent> ccm;
    private ComponentMapper<HealthComponent> hcm;
    private ComponentMapper<StateComponent> scm;
    private ComponentMapper<AnimationComponent> acm;
    private ImmutableArray<Entity> entities;

    public HealthSystem() {
    }

    public HealthSystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        ccm = ComponentMapper.getFor(CollisionComponent.class);
        hcm = ComponentMapper.getFor(HealthComponent.class);
        scm = ComponentMapper.getFor(StateComponent.class);
        acm =ComponentMapper.getFor(AnimationComponent.class);
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(CollisionComponent.class, HealthComponent.class), new Bits(), new Bits()));
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

//            for (int j=0; j < ccm.get(entity).getHit().size(); ++j) {
//
//            }

            while(ccm.get(entity).checkHit()) {
                hcm.get(entity).countHit(ccm.get(entity).popHit()[0]);
                System.out.println("HIER");
            }
        }
    }

    @Override
    public boolean checkProcessing() {
        return super.checkProcessing();
    }

    @Override
    public void setProcessing(boolean processing) {
        super.setProcessing(processing);
    }
}
