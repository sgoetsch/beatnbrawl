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
    
    private Rectangle boundingBox;
    private Rectangle sensorLeft;
    private Rectangle sensorRight;
    private Rectangle sensorTop;
    private Rectangle sensorBottom;
    private Rectangle collisionBox;
    private Rectangle attackBox;
    private CollidingBody collidingBody;
    private boolean isLeftOfGround;
    private boolean isRightOfGround;
    private boolean isTopOfGround;
    private boolean isBottomOfGround;
    private Rectangle groundBody;
    

    public CollisionComponent() {
        
        collidingBody = new CollidingBody(0, 0, 0, 0);
        
//            boundingBox = new Rectangle();
//            collisionBox = new Rectangle();
//            attackBox = new Rectangle();
//            
//            sensorLeft = new Rectangle(0, 0, 1, 1);
//            sensorRight = new Rectangle(0, 0, 1, 1);
//            sensorTop = new Rectangle(0, 0, 1, 1);
//            sensorBottom = new Rectangle(0, 0, 1, 1);

    }
    
    public void setGround(Rectangle groundBody){
        this.groundBody = groundBody;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public Rectangle getSensorLeft() {
        return sensorLeft;
    }

    public Rectangle getSensorRight() {
        return sensorRight;
    }

    public Rectangle getSensorTop() {
        return sensorTop;
    }

    public Rectangle getSensorBottom() {
        return sensorBottom;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public Rectangle getAttackBox() {
        return attackBox;
    }

    public CollidingBody getCollidingBody() {
        return collidingBody;
    }
    
    
    public boolean isLeftOfGround(){
        return isLeftOfGround;
    }
    
    public boolean isRightOfGround(){
        return isRightOfGround;
    }
    
    public boolean isTopOfGround(){
        return isTopOfGround;
    }
    
    public boolean isBottomOfGround(){
        return isBottomOfGround;
    }

    public void setIsLeftOfGround(Rectangle groundBody, int x, int y) {
        this.isLeftOfGround = groundBody.contains(x, y);
    }

    public void setIsRightOfGround(Rectangle groundBody, int x, int y) {
        this.isRightOfGround = groundBody.contains(x, y);
    }

    public void setIsTopOfGround(Rectangle groundBody, int x, int y) {
        this.isTopOfGround = groundBody.contains(x, y);
    }

    public void setIsBottomOfGround(Rectangle groundBody, int x, int y) {
        this.isBottomOfGround = groundBody.contains(x, y);
    }
    
    
}
