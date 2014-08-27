package de.quadspot.beatnbrawl.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import de.quadspot.beatnbrawl.beatnbrawl;
import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;
import java.util.Comparator;

/**
 * Created by goetsch on 05.08.14.
 */
class SpriteData {

    private TextureRegion tr;
    Vector2 position;
    float width;
    float heith;

    public SpriteData(TextureRegion tr, float x, float y, float width, float heith) {
        this.tr = tr;
        this.position.x = x;
        this.position.y = y;
        this.width = width;
        this.heith = heith;
    }

    public TextureRegion getTr() {
        return tr;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeith() {
        return heith;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

}

public class RenderSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private Entity mapEntity;
    private float elapsedTime = 0;

    private float scale;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public RenderSystem(OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, RenderComponent.class, AnimationComponent.class, CollisionComponent.class),
                new Bits(), new Bits()));
        mapEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(MapComponent.class), new Bits(), new Bits())).first();
        ComponentMapper<PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper<MapComponent> mapcm = ComponentMapper.getFor(MapComponent.class);
        scale = mapcm.get(mapEntity).getMapFactor();
        camera.position.set(Gdx.graphics.getWidth() / 2, mapcm.get(mapEntity).getMapHeight() / 2, 0);
        camera.update();
        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        ComponentMapper<PositionComponent> pcm = ComponentMapper.getFor(PositionComponent.class);
        ComponentMapper<RenderComponent> rcm = ComponentMapper.getFor(RenderComponent.class);
        ComponentMapper<MapComponent> mapcm = ComponentMapper.getFor(MapComponent.class);
        ComponentMapper<AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);
        ComponentMapper<CollisionComponent> ccm = ComponentMapper.getFor(CollisionComponent.class);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        if ((pcm.get(entities.first()).getPosition().x > camera.viewportWidth / 2) && (pcm.get(entities.first()).getPosition().x < mapcm.get(mapEntity).getMapWidth() - camera.viewportWidth / 2)) {
            camera.position.set(pcm.get(entities.first()).getPosition().x, mapcm.get(mapEntity).getMapHeight() / 2, 0);
        }

        camera.update();
        mapcm.get(mapEntity).getTiledMapRenderer().setView(camera);
        mapcm.get(mapEntity).getTiledMapRenderer().render();

        //Z-Sort versuch
        Array<Object[]> sort = new Array();
        Object[] SpiteDataA = new Object[5];
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            elapsedTime += deltaTime;
            SpiteDataA[0] = acm.get(entity).getCurrentAnimation().getKeyFrame(elapsedTime);
            SpiteDataA[1] = pcm.get(entity).getPosition().x;
            SpiteDataA[2] = pcm.get(entity).getPosition().y + pcm.get(entity).getPosition().z;
            SpiteDataA[3] = acm.get(entity).getWidth(elapsedTime);
            SpiteDataA[4] = acm.get(entity).getHeight(elapsedTime);
            
            sort.add(SpiteDataA);
            
//            sort.add( new SpriteData(acm.get(entity).getCurrentAnimation().getKeyFrame(elapsedTime),
//                    pcm.get(entity).getPosition().x, 
//                    pcm.get(entity).getPosition().y + pcm.get(entity).getPosition().z,
//                    acm.get(entity).getWidth(elapsedTime),
//                    acm.get(entity).getHeight(elapsedTime)));
            //sort.add(new Sprite(acm.get(entity).getCurrentAnimation().getKeyFrame(elapsedTime))); 
//            sort.get(i).setPosition(pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y + pcm.get(entity).getPosition().z);
//            sort.get(i).setRegionWidth(acm.get(entity).getWidth(elapsedTime));
//            sort.get(i).setRegionHeight(acm.get(entity).getHeight(elapsedTime));
//
//            sort.get(i).setScale(scale, scale);

        }
        //@TODO: implemet Comperator;
        sort.sort(new Comparator<Object[]>() {
            @Override
            public int compare(Object[] s0, Object[] s1) {
                return (int) s0[2] - (int) s1[2];
            }
        });
        sort.reverse();
        batch.begin();
        
        for (Object[] sprite : sort) {
            
            batch.draw((TextureRegion)sprite[0], (float)sprite[1], (float)sprite[2],0,0,(float)sprite[3],(float)sprite[4],scale,scale,0);
        }
        //-------------------------

//        batch.begin();
//        for(int i = 0; i < entities.size(); ++i){
//            Entity entity = entities.get(i);
//            elapsedTime += deltaTime;
//            //batch.draw(acm.get(entity).getCurrentAnimation().getKeyFrame(elapsedTime), pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y+pcm.get(entity).getPosition().z);
//            batch.draw(acm.get(entity).getCurrentAnimation().getKeyFrame(elapsedTime), pcm.get(entity).getPosition().x, pcm.get(entity).getPosition().y+pcm.get(entity).getPosition().z,
//                    0,0,acm.get(entity).getWidth(elapsedTime),acm.get(entity).getHeight(elapsedTime),scale,scale,0);
//            //batch.draw(render.getImg(), 300, 300);
//            //System.out.println(deltaTime);
//            //System.out.println(acm.get(entity).getWalkRightAnimation().getKeyFrames().length);
//            // getKeyFrameIndex(deltaTime));
//        }
        if (beatnbrawl.DEBUG) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
        batch.end();
        if (beatnbrawl.DEBUG) {
            for (int i = 0; i < entities.size(); ++i) {
                Entity entity = entities.get(i);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

                shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(ccm.get(entity).getCollidingBody().getCollisionBox().x, ccm.get(entity).getCollidingBody().getCollisionBox().y, ccm.get(entity).getCollidingBody().getCollisionBox().width, ccm.get(entity).getCollidingBody().getCollisionBox().height);
                shapeRenderer.setColor(Color.BLUE);
                shapeRenderer.rect(ccm.get(entity).getCollidingBody().getBoundingBox().x, ccm.get(entity).getCollidingBody().getBoundingBox().y, ccm.get(entity).getCollidingBody().getBoundingBox().width, ccm.get(entity).getCollidingBody().getBoundingBox().height);
                shapeRenderer.setColor(Color.GREEN);
                shapeRenderer.rect(mapcm.get(mapEntity).getGroundBody().x, mapcm.get(mapEntity).getGroundBody().y, mapcm.get(mapEntity).getGroundBody().width, mapcm.get(mapEntity).getGroundBody().height);
                shapeRenderer.setColor(Color.ORANGE);
                shapeRenderer.rect(ccm.get(entity).getCollidingBody().getAttackBoxRight().x, ccm.get(entity).getCollidingBody().getAttackBoxRight().y, ccm.get(entity).getCollidingBody().getAttackBoxRight().width, ccm.get(entity).getCollidingBody().getAttackBoxRight().height);
                shapeRenderer.setColor(Color.PINK);
                shapeRenderer.rect(ccm.get(entity).getCollidingBody().getAttackBoxLeft().x, ccm.get(entity).getCollidingBody().getAttackBoxLeft().y, ccm.get(entity).getCollidingBody().getAttackBoxLeft().width, ccm.get(entity).getCollidingBody().getAttackBoxLeft().height);
                shapeRenderer.end();
            }
        }
    }
}
