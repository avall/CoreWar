package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.assets.Assets;
import com.gmail.s0o3r0a4.corewar.game.CoreWarDebug;
import com.gmail.s0o3r0a4.corewar.net.Net;
import com.gmail.s0o3r0a4.corewar.net.node.Node;
import com.gmail.s0o3r0a4.corewar.screen.dimensions.GameScreenDimensions;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys.G;
import static com.badlogic.gdx.Input.Keys.P;

public class NetScreen implements Screen
{
    private CoreWar coreWar;

    private OrthographicCamera camera;
    private FitViewport fitViewport;
    private Stage stage;

    private Assets assets;

    private Texture blockTexture;
    private TextureRegion blockTextureRegion;
    private TextureRegionDrawable blockTexRegionDrawable;

    private TextureRegion FABTextureRegion;
    private TextureRegionDrawable FABTexRegionDrawable;
    private ImageButton FAButton;

    private float screenWidth;
    private float screenHeight;

    private Net testNet;
    private ImageButton testButton;
    private ImageButton testButton2;
    private ImageButton testButton3;
//    private Sprite testButton;

    private boolean testOnce = false;

    public NetScreen(CoreWar game, Assets assets)
    {
        this.coreWar = game;
        this.stage = new Stage();
        this.assets = assets;
    }

    @Override
    public void show()
    {
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

        blockTextureRegion = new TextureRegion(blockTexture);
        blockTextureRegion.setRegionWidth(30);
        blockTextureRegion.setRegionHeight(30);
        blockTexRegionDrawable = new TextureRegionDrawable(blockTextureRegion);

        // TODO: Test Net
        testNet = new Net(screenWidth, screenHeight);
        testButton = new ImageButton(blockTexRegionDrawable);
        testButton2 = new ImageButton(blockTexRegionDrawable);
        testButton3 = new ImageButton(blockTexRegionDrawable);
        // TODO: Add visible true after creating the table
        testButton.setVisible(false);
        testButton2.setVisible(false);
        testButton3.setVisible(false);
//        testButton = new Sprite(blockTexture);
        Table testTable = new Table();
        testTable.setFillParent(true);
        testTable.add(testButton).expand().bottom().left();
        testTable.add(testButton2).expand().bottom().left();
        testTable.add(testButton3).expand().bottom().left();
        testNet.update();
//        testButton.setPosition((float)testNet.x, (float)testNet.y);
        ScrollPane scroller2 = new ScrollPane(testTable);
        scroller2.setFillParent(true);

        // README: Create an overlay table for the stack
        final Table underLayTable = new Table();
        underLayTable.setFillParent(true);
        underLayTable.add(scroller2).expand().bottom().left();
        underLayTable.row();

        // README: Add a Floating Action Button
        FABTextureRegion = new TextureRegion(blockTexture);
        FABTexRegionDrawable = new TextureRegionDrawable(FABTextureRegion);
        FAButton = new ImageButton(blockTexRegionDrawable);
        FAButton.bottom().right().pad(20);
        FAButton.addListener(new ChangeListener()
        {
            public void changed(ChangeEvent event, Actor actor)
            {
            }
        });

        // README: Add the FAButton to the FABContainer
        Table FABContainer = new Table();
        FABContainer.setFillParent(true);
        FABContainer.add(FAButton).expand().bottom().right();

        // README: Stack up the content and FAB
        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.add(underLayTable);
        stack.add(FABContainer);

        testButton.setVisible(true);
        testButton2.setVisible(true);
        testButton3.setVisible(true);

        // README: Add stack to the stage
        this.stage.addActor(stack);
        input.setInputProcessor(this.stage);

        // TODO: Put all startup debug information here
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug("Net Screen", "Created");
        Gdx.app.debug("Stage width", Float.toString(stage.getWidth()));
        Gdx.app.debug("Stage height", Float.toString(stage.getHeight()));
        Gdx.app.debug(Float.toString((float)testNet.x), Float.toString((float)testNet.y));
        Gdx.app.debug(Float.toString((float)testNet.eneX), Float.toString((float)testNet.eneY));
    }

    @Override
    public void render(float delta)
    {
        // clear the screen with a black color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        coreWar.batch.setProjectionMatrix(camera.combined);
//        shapeRenderer.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        coreWar.batch.begin();

        stage.setViewport(fitViewport);
        stage.act();
        stage.draw();

//        coreWar.batch.draw(testButton, );

        coreWar.batch.end();

        // TODO: Test Net
        testNet.update();
        testButton.setPosition((float)testNet.x, (float)testNet.y);
        testButton2.setPosition((float)testNet.eneX, (float)testNet.eneY);
        testButton3.setPosition((float)testNet.thirdX, (float)testNet.thirdY);

        // README: Get input
        if ((Gdx.input.isKeyPressed(Input.Keys.BACK)))
        {
            Gdx.app.debug("hi", "auntie");
            coreWar.setScreen(new MainMenuScreen(coreWar, assets));
        }

    }


    @Override
    public void resize(int width, int height)
    {
//        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();

//        fitViewport.setCamera(camera);
        fitViewport.update(width, height, true);
//        coreWar.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}