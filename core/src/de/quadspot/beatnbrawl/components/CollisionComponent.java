/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import de.quadspot.beatnbrawl.CollidingBody;

/**
 *
 * @author herfi
 */
public class CollisionComponent extends Component {
    

    private CollidingBody collidingBody;
    private Rectangle groundBody;
    private boolean isCollidingRight; // passives "has been hit" - wurde getroffen
    private boolean isCollidingLeft;
    private ArrayList<long[]> hit;
    private Hashtable<Long, Long> hits;

    public CollisionComponent() {
        
        collidingBody = new CollidingBody(0, 0, 0, 0);
        hit = new ArrayList<>();
        hits = new Hashtable<>();

    }
    
    public void setGround(Rectangle groundBody){
        this.groundBody = groundBody;
    }


    public CollidingBody getCollidingBody() {
        return collidingBody;
    }
    
    
    public boolean isLeftOfGround(){
        return !groundBody.contains(collidingBody.getSensorLeft());
    }
    
    public boolean isRightOfGround(){
        return !groundBody.contains(collidingBody.getSensorRight());
    }
    
    public boolean isTopOfGround(){
        return !groundBody.contains(collidingBody.getSensorTop());
    }
    
    public boolean isBottomOfGround(){
        return !groundBody.contains(collidingBody.getSensorBottom());
    }
    
    public boolean isOfGround(){
        return !(groundBody.contains(collidingBody.getSensorLeft()) || groundBody.contains(collidingBody.getSensorRight()) || groundBody.contains(collidingBody.getSensorTop()) || groundBody.contains(collidingBody.getSensorBottom()));
    }

    public boolean isCollidingLeft() {
        return isCollidingLeft;
    }

    public void setCollidingLeft(boolean isCollidingLeft) {
        if (isCollidingLeft) {
            this.isCollidingLeft = isCollidingLeft;
        }
    }

    public boolean isCollidingRight() {
        return isCollidingRight;
    }

    public void setCollidingRight(boolean isCollidingRight) {
        if (isCollidingRight) {
            this.isCollidingRight = isCollidingRight;
        }
    }

    public void resetColliding() {
        this.isCollidingRight = false;
        this.isCollidingLeft = false;
    }

/*    public long[] popHit() {
        long[] tmp = {-1, -1};
        return !this.hit.isEmpty() ? this.hit.get(0): tmp;
    }

    public void pushHit(long hit, long id, long count) {
        if (!(hits.put(id, count) == count)) {
            hcm.get(entity).countHit(
        }






        boolean test = false;
        if (!this.hit.isEmpty()) {
            for (int i = 0; i < this.hit.size(); i++) {
                if (this.hit.get(i)[1] == id) {
                    test=true;
                }
            }
            if (!test) {
                long[] tmp = {hit, id, count};
                this.hit.add(tmp);
            }
         }
        else {
            long[] tmp = {hit, id, count};
            this.hit.add(tmp);
        }
        //System.out.println(this.hit);
    }

    public boolean checkHit() {
        return !hit.isEmpty();
    }*/

    public ArrayList<long[]> getHit() {
        return hit;
    }

    public Long putHits(Long k, Long v) {
        Long x = hits.put(k,v);
        return (x != null ? x : -1 );
        //return hits.put(k, v);
    }

}
