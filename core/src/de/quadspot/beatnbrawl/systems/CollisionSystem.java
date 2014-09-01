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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Bits;
import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.MovementComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;
import de.quadspot.beatnbrawl.components.StateComponent;

/**
 *
 * @author herfi
 */
public class CollisionSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> entities2;
    private Entity mapEntity;

    @Override
    public boolean checkProcessing() {
        return super.checkProcessing(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, CollisionComponent.class, StateComponent.class), new Bits(), new Bits()));
        //entities2 = entities;
        mapEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(MapComponent.class), new Bits(), new Bits())).first();

        ComponentMapper <PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper <AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);
        ComponentMapper <CollisionComponent> ccm = ComponentMapper.getFor(CollisionComponent.class);
        ComponentMapper<MapComponent> mapcm = ComponentMapper.getFor(MapComponent.class);


        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);

            ccm.get(entity).getCollidingBody().set(pcm.get(entity).getPosition(), acm.get(entity).getWidth(0)*mapcm.get(mapEntity).getMapFactor(), acm.get(entity).getHeight(0)*mapcm.get(mapEntity).getMapFactor());
            ccm.get(entity).setGround(mapcm.get(mapEntity).getGroundBody());
            //System.out.println("Aktuelle Pos:"+pcm.get(entity).getPosition()+"     x:"+ccm.get(entity).getCollidingBody().getBoundingBox().x + "   y:" +ccm.get(entity).getCollidingBody().getBoundingBox().y);


