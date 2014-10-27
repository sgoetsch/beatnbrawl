/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.quadspot.beatnbrawl;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author herfi
 */
public class CollidingBody {
    
    private float sensorWidth=1;
    private float sensorHeight =1;
    private Rectangle boundingBox;
    private Rectangle sensorLeft;
    private Rectangle sensorRight;
    private Rectangle sensorTop;
    private Rectangle sensorBottom;
    private Rectangle collisionBox;
    private Rectangle attackBoxLeft;
    private Rectangle attackBoxRight;
    private float x;
    private float y;
    private float width;
    private float height;
    private Vector2 position = new Vector2(0,0);
    private float prvRangeRight;
    private float prvRangeLeft;
    private float attackRangeLeft;
    

    public CollidingBody(float x, float y, float width, float height) {
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.position.set(x, y);

        boundingBox = new Rectangle(x, y, width, height);
        collisionBox = new Rectangle(x, y, width, height /4);
        attackBoxLeft = new Rectangle();

        attackBoxRight = new Rectangle();

        sensorLeft = new Rectangle(x,(y+ height)/2- sensorHeight /2,sensorWidth, sensorHeight);
        sensorRight = new Rectangle(x+width-sensorWidth,(y+ height)/2- sensorHeight /2,sensorWidth, sensorHeight);
        sensorTop = new Rectangle((x+width)/2-sensorWidth/2,y+ height - sensorHeight,sensorWidth, sensorHeight);
        sensorBottom = new Rectangle((x+width)/2-sensorWidth/2, y, sensorWidth, sensorHeight);
    }

    public float getSensorWidth() {
        return sensorWidth;
    }

    public float getSensorHeight() {
        return sensorHeight;
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

    public Rectangle getAttackBoxLeft() {
        return attackBoxLeft;
    }
    
    public Rectangle getAttackBoxRight() {
        return attackBoxRight;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2 getPosition() {
        return position;
    }
    
    public void set(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setRecs();
    }
    
    public void set(Vector3 position, float width, float height){
        this.position.set(new Vector2(position.x,position.z));
        this.x = this.position.x;
        this.y = this.position.y;
        this.width = width;
        this.height = height;
        boundingBox.setSize(width, height);
        collisionBox.setSize(width, height/4);
        attackBoxLeft.setSize(0, height);
        attackBoxRight.setSize(0, height);
        setRecs();
    }

    public void set(Vector3 position){
        this.position.set(new Vector2(position.x,position.z));
        this.x = this.position.x;
        this.y = this.position.y;
        setRecs();
    }

    public void attackLeft(float range){
        attackBoxLeft.setSize(range, attackBoxLeft.height);
        this.attackRangeLeft = range;
        setRecs();
        
        if (range == 0){
            collisionBox.setSize(width - prvRangeLeft, collisionBox.height);
            //collisionBox.setPosition(position.x + prvRangeLeft,collisionBox.y);
            this.prvRangeLeft = 0;
            //this.attackRangeLeft = 0;
        }else{
            this.prvRangeLeft = range;
            
            
            //collisionBox.setPosition(collisionBox.x - range,collisionBox.y);
            //float tmp = collisionBox.getWidth()
            collisionBox.setSize(width + range, collisionBox.height);
            
            
        }
    }

    public void attackRight(float range){
        
        attackBoxRight.setSize(range, attackBoxRight.height);
        if (range == 0){
            collisionBox.setSize(width, collisionBox.height);
            this.prvRangeRight = 0;
        }else{
            this.prvRangeRight = range;
            collisionBox.setSize(width + range, collisionBox.height);
        }
            
    }

    private void setRecs(){
        
        //boundingBox.set(x, y, width, height);
        boundingBox.setPosition(position);
        //collisionBox.set(x, y, width, height/4);
        collisionBox.setPosition(position.x-attackRangeLeft, position.y - collisionBox.height/2);
        attackBoxRight.setPosition(position.x + width, position.y);
        attackBoxLeft.setPosition(position.x-attackRangeLeft, position.y);

        //sensorLeft.set(x,(y+height)/2-sensorHeight/2,sensorWidth,sensorHeight);
        sensorLeft.setPosition(collisionBox.x, (collisionBox.y+ collisionBox.height)/2- sensorHeight /2);
        
        //sensorRight.set(x+width-sensorWidth,(y+height)/2-sensorHeight/2,sensorWidth,sensorHeight);
        sensorRight.setPosition(collisionBox.x+width-sensorWidth,(collisionBox.y+ collisionBox.height)/2- sensorHeight /2);

        //sensorTop.set((x+width)/2-sensorWidth/2,y+height-sensorHeight,sensorWidth,sensorHeight);
        sensorTop.setPosition((collisionBox.x+width)/2-sensorWidth/2,collisionBox.y+ collisionBox.height - sensorHeight);
        
        //sensorBottom.set((x+width)/2-sensorWidth/2,y,sensorWidth,sensorHeight);
        sensorBottom.setPosition((collisionBox.x+width)/2-sensorWidth/2,collisionBox.y);
    }
}
