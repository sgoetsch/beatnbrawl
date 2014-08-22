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
    private float sensorHight=1;
    private Rectangle boundingBox;
    private Rectangle sensorLeft;
    private Rectangle sensorRight;
    private Rectangle sensorTop;
    private Rectangle sensorBottom;
    private Rectangle collisionBox;
    private Rectangle attackBox;
    private float x;
    private float y;
    private float width;
    private float hight;
    private Vector2 position = new Vector2(0,0);
    

    public CollidingBody(float x, float y, float width, float hight) {
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.hight = hight; 
        this.position.set(x, y);
        
        boundingBox = new Rectangle(x, y, width, hight);
        collisionBox = new Rectangle(x, y, width, hight/4);
        attackBox = new Rectangle();
            
        sensorLeft = new Rectangle(x,(y+hight)/2-sensorHight/2,sensorWidth,sensorHight);
        sensorRight = new Rectangle(x+width-sensorWidth,(y+hight)/2-sensorHight/2,sensorWidth,sensorHight);
        sensorTop = new Rectangle((x+width)/2-sensorWidth/2,y+hight-sensorHight,sensorWidth,sensorHight);
        sensorBottom = new Rectangle((x+width)/2-sensorWidth/2,y,sensorWidth,sensorHight);
    }

    public float getSensorWidth() {
        return sensorWidth;
    }

    public float getSensorHight() {
        return sensorHight;
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHight() {
        return hight;
    }

    public Vector2 getPosition() {
        return position;
    }
    
    public void set(float x, float y, float width, float hight){
        this.x = x;
        this.y = y;
        this.width = width;
        this.hight = hight;
        setRecs();
    }
    
    public void set(Vector3 position, float width, float hight){
        this.position.set(new Vector2(position.x,position.z));
        this.x = this.position.x;
        this.y = this.position.y;
        this.width = width;
        this.hight = hight;
        setRecs();
    }
    
    private void setRecs(){
        
        //boundingBox.set(x, y, width, hight);
        boundingBox.setPosition(position);
        //collisionBox.set(x, y, width, hight/4);
        collisionBox.setPosition(position);
        //attackBox.set();
            
        //sensorLeft.set(x,(y+hight)/2-sensorHight/2,sensorWidth,sensorHight);
        sensorLeft.setPosition(x, (y+hight)/2-sensorHight/2);
        
        //sensorRight.set(x+width-sensorWidth,(y+hight)/2-sensorHight/2,sensorWidth,sensorHight);
        sensorRight.setPosition(x+width-sensorWidth,(y+hight)/2-sensorHight/2);

        //sensorTop.set((x+width)/2-sensorWidth/2,y+hight-sensorHight,sensorWidth,sensorHight);
        sensorTop.setPosition((x+width)/2-sensorWidth/2,y+hight-sensorHight);
        
        //sensorBottom.set((x+width)/2-sensorWidth/2,y,sensorWidth,sensorHight);
        sensorBottom.setPosition((x+width)/2-sensorWidth/2,y);
    }
}