//            ccm.get(entity).getBoundingBox().set(pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y, acm.get(entity).getWidth(elapsedTime), acm.get(entity).getHeight(elapsedTime));
//            ccm.get(entity).getCollisionBox().set(pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y, acm.get(entity).getWidth(elapsedTime), acm.get(entity).getHeight(elapsedTime)/4);
//            
//            ccm.get(entity).getSensorLeft().set(pcm.get(entity).getPosition().x, 
//                                                (pcm.get(entity).getPosition().y+acm.get(entity).getHeight(elapsedTime))/2-ccm.get(entity).getSensorLeft().getHeight()/2, 
//                                                ccm.get(entity).getSensorLeft().getWidth(), 
//                                                ccm.get(entity).getSensorLeft().getHeight());
//            
//            ccm.get(entity).getSensorRight().set(pcm.get(entity).getPosition().x+acm.get(entity).getWidth(elapsedTime)-ccm.get(entity).getSensorLeft().getWidth(), 
//                                                 (pcm.get(entity).getPosition().y+acm.get(entity).getHeight(elapsedTime))/2-ccm.get(entity).getSensorLeft().getHeight(), 
//                                                 ccm.get(entity).getSensorLeft().getWidth(), 
//                                                 ccm.get(entity).getSensorLeft().getHeight());
//            
//            ccm.get(entity).getSensorTop().set((pcm.get(entity).getPosition().x+acm.get(entity).getWidth(elapsedTime))/2-ccm.get(entity).getSensorLeft().getWidth()/2, 
//                                               pcm.get(entity).getPosition().y+acm.get(entity).getHeight(elapsedTime)-ccm.get(entity).getSensorLeft().getHeight(), 
//                                               ccm.get(entity).getSensorLeft().getWidth(), 
//                                               ccm.get(entity).getSensorLeft().getHeight());
//            
//            ccm.get(entity).getSensorBottom().set((pcm.get(entity).getPosition().x+acm.get(entity).getWidth(elapsedTime))/2-ccm.get(entity).getSensorLeft().getWidth()/2, 
//                                                  pcm.get(entity).getPosition().y, 
//                                                  ccm.get(entity).getSensorLeft().getWidth(), 
//                                                  ccm.get(entity).getSensorLeft().getHeight());
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(float deltaTime) {
        ComponentMapper <PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        //ComponentMapper <AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);
        ComponentMapper <CollisionComponent> ccm = ComponentMapper.getFor(CollisionComponent.class);
        //ComponentMapper<MovementComponent> mcm = ComponentMapper.getFor(MovementComponent.class);
        ComponentMapper <StateComponent> scm = ComponentMapper.getFor(StateComponent.class);





        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);

            ccm.get(entity).getCollidingBody().set(pcm.get(entity).getPosition());

            if (ccm.get(entity).isLeftOfGround()) {
                pcm.get(entity).getPosition().set(pcm.get(entity).getOldPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getPosition().z);
                //mcm.get(entity).getVelocity().x=0;
                System.out.println("is left of ground");
            }
            if (ccm.get(entity).isRightOfGround()) {
                pcm.get(entity).getPosition().set(pcm.get(entity).getOldPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getPosition().z);
                //mcm.get(entity).getVelocity().x=0;
                System.out.println("is right of ground");
            }
            if (ccm.get(entity).isTopOfGround()) {
                pcm.get(entity).getPosition().set(pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getOldPosition().z);
                //mcm.get(entity).getVelocity().y=0;
                System.out.println("is top of ground");
            }
            if (ccm.get(entity).isBottomOfGround()) {
                pcm.get(entity).getPosition().set(pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getOldPosition().z);
                //mcm.get(entity).getVelocity().y=0;
                System.out.println("is bottom of ground");
            }
            for(int k = 0; k < entities.size(); ++k){
                Entity entity2 = entities.get(k);
                // Prevent Comparison with same object
                if ( (pcm.get(entity).getPosition().x != pcm.get(entity2).getPosition().x   ) ){


                    /*System.out.println(ccm.get(entity2).getCollidingBody().getCollisionBox().overlaps(ccm.get(entity).getCollidingBody().getCollisionBox())+"-"
                            +ccm.get(entity).getCollidingBody().getBoundingBox().overlaps(ccm.get(entity2).getCollidingBody().getAttackBoxRight())+"-"
                            +scm.get(entity2).getState().equals(StateComponent.State.ATTACK_RIGHT)+"-"
                            +(scm.get(entity2).getState().equals(StateComponent.State.ATTACK_LEFT)) + "->"+ (ccm.get(entity2).getCollidingBody().getCollisionBox().overlaps(ccm.get(entity).getCollidingBody().getCollisionBox())
                            && (ccm.get(entity).getCollidingBody().getBoundingBox().overlaps(ccm.get(entity2).getCollidingBody().getAttackBoxRight()))
                            && (scm.get(entity2).getState().equals(StateComponent.State.ATTACK_RIGHT)
                            || (scm.get(entity2).getState().equals(StateComponent.State.ATTACK_LEFT)))) + " " + entity);
*/
                    ccm.get(entity).setCollidingRight(ccm.get(entity2).getCollidingBody().getCollisionBox().overlaps(ccm.get(entity).getCollidingBody().getCollisionBox())
                                                    && (ccm.get(entity).getCollidingBody().getBoundingBox().overlaps(ccm.get(entity2).getCollidingBody().getAttackBoxRight()))
                                                    && (scm.get(entity2).getState().equals(StateComponent.State.ATTACK_RIGHT)
                                                     || (scm.get(entity2).getState().equals(StateComponent.State.ATTACK_LEFT))));

                    // TODO: Prevent enemy vs. enemy collisions!

                    ccm.get(entity).setCollidingLeft(ccm.get(entity2).getCollidingBody().getCollisionBox().overlaps(ccm.get(entity).getCollidingBody().getCollisionBox())&&
                                                    (ccm.get(entity).getCollidingBody().getBoundingBox().overlaps(ccm.get(entity2).getCollidingBody().getAttackBoxLeft())) &&
                                                    (scm.get(entity2).getState().equals(StateComponent.State.ATTACK_RIGHT)
                                                    || (scm.get(entity2).getState().equals(StateComponent.State.ATTACK_LEFT))));
                }
/*                    System.out.println(acm.get(entities.first()).getCurrentAnimation().isAnimationFinished(acm.get(entities.first()).getStateTime()) + "  "+mcm.get(entities.first()).getPrevState()+"  "+mcm.get(entities.first()).getState()+ "  " +  acm.get(entities.first()).getStateTime());
                    if ((acm.get(entities.first()).getCurrentAnimation().isAnimationFinished(acm.get(entities.first()).getStateTime())) && (mcm.get(entity).getState().equals(MovementComponent.State.ATTACK_RIGHT))) {
                        mcm.get(entities.first()).setState(mcm.get(entities.first()).getPrevState());
                        System.out.println("Setback!");
                    }
                    ccm.get(entities.first()).getCollidingBody().attackRight(0);
                        ccm.get(entities.first()).getCollidingBody().attackLeft(0);
                    
                   */
            }
        }

    }









}
