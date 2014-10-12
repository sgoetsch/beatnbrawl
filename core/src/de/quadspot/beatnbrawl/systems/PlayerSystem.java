package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Bits;

import de.quadspot.beatnbrawl.beatnbrawl;
import de.quadspot.beatnbrawl.components.HealthComponent;
import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.screens.GameScreen;

/**
 * Created by goetsch on 12.10.14.
 */
public class PlayerSystem extends EntitySystem {
    private Entity playerEntity;
    private ComponentMapper<HealthComponent> hcm;
    private final GameScreen game;

    public PlayerSystem(GameScreen game) {
        this.game = game;
    }

    @Override
    public void addedToEngine(Engine engine) {
        playerEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(InputComponent.class), new Bits(), new Bits())).first();
        hcm = ComponentMapper.getFor(HealthComponent.class);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        if (hcm.get(playerEntity).isDead())
            game.setState(GameScreen.GAME_OVER);
    }

    @Override
    public boolean checkProcessing() {
        return super.checkProcessing();
    }

    @Override
    public void setProcessing(boolean processing) {
        super.setProcessing(processing);
    }
}
