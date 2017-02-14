package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.assets.Assets;
import com.gmail.s0o3r0a4.corewar.net.NodesManager;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent.Type.scroll;

public class BaseScreen implements Screen {

    private CoreWar coreWar;

    private Assets assets;
    private Stage stage;
    private OrthographicCamera camera;
    private FitViewport fitViewport;

    private NodesManager nodesManager;
    private long startTime = 0;
    private int runningSpeed;

    private float screenWidth;
    private float screenHeight;

    private Texture blockTexture;
    private TextureRegion blockTextureRegion;
    private TextureRegionDrawable blockTexRegionDrawable;

    private Array<TextureRegion> FABTextureRegions;
    private Array<TextureRegionDrawable> FABTexRegionDrawables;

    private Table baseTable;
    private Table scrollTable;
    private ScrollPane scroller;
    private Table actionBar;

    private Array<Button> FAButtons;

    public BaseScreen (CoreWar coreWar, NodesManager nodesManager, Assets assets) {
        this.coreWar = coreWar;
        this.stage = new Stage();
        this.assets = assets;
        this.nodesManager = nodesManager;
    }

    @Override
    public void show() {
        // README: Get screen size
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        // TODO: Load sound effect, music, and images

        // README: Create camera
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.setToOrtho(false, screenWidth, screenHeight);
        // README: Create viewport
        fitViewport = new FitViewport(screenWidth, screenHeight, camera);
        // README: Get assets
        Skin skin = assets.manager.get(Assets.uiSkin);
        blockTexture = assets.manager.get(Assets.blockTexture);

        // README: Add base table
        baseTable = new Table();

        // README: Add a scroll table
        scrollTable = new Table();
        scroller = new ScrollPane(scrollTable);

        // README: Set up scroll table
        scroller.setPosition(0, 0);
        scroller.setWidth(screenWidth);
        scroller.setHeight(screenHeight * (1f - 0.075f));

        // README: Add a action bar
        actionBar = new Table();
        // README: Set up action bar
        actionBar.setPosition(0, screenHeight * (1f - 0.075f));
        actionBar.setWidth(screenWidth);
        actionBar.setHeight(screenHeight * 0.075f);

        // TODO: TEST
        blockTextureRegion = new TextureRegion(blockTexture);
        blockTextureRegion.setRegionWidth(100);
        blockTextureRegion.setRegionHeight(100);
        blockTexRegionDrawable = new TextureRegionDrawable(blockTextureRegion);
        actionBar.setBackground(blockTexRegionDrawable);
        actionBar.setColor(Color.RED);
        scrollTable.setBackground(blockTexRegionDrawable);
        scrollTable.setColor(Color.GREEN);
        // TODO: TEST

        // README: Put tables together
        baseTable.addActor(actionBar);
        baseTable.addActor(scroller);

        // README: Stack the content
        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.add(baseTable);

        // README: Add Floating Action Buttons

//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = skin.getFont("default-font");

        FABTextureRegions = new Array<TextureRegion>();
        FABTexRegionDrawables = new Array<TextureRegionDrawable>();
        FAButtons = new Array<Button>();
        Array<Table> FABContainers = new Array<Table>(); // README: Add the FAButtons to the FABContainer

        for (int i = 0; i < 4; i++) {
            FABTextureRegions.add(new TextureRegion(blockTexture));
            FABTexRegionDrawables.add(new TextureRegionDrawable(FABTextureRegions.peek()));

            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = skin.getFont("default-font");
            textButtonStyle.up = FABTexRegionDrawables.peek();
            textButtonStyle.down = FABTexRegionDrawables.peek();
            textButtonStyle.checked = FABTexRegionDrawables.peek();

            FAButtons.add(new Button());
            FAButtons.peek().setBackground(FABTexRegionDrawables.peek());
            FAButtons.peek().setColor(Color.WHITE);
            FAButtons.peek().setStyle(textButtonStyle);
            FAButtons.peek().bottom().right();

            FABContainers.add(new Table());
            FABContainers.peek().setFillParent(true);
            FABContainers.peek().add(FAButtons.peek()).size((int) (screenWidth * 0.075f), (int) (screenWidth * 0.075f))
                    .expand().bottom().right().pad((int) (screenWidth * 0.05f));  // README: Add the FAButtons to the FABContainer

            stack.add(FABContainers.peek()); // README: Add the FAB to stack
        }

        FAButtons.get(1).setVisible(false);
        FAButtons.get(2).setVisible(false);
        FAButtons.get(3).setVisible(false);

        FAButtons.get(0).addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                runningSpeed = 1;
                FAButtons.get(0).setVisible(false);
                FAButtons.get(1).setVisible(true);
            }
        });

        FAButtons.get(1).addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                runningSpeed = 4;
                FAButtons.get(1).setVisible(false);
                FAButtons.get(2).setVisible(true);
            }
        });

        FAButtons.get(2).addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                runningSpeed = 16;
                FAButtons.get(2).setVisible(false);
                FAButtons.get(3).setVisible(true);
            }
        });

        FAButtons.get(3).addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                runningSpeed = 0;
                FAButtons.get(3).setVisible(false);
                FAButtons.get(0).setVisible(true);
            }
        });

        // README: Add stack to the stage
        this.stage.addActor(stack);
        input.setInputProcessor(this.stage);

        stack.addActorBefore(FABContainers.first(), FABContainers.peek());

        startTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a black color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // begin a new batch and draw the bucket and
        // all drops
        coreWar.batch.begin();

        stage.setViewport(fitViewport);
        stage.act();
        stage.draw();

        coreWar.batch.end();

        if (TimeUtils.timeSinceMillis(startTime) > 1000) {
            for (int i = 0; i < runningSpeed; i++) {
                nodesManager.cycle();
            }
            startTime = TimeUtils.millis();
        }
    }

    @Override
    public void resize(int width, int height) {
        // camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();

        // fitViewport.setCamera(camera);
        fitViewport.update(width, height, true);
        // coreWar.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
