package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Bits;

import de.quadspot.beatnbrawl.components.AIComponent;
import de.quadspot.beatnbrawl.components.ActionComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.StateComponent;

/**
 * Created by goetsch on 02.09.14.
 */
public class AISystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private Entity player;

    /**
     * Default constructor that will initialise an EntitySystem with priority 0.
     */
    public AISystem() {
    }

    /**
     * Initialises the EntitySystem with the priority specified.
     *
     * @param priority The priority to execute this system with (lower means higher priority).
     */
    public AISystem(int priority) {
        super(priority);
    }

    /**
     * Called when this EntitySystem is added to an {@link com.badlogic.ashley.core.Engine}.
     *
     * @param engine The {@link com.badlogic.ashley.core.Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(AIComponent.class, CollisionComponent.class, MovementComponent.class, ActionComponent.class, StateComponent.class),
                new Bits(), new Bits()));
        player = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(InputComponent.class),
                new Bits(), new Bits())).first();
    }

    /**
     * Called when this EntitySystem is removed from an {@link com.badlogic.ashley.core.Engine}.
     *
     * @param engine The {@link com.badlogic.ashley.core.Engine} the system was removed from.
     */
    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    /**
     * The update method called every tick.
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
    @Override
    public void update(float deltaTime) {
        ComponentMapper<PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper<AIComponent> aicm = ComponentMapper.getFor(AIComponent.class);
        ComponentMapper<ActionComponent> actcm = ComponentMapper.getFor(ActionComponent.class);
        ComponentMapper<MovementComponent> mcm = ComponentMapper.getFor(MovementComponent.class);
        ComponentMapper<CollisionComponent> ccm = ComponentMapper.getFor(CollisionComponent.class);


        for(int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

            //if (aicm.get(entity).getDecisionTime(deltaTime)) {
            /*
            if (Gegner in Reichweite (distanz-vektor))
                dann das was unten steht
*/
                Vector3 pqvector = new Vector3(pcm.get(player).getPosition().x - pcm.get(entity).getPosition().x, 0, pcm.get(player).getPosition().z - pcm.get(entity).getPosition().z).nor().scl(mcm.get(entity).getMaxVelocity());
                System.out.println(pqvector);
                mcm.get(entity).getVelocity().set(pqvector.scl(mcm.get(entity).getMaxVelocity()));
            }
        //}
    }
}
