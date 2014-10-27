package de.quadspot.beatnbrawl.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by goetsch on 27.10.14.
 */
public class SoundComponent extends Component {

    Sound sound_hit;

    public SoundComponent(HashMap props) {

        sound_hit = Gdx.audio.newSound(Gdx.files.internal(props.get("hitsound").toString()));


    }

    public Sound getSound_hit() {
        return sound_hit;
    }
}
