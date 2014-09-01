package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Animation;

import de.quadspot.beatnbrawl.components.ActionComponent;
import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.StateComponent;

/**
 * Created by goetsch on 31.08.14.
 */
public class StateSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private ComponentMapper<MovementComponent> mcm;
    private ComponentMapper<CollisionComponent> ccm;
    private ComponentMapper<ActionComponent> actcm;
    private ComponentMapper<StateComponent> scm;
    private ComponentMapper<AnimationComponent> acm;


    /**
     * Default constructor that will initialise an EntitySystem with priority 0.
     */
    public StateSystem() {
    }

    /**
     * Initialises the EntitySystem with the priority specified.
     *
     * @param priority The priority to execute this system with (lower means higher priority).
     */
    public StateSystem(int priority) {
        super(priority);
    }

    /**
     * Called when this EntitySystem is added to an {@link com.badlogic.ashley.core.Engine}.
     *
     * @param engine The {@link com.badlogic.ashley.core.Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(MovementComponent.class, CollisionComponent.class, ActionComponent.class));
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

        mcm = ComponentMapper.getFor(MovementComponent.class);
        ccm = ComponentMapper.getFor(CollisionComponent.class);
        actcm = ComponentMapper.getFor(ActionComponent.class);
        scm = ComponentMapper.getFor(StateComponent.class);
        acm = ComponentMapper.getFor(AnimationComponent.class);

        for(int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            scm.get(entity).setStateTime(deltaTime);

            System.out.println(mcm.get(entity).isFacedRight() + "-" + ccm.get(entity).isCollidingRight()+"-"+(acm.get(entity).getCurrentAnimation().getPlayMode().equals(Animation.PlayMode.NORMAL)
                    && acm.get(entity).getCurrentAnimation().isAnimationFinished(scm.get(entity).getStateTime())
                    || acm.get(entity).getCurrentAnimation().getPlayMode().equals(Animation.PlayMode.LOOP)));

            if (acm.get(entity).getCurrentAnimation().getPlayMode().equals(Animation.PlayMode.NORMAL)
                    && acm.get(entity).getCurrentAnimation().isAnimationFinished(scm.get(entity).getStateTime())
                    || acm.get(entity).getCurrentAnimation().getPlayMode().equals(Animation.PlayMode.LOOP))
            if (mcm.get(entity).isFacedRight()) {
                if (actcm.get(entity).isAttacking()) {
                    // Attack animation rechts
                    actcm.get(entity).setAttacking(false);
                    scm.get(entity).setState(StateComponent.State.ATTACK_RIGHT);
                }
                else if  (ccm.get(entity).isCollidingLeft()) {
                    System.out.println("Right-Hit-Left");
                    // Entity wurde getroffen von Gegner der nach Links geschlagen hat -> von-vorne-getroffen Animation, nach rechts ausgerichtet
                }
                else if  (ccm.get(entity).isCollidingRight()) {
                    System.out.println("Right-Hit-Right");
                    // -> von-hinten-getroffen Animation, nach rechts ausgerichtet
                }
                else if  (mcm.get(entity).isMovingRight()) {
                    // walk right Animation
                    scm.get(entity).setState(StateComponent.State.WALK_RIGHT);
                }
                else if (mcm.get(entity).isStanding()) {
                    //stand right animation
                    scm.get(entity).setState(StateComponent.State.STAND_RIGHT);
                }
            }
            else if (mcm.get(entity).isFacedLeft()) {
                if (actcm.get(entity).isAttacking()) {
                    // Attack animation links
                    scm.get(entity).setState(StateComponent.State.ATTACK_LEFT);
                    actcm.get(entity).setAttacking(false);
                }
                else if  (ccm.get(entity).isCollidingLeft()) {
                    // Entity wurde getroffen von Gegner der nach Links geschlagen hat -> von-hinten-getroffen Animation, nach links ausgerichtet
                }
                else if  (ccm.get(entity).isCollidingRight()) {
                    // -> von-vorne-getroffen Animation, nach links ausgerichtet
                }
                else if  (mcm.get(entity).isMovingLeft()) {
                    // walk left Animation
                    scm.get(entity).setState(StateComponent.State.WALK_LEFT);
                }
                else if (mcm.get(entity).isStanding()) {
                    //stand left animation
                    scm.get(entity).setState(StateComponent.State.STAND_LEFT);
                }
            }
        }







            /*
            if(mcm.get(entities.first()).getState().equals(MovementComponent.State.STAND_RIGHT)||mcm.get(entities.first()).getState().equals(MovementComponent.State.WALK_RIGHT)){
               System.out.println("Attack right");

                mcm.get(entities.first()).setState(MovementComponent.State.ATTACK_RIGHT);
                ccm.get(entities.first()).getCollidingBody().attackRight(100);


            }else if(mcm.get(entities.first()).getState().equals(MovementComponent.State.STAND_LEFT) || mcm.get(entities.first()).getState().equals(MovementComponent.State.WALK_LEFT)){
                System.out.println("Attack left");

                mcm.get(entities.first()).setState(MovementComponent.State.ATTACK_LEFT);
                ccm.get(entities.first()).getCollidingBody().attackLeft(100);
            }*/
    }
}
