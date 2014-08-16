package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

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


    public AnimationComponent(String string) {
        this.textureAtlas = new TextureAtlas(string);

      //  TextureRegion[] walkRightFrames = textureAtlas.findRegions("walk");
        walkRightAnimation = new Animation(0.15f, (textureAtlas.findRegions("walk")));
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        standRightAnimation = new Animation(0.15f, (textureAtlas.findRegions("stand")));
        standRightAnimation.setPlayMode(Animation.PlayMode.LOOP);

        jumpRightAnimation = new Animation(0.15f, (textureAtlas.findRegions("jump")));
        jumpRightAnimation.setPlayMode(Animation.PlayMode.LOOP);


        // TODO: Refactor to own function
        TextureRegion[] walkLeftFrames = new TextureRegion[walkRightAnimation.getKeyFrames().length];
        k=0;
        for (TextureRegion i : walkRightAnimation.getKeyFrames()){
            walkLeftFrames[k] = new TextureRegion(i);
            walkLeftFrames[k].flip(true, false);
            k++;
        }
        walkLeftAnimation = new Animation(0.15f, walkLeftFrames);
        walkLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] jumpLeftFrames = new TextureRegion[jumpRightAnimation.getKeyFrames().length];
        k=0;
        for (TextureRegion i : jumpRightAnimation.getKeyFrames()){
            jumpLeftFrames[k] = new TextureRegion(i);
            jumpLeftFrames[k].flip(true, false);
            k++;
        }
        jumpLeftAnimation = new Animation(0.15f, jumpLeftFrames);
        jumpLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] standLeftFrames = new TextureRegion[standRightAnimation.getKeyFrames().length];
        k=0;
        for (TextureRegion i : standRightAnimation.getKeyFrames()){
            standLeftFrames[k] = new TextureRegion(i);
            standLeftFrames[k].flip(true, false);
            k++;
        }
        standLeftAnimation = new Animation(0.15f, standLeftFrames);
        standLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);

        currentAnimation = standRightAnimation; //Commenting this leads to: java.lang.NullPointerException
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
