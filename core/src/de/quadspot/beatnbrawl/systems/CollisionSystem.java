/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Bits;

import de.quadspot.beatnbrawl.components.AIComponent;
import de.quadspot.beatnbrawl.components.ActionComponent;
import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.StateComponent;

/**
 *
 * @author herfi
 */
public class CollisionSystem extends EntitySystem{
    private ImmutableArray<Entity> enemyEntities;
    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> allEntities;
    ComponentMapper <PositionComponent> pcm;
    ComponentMapper <AnimationComponent> acm;
    ComponentMapper <CollisionComponent> ccm;
    ComponentMapper<MapComponent> mapcm;
    ComponentMapper <ActionComponent> actcm;
    ComponentMapper <StateComponent> scm;


    private Entity mapEntity;

    @Override
    public boolean checkProcessing() {
        return super.checkProcessing(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addedToEngine(Engine engine) {
        enemyEntities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, CollisionComponent.class, StateComponent.class, ActionComponent.class, AIComponent.class), new Bits(), new Bits()));
        playerEntities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, CollisionComponent.class, StateComponent.class, ActionComponent.class, InputComponent.class), new Bits(), new Bits()));
        allEntities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, CollisionComponent.class, StateComponent.class, ActionComponent.class), new Bits(), new Bits()));
        mapEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(MapComponent.class), new Bits(), new Bits())).first();

        pcm  = ComponentMapper.getFor(PositionComponent.class);
        acm = ComponentMapper.getFor(AnimationComponent.class);
        ccm = ComponentMapper.getFor(CollisionComponent.class);
        mapcm = ComponentMapper.getFor(MapComponent.class);
        actcm = ComponentMapper.getFor(ActionComponent.class);
        scm = ComponentMapper.getFor(StateComponent.class);



        for(int i = 0; i < allEntities.size(); ++i){
            Entity entity = allEntities.get(i);

            ccm.get(entity).getCollidingBody().set(pcm.get(entity).getPosition(), acm.get(entity).getWidth(0)*mapcm.get(mapEntity).getMapFactor(), acm.get(entity).getHeight(0)*mapcm.get(mapEntity).getMapFactor());
            ccm.get(entity).setGround(mapcm.get(mapEntity).getGroundBody());
            //System.out.println("Aktuelle Pos:"+pcm.get(entity).getPosition()+"     x:"+ccm.get(entity).getCollidingBody().getBoundingBox().x + "   y:" +ccm.get(entity).getCollidingBody().getBoundingBox().y);
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(float deltaTime) {
// entity -> der potenziell geschlagene, entity2 -> der Angreifer
        for (int i = 0; i < allEntities.size(); ++i) {
            Entity entity = allEntities.get(i);

            ccm.get(entity).getCollidingBody().set(pcm.get(entity).getPosition());


            if (ccm.get(entity).isLeftOfGround()) {
                pcm.get(entity).getPosition().set(pcm.get(entity).getOldPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getPosition().z);
                //mcm.get(entity).getVelocity().x=0;
                //System.out.println("is left of ground");
            }
            if (ccm.get(entity).isRightOfGround()) {
                pcm.get(entity).getPosition().set(pcm.get(entity).getOldPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getPosition().z);
                //mcm.get(entity).getVelocity().x=0;
                //System.out.println("is right of ground");
            }
            if (ccm.get(entity).isTopOfGround()) {
                pcm.get(entity).getPosition().set(pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getOldPosition().z);
                //mcm.get(entity).getVelocity().y=0;
                //System.out.println("is top of ground");
            }
            if (ccm.get(entity).isBottomOfGround()) {
                pcm.get(entity).getPosition().set(pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getOldPosition().z);
                //mcm.get(entity).getVelocity().y=0;
                //System.out.println("is bottom of ground");
            }
        }

        for (int l = 0; l < playerEntities.size(); ++l) {
            Entity entity = playerEntities.get(l);
            {
                for (int k = 0; k < enemyEntities.size(); ++k) {
                    Entity entity2 = enemyEntities.get(k);
                    whohit(entity, entity2);
                }
            }
        }
        for (int l = 0; l < enemyEntities.size(); ++l) {
            Entity entity = enemyEntities.get(l);
            {
                for (int k = 0; k < playerEntities.size(); ++k) {
                    Entity entity2 = playerEntities.get(k);
                    whohit(entity, entity2);
                }
            }
        }
    }
    private void whohit(Entity entity, Entity entity2) {
        if ((pcm.get(entity).getPosition().x != pcm.get(entity2).getPosition().x)) { //TODO entity-IDs vergleichen

            if (actcm.get(entity2).isAttacking() && scm.get(entity2).getState().equals(StateComponent.State.ATTACK_LEFT)) {
                ccm.get(entity2).getCollidingBody().attackLeft(100);
            } else if (actcm.get(entity2).isAttacking() && scm.get(entity2).getState().equals(StateComponent.State.ATTACK_RIGHT)) {
                ccm.get(entity2).getCollidingBody().attackRight(100);
            }


            boolean hitRight = (ccm.get(entity2).getCollidingBody().getCollisionBox().overlaps(ccm.get(entity).getCollidingBody().getCollisionBox())
                    && (ccm.get(entity).getCollidingBody().getBoundingBox().overlaps(ccm.get(entity2).getCollidingBody().getAttackBoxRight()))
                    && actcm.get(entity2).isAttacking() && scm.get(entity2).getState().equals(StateComponent.State.ATTACK_RIGHT));
            ccm.get(entity).setCollidingRight(hitRight);

            // TODO: Prevent enemy vs. enemy collisions!

            boolean hitLeft = (ccm.get(entity2).getCollidingBody().getCollisionBox().overlaps(ccm.get(entity).getCollidingBody().getCollisionBox()) &&
                    (ccm.get(entity).getCollidingBody().getBoundingBox().overlaps(ccm.get(entity2).getCollidingBody().getAttackBoxLeft())) &&
                    actcm.get(entity2).isAttacking() && scm.get(entity2).getState().equals(StateComponent.State.ATTACK_LEFT));
            ccm.get(entity).setCollidingLeft(hitLeft);


            if (hitLeft || hitRight) {
                ccm.get(entity).pushHit(10, entity2.getId());
//                ccm.get(entity2).getCollidingBody().attackLeft(0);
//                ccm.get(entity2).getCollidingBody().attackRight(0);
//                actcm.get(entity2).setAttacking(false);
                System.out.println("HIT!!!!");
            }
        }
    }
}
