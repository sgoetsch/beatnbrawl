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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Bits;
import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;

/**
 *
 * @author herfi
 */
public class CollisionSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private float elapsedTime;
    private Entity mapEntity;

    @Override
    public boolean checkProcessing() {
        return super.checkProcessing(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, AnimationComponent.class, CollisionComponent.class), new Bits(), new Bits()));
        mapEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(MapComponent.class), new Bits(), new Bits())).first();
        
        ComponentMapper <PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper <RenderComponent> rcm = ComponentMapper.getFor(RenderComponent.class);
        ComponentMapper <AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);
        ComponentMapper <CollisionComponent> ccm = ComponentMapper.getFor(CollisionComponent.class);
        ComponentMapper<MapComponent> mapcm = ComponentMapper.getFor(MapComponent.class);

        
        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            
            ccm.get(entity).getCollidingBody().set(pcm.get(entity).getPosition(), acm.get(entity).getWidth(elapsedTime), acm.get(entity).getHeight(elapsedTime));
            ccm.get(entity).setGround(mapcm.get(mapEntity).getGroundBody());

            
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
        elapsedTime += deltaTime;
       
        ComponentMapper <PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper <RenderComponent> rcm = ComponentMapper.getFor(RenderComponent.class);
        ComponentMapper <AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);
        ComponentMapper <CollisionComponent> ccm = ComponentMapper.getFor(CollisionComponent.class);

        
        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            
            
            ccm.get(entity).getCollidingBody().set(pcm.get(entity).getPosition(), acm.get(entity).getWidth(elapsedTime), acm.get(entity).getHeight(elapsedTime));

        }
        
    }

    

    
    
    
    
}
