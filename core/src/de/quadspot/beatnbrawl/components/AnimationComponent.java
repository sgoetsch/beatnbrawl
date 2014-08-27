package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by goetsch on 13.08.14.
 */
public class AnimationComponent extends Component {
    TextureAtlas textureAtlas;
    Animation walkRightAnimation;
    Animation walkLeftAnimation;
    Animation jumpLeftAnimation;
    Animation jumpRightAnimation;
    Animation standRightAnimation;
    Animation standLeftAnimation;
    Animation currentAnimation;
    int k;
    private final Animation attackLeftAnimation;
    private final Animation attackRightAnimation;


    public AnimationComponent(String string) {
        this.textureAtlas = new TextureAtlas(string);

      //  TextureRegion[] walkRightFrames = textureAtlas.findRegions("walk");
        walkRightAnimation = new Animation(0.15f, (textureAtlas.findRegions("walk")));
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        standRightAnimation = new Animation(0.15f, (textureAtlas.findRegions("stand")));
        standRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        jumpRightAnimation = new Animation(0.15f, (textureAtlas.findRegions("jump")));
        jumpRightAnimation.setPlayMode(Animation.PlayMode.LOOP);


        walkLeftAnimation = flip(0.15f, walkRightAnimation);     
        walkLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        
        jumpLeftAnimation = flip(0.15f, jumpRightAnimation);
        jumpLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        attackRightAnimation = new Animation(0.15f, (textureAtlas.findRegions("hit")));
        attackRightAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        
        attackLeftAnimation = flip(0.15f, attackRightAnimation);
        attackLeftAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        


        currentAnimation = standRightAnimation; //Commenting this leads to: java.lang.NullPointerException
    }


    
    private Animation flip(float f, Animation sourceAnimation){
        TextureRegion[] destFrames = new TextureRegion[sourceAnimation.getKeyFrames().length];
        k=0;
        for (TextureRegion i : sourceAnimation.getKeyFrames()){
            destFrames[k] = new TextureRegion(i);
            destFrames[k].flip(true, false);
            k++;
        }
        Animation destAnimation = new Animation(f, destFrames);
        
        return destAnimation;
    }

    public Animation getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public Animation getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public Animation getJumpLeftAnimation() {
        return jumpLeftAnimation;
    }

    public Animation getJumpRightAnimation() {
        return jumpRightAnimation;
    }

    public Animation getStandRightAnimation() {
        return standRightAnimation;
    }

    public Animation getStandLeftAnimation() {
        return standLeftAnimation;
    }
    
    public Animation getAttackLeftAnimation() {
        return attackLeftAnimation;
    }

    public Animation getAttackRightAnimation() {
        return attackRightAnimation;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public int getWidth(float elapsedTime) {
        return currentAnimation.getKeyFrame(elapsedTime).getRegionWidth();
    }

    public int getHeight(float elapsedTime) {
        return currentAnimation.getKeyFrame(elapsedTime).getRegionHeight();
    }
}
