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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import de.quadspot.beatnbrawl.beatnbrawl;
import de.quadspot.beatnbrawl.components.AnimationComponent;
import de.quadspot.beatnbrawl.components.CollisionComponent;
import de.quadspot.beatnbrawl.components.HealthComponent;
import de.quadspot.beatnbrawl.components.InputComponent;
import de.quadspot.beatnbrawl.components.MapComponent;
import de.quadspot.beatnbrawl.components.PositionComponent;
import de.quadspot.beatnbrawl.components.RenderComponent;
import java.util.Comparator;

/**
 * Created by goetsch on 05.08.14.
 */
class SpriteData {

    private Texture t;
    private TextureRegion tr;
    Vector2 position = new Vector2(0,0);
    float width;
    float heigth;

    public SpriteData(TextureRegion tr, float x, float y, float width, float heigth) {
        this.tr = tr;
        this.position.x = x;
        this.position.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public SpriteData(Texture t, float x, float y, float width, float heigth) {
        this.t = t;
        this.position.x = x;
        this.position.y = y;
        this.width = width;
        this.heigth = heigth;
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

    public float getHeigth() {
        return heigth;
    }

    public float getX() {
        return position.x;
    }

    public int getY() {
        return ((Float)position.y).intValue();
    }

}

public class RenderSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private Entity mapEntity;
    private Entity playerEntity;


    private float scale;

    private OrthographicCamera camera;
    private beatnbrawl game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    ComponentMapper<PositionComponent> pcm;
    ComponentMapper<MapComponent> mapcm;
    ComponentMapper<RenderComponent> rcm;
    ComponentMapper<AnimationComponent> acm;
    ComponentMapper<CollisionComponent> ccm;
    ComponentMapper<HealthComponent> hcm;



    public RenderSystem(OrthographicCamera camera, beatnbrawl game) {
        this.camera = camera;
        this.batch = game.batch;
        this.game = game;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(PositionComponent.class, RenderComponent.class, AnimationComponent.class, CollisionComponent.class),
                new Bits(), new Bits()));
        mapEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(MapComponent.class), new Bits(), new Bits())).first();
        playerEntity = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(InputComponent.class), new Bits(), new Bits())).first();
        pcm = ComponentMapper.getFor(PositionComponent.class);
        mapcm = ComponentMapper.getFor(MapComponent.class);
        scale = mapcm.get(mapEntity).getMapFactor();
        camera.position.set(Gdx.graphics.getWidth() / 2, mapcm.get(mapEntity).getMapHeight() / 2, 0);
        camera.update();
        shapeRenderer = new ShapeRenderer();

        rcm = ComponentMapper.getFor(RenderComponent.class);
        acm = ComponentMapper.getFor(AnimationComponent.class);
        ccm = ComponentMapper.getFor(CollisionComponent.class);
        hcm = ComponentMapper.getFor(HealthComponent.class);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }

    @Override
    public void update(float deltaTime) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        if ((pcm.get(playerEntity).getPosition().x > camera.viewportWidth / 2) && (pcm.get(playerEntity).getPosition().x < mapcm.get(mapEntity).getMapWidth() - camera.viewportWidth / 2)) {
            camera.position.set(pcm.get(playerEntity).getPosition().x, mapcm.get(mapEntity).getMapHeight() / 2, 0);
        }

        camera.update();
        mapcm.get(mapEntity).getTiledMapRenderer().setView(camera);
        mapcm.get(mapEntity).getTiledMapRenderer().render();

        //Z-Sort
        Array<SpriteData> sort = new Array();
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);

            sort.add( new SpriteData(acm.get(entity).getCurrentAnimation().getKeyFrame(acm.get(entity).getStateTime()),
                    pcm.get(entity).getPosition().x, 
                    pcm.get(entity).getPosition().y + pcm.get(entity).getPosition().z,
                    acm.get(entity).getWidth(acm.get(entity).getStateTime()),
                    acm.get(entity).getHeight(acm.get(entity).getStateTime())));

        }

//        sort.add( new SpriteData(acm.get(entity).getCurrentAnimation().getKeyFrame(elapsedTime),
//                pcm.get(entity).getPosition().x,
//                pcm.get(entity).getPosition().y + pcm.get(entity).getPosition().z,
//                acm.get(entity).getWidth(elapsedTime),
//                acm.get(entity).getHeight(elapsedTime)));


        sort.sort(new Comparator<SpriteData>() {
            @Override
            public int compare(SpriteData s0, SpriteData s1) {
                return s0.getY() - s1.getY();
            }
        });
        sort.reverse();
        batch.begin();
        
        for (SpriteData sprite : sort) {
            
           //batch.draw((TextureRegion)sprite[0], (float)sprite[1], (float)sprite[2],0,0,(float)sprite[3],(float)sprite[4],scale,scale,0);
            batch.draw(sprite.getTr(),sprite.getX(), sprite.getY(),sprite.getWidth()/2,0,sprite.getWidth(),sprite.getHeigth(),scale,scale,0);

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

        OrthographicCamera guiCam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        guiCam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);

        batch.setProjectionMatrix(guiCam.combined);
        batch.begin();
        game.font.draw(batch, hcm.get(playerEntity).getHealthString() , 30, 480 - 20);
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
