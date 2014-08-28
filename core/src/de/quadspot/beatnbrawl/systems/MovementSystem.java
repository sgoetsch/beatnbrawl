package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Bits;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.MapComponent;

import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;

/**
 * Created by goetsch on 05.08.14.
 */
public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private Entity mapEntity;
    Vector3 oldPos = new Vector3(0, 0, 0);
    int k=0;
    public MovementSystem() {

    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(PositionComponent.class, MovementComponent.class, CollisionComponent.class));
        mapEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(MapComponent.class), new Bits(), new Bits())).first();

    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {

        ComponentMapper<PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper<MovementComponent> mcm = ComponentMapper.getFor(MovementComponent.class);
        ComponentMapper <CollisionComponent> ccm = ComponentMapper.getFor(CollisionComponent.class);

        ComponentMapper<MapComponent> mapcm = ComponentMapper.getFor(MapComponent.class);
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

            //System.out.println(mapcm.get(mapEntity).getGroundBody().getX()+","+mapcm.get(mapEntity).getGroundBody().getY()+","+(mapcm.get(mapEntity).getGroundBody().getX()+mapcm.get(mapEntity).getGroundBody().getWidth())+","+(mapcm.get(mapEntity).getGroundBody().getY()+mapcm.get(mapEntity).getGroundBody().getHeight()));
            //System.out.println(!mapcm.get(mapEntity).isOnGround((int) pcm.get(entity).getPosition().x, (int) pcm.get(entity).getPosition().z));
            //System.out.println("x:"+(int) pcm.get(entity).getPosition().x +"y:"+ (int) pcm.get(entity).getPosition().y+"z:"+ (int) pcm.get(entity).getPosition().z);

            pcm.get(entity).getOldPosition().set(pcm.get(entity).getPosition().cpy());



            pcm.get(entity).getPosition().add(mcm.get(entity).getVelocity().cpy().scl(deltaTime));
            //System.out.println("NEU:" + pcm.get(entity).getPosition() + "     ALT:" + oldPos + "       Velocity:" + mcm.get(entity).getVelocity());
            //System.out.println("NEU:" + ccm.get(entity).getCollidingBody().getPosition());
            if (!((mcm.get(entity).getState().equals(MovementComponent.State.ATTACK_LEFT)) || (mcm.get(entity).getState().equals(MovementComponent.State.ATTACK_RIGHT)))) {

                if (mcm.get(entity).getVelocity().x > 0) {
                    mcm.get(entity).setState(MovementComponent.State.WALK_RIGHT);

                } else if (mcm.get(entity).getVelocity().x < 0) {
                    mcm.get(entity).setState(MovementComponent.State.WALK_LEFT);

                } else if (mcm.get(entity).getVelocity().y > 0) {

                } else if (mcm.get(entity).getVelocity().y < 0) {

                } else if (mcm.get(entity).getVelocity().z > 0) {

                } else if (mcm.get(entity).getVelocity().z < 0) {

                } else {
                    if (mcm.get(entity).getPrevState().dir().equals("RIGHT")) {
                        mcm.get(entity).setState(MovementComponent.State.STAND_RIGHT);
                    } else {
                        mcm.get(entity).setState(MovementComponent.State.STAND_LEFT);
                    }

                }
            }

            // of ground things
            //System.out.println(ccm.get(entity).isLeftOfGround());
            //System.out.println("x:"+ccm.get(entity).getCollidingBody().getBoundingBox().x + "   y:" +ccm.get(entity).getCollidingBody().getBoundingBox().y);

        }

    }
}

