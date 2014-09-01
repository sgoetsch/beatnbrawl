/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
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

    public CollisionComponent() {
        
        collidingBody = new CollidingBody(0, 0, 0, 0);


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
        this.isCollidingLeft = isCollidingLeft;
    }

    public boolean isCollidingRight() {
        return isCollidingRight;
    }

    public void setCollidingRight(boolean isCollidingRight) {
        this.isCollidingRight = isCollidingRight;
    }
}
