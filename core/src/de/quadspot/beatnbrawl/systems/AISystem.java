package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Bits;

import de.quadspot.beatnbrawl.beatnbrawl;
import de.quadspot.beatnbrawl.components.AIComponent;
import de.quadspot.beatnbrawl.components.ActionComponent;
import de.quadspot.beatnbrawl.components.BossComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.StateComponent;
import de.quadspot.beatnbrawl.screens.GameScreen;

/**
 * Created by goetsch on 02.09.14.
 */
public class AISystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> bossEntities;
    private Entity player;
    private GameScreen gameScreen;
    ComponentMapper<PositionComponent> pcm;
    ComponentMapper<AIComponent> aicm;
    ComponentMapper<MovementComponent> mcm;
    ComponentMapper<ActionComponent> actcm;
    ComponentMapper<StateComponent> scm;


    /**
     * Default constructor that will initialise an EntitySystem with priority 0.
     */
    public AISystem(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Initialises the EntitySystem with the priority specified.
     *
     * @param priority The priority to execute this system with (lower means higher priority).
     */
    public AISystem(int priority, GameScreen gameScreen) {
        super(priority);
        this.gameScreen = gameScreen;
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
        bossEntities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(BossComponent.class),
                new Bits(), new Bits()));
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
        pcm = ComponentMapper.getFor(PositionComponent.class);
        aicm = ComponentMapper.getFor(AIComponent.class);
        actcm = ComponentMapper.getFor(ActionComponent.class);
        mcm = ComponentMapper.getFor(MovementComponent.class);
        scm = ComponentMapper.getFor(StateComponent.class);


        for(int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

            if (!(scm.get(entity).getState().equals(StateComponent.State.DEAD_RIGHT) || scm.get(entity).getState().equals(StateComponent.State.DEAD_LEFT))) {
                if (aicm.get(entity).getDecisionTime(deltaTime)) {
            /*
            if (Gegner in Reichweite (distanz-vektor))
                dann das was unten steht
*/
                    if (!(scm.get(player).getState().equals(StateComponent.State.DEAD_LEFT) || scm.get(player).getState().equals(StateComponent.State.DEAD_RIGHT))) {
                        int rand = MathUtils.random(0, 2);
                        if (rand == 0) { // 1/3 Chance etwas zu tun, 2/3 idle
                            // falls distanz zu player <= 250 und player nicht in attack state
                            if (distance(pcm.get(player).getPosition().cpy(), pcm.get(entity).getPosition().cpy()) <= 250 &&
                                    !(scm.get(entity).getState().equals(StateComponent.State.ATTACK_RIGHT) ||
                                            scm.get(entity).getState().equals(StateComponent.State.ATTACK_RIGHT))) {
                                mcm.get(entity).getVelocity().setZero(); //stehen bleiben
                                actcm.get(entity).setAttacking(true); //angreifen
                            } else if (distance(pcm.get(player).getPosition().cpy(), pcm.get(entity).getPosition().cpy()) <= 900 && distance(pcm.get(player).getPosition().cpy(), pcm.get(entity).getPosition().cpy()) > 250) {
                                //System.out.println(distance(pcm.get(player).getPosition().cpy(), pcm.get(entity).getPosition().cpy()));
                                moveTowardsPlayer(player, entity);
                            } else
                                mcm.get(entity).getVelocity().setZero(); // falls Player-distance > 900, stehen bleiben und nichts tun
                        }
                    }
                }
            }
            else
                mcm.get(entity).getVelocity().setZero();
        }
        if (bossEntities.size()==0)
            gameScreen.setState(GameScreen.GAME_LEVEL_END);
        //System.out.println(bossEntities.size());
        for(int i = 0; i < bossEntities.size(); ++i) {
            Entity entity = bossEntities.get(i);
            if (scm.get(entity).getState().equals(StateComponent.State.DEAD_LEFT) || scm.get(entity).getState().equals(StateComponent.State.DEAD_RIGHT))
                entity.remove(BossComponent.class);
        }
    }

    private float distance (Vector3 v1, Vector3 v2){
        Vector3 rv = v1.sub(v2);
        //System.out.println(rv);
        return rv.len();
    }

    private void moveTowardsPlayer(Entity player, Entity entity){
        Vector3 pqvector = new Vector3(pcm.get(player).getPosition().x - pcm.get(entity).getPosition().x, 0, pcm.get(player).getPosition().z - pcm.get(entity).getPosition().z).nor();
        //System.out.println(pqvector);
        mcm.get(entity).getVelocity().set(pqvector.scl(mcm.get(entity).getMaxVelocity()));
    }
}
