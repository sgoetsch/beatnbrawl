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
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, AnimationComponent.class, CollisionComponent.class), new Bits(), new Bits()));
        entities2 = entities;
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
        ComponentMapper <AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);
        ComponentMapper <CollisionComponent> ccm = ComponentMapper.getFor(CollisionComponent.class);
        ComponentMapper<MovementComponent> mcm = ComponentMapper.getFor(MovementComponent.class);



                    
        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            
            
            ccm.get(entity).getCollidingBody().set(pcm.get(entity).getPosition());
            //System.out.println("Aktuelle Pos:"+pcm.get(entity).getPosition()+"     x:"+ccm.get(entity).getCollidingBody().getBoundingBox().x + "   y:" +ccm.get(entity).getCollidingBody().getBoundingBox().y);
            //System.out.println("Collision"+k++);
            
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
                
                if ( (pcm.get(entity).getPosition().x != pcm.get(entity2).getPosition().x   ) ){
//                    System.out.println("-----------------");
//                    System.out.println("entity1: x:"+ccm.get(entity).getCollidingBody().getBoundingBox().x+"                 y:"+ccm.get(entity).getCollidingBody().getBoundingBox().y+"("+i+")("+k+")");
//                    System.out.println("entity2: x:"+ccm.get(entity2).getCollidingBody().getCollisionBox().x+"                  y:"+ccm.get(entity2).getCollidingBody().getCollisionBox().x+"("+i+")("+k+")");
                    //System.out.println(Intersector.overlaps(ccm.get(entity).getCollidingBody().getBoundingBox(), ccm.get(entity2).getCollidingBody().getBoundingBox()));
                    //System.out.println(doOverlap(ccm.get(entity).getCollidingBody().getBoundingBox(), ccm.get(entity2).getCollidingBody().getBoundingBox()));
                    //System.out.println("rect1:"+ccm.get(entity).getCollidingBody().getBoundingBox()+"     rect2:"+ccm.get(entity2).getCollidingBody().getBoundingBox());
                    
                    if (ccm.get(entity2).getCollidingBody().getCollisionBox().overlaps(ccm.get(entity).getCollidingBody().getCollisionBox())&&
                        ccm.get(entity2).getCollidingBody().getBoundingBox().overlaps(ccm.get(entity).getCollidingBody().getAttackBoxRight())&&
                        mcm.get(entity).getState().equals(MovementComponent.State.ATTACK_RIGHT)){
                        //pcm.get(entity).getPosition().set(pcm.get(entity).getOldPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getPosition().z);
                        System.out.println("Hit Right");
                        mcm.get(entities.first()).setState(mcm.get(entities.first()).getPrevState());
                        ccm.get(entities.first()).getCollidingBody().attackRight(0);
                        
                        
                    }
                    
                    if (ccm.get(entity2).getCollidingBody().getCollisionBox().overlaps(ccm.get(entity).getCollidingBody().getCollisionBox())&&
                        ccm.get(entity2).getCollidingBody().getBoundingBox().overlaps(ccm.get(entity).getCollidingBody().getAttackBoxLeft())&&
                        mcm.get(entity).getState().equals(MovementComponent.State.ATTACK_LEFT)){
                        //pcm.get(entity).getPosition().set(pcm.get(entity).getOldPosition().x, pcm.get(entity).getPosition().y, pcm.get(entity).getPosition().z);
                        System.out.println("Hit Left");
                        mcm.get(entities.first()).setState(mcm.get(entities.first()).getPrevState());
                        ccm.get(entities.first()).getCollidingBody().attackLeft(0);
                    }
                        ccm.get(entities.first()).getCollidingBody().attackRight(0);
                        ccm.get(entities.first()).getCollidingBody().attackLeft(0);
                    
                   
                }
            }

        }
        
    }

    

    
    
    
    
}
