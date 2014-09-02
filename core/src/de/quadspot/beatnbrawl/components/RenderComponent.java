package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by goetsch on 05.08.14.
 */
public class RenderComponent extends Component {
//    private TextureRegion frame = GameAssetManager.getInstance().get("textures/textures.atlas", TextureAtlas.class).findRegion("null");

    private Texture img = new Texture("badlogic.jpg");
    private Vector2 frameOffset = new Vector2();
    private float stateTime;

    public RenderComponent(){}

    public RenderComponent(Vector2 offset){
        this.frameOffset = offset;
    }

    public Texture getImg() {
        return img;
    }

    public Vector2 getFrameOffset() {
        return frameOffset;
    }

}
