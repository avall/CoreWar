package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class GameScreen implements Screen
{
    private CoreWarDebug coreWarGame;
    private CoreWar coreWar;

    private OrthographicCamera camera;
    private FitViewport fitViewport;
    private Stage stage;

    private Assets assets;

    private Texture blockTexture;
    private TextureRegion blockTextureRegion;
    private TextureRegionDrawable blockTexRegionDrawable;
    public Array<ImageButton> blocks;

    private TextureRegion FABTextureRegion;
    private TextureRegionDrawable FABTexRegionDrawable;
    private ImageButton FAButton;

    private Table scrollTable;
    private ScrollPane scroller;

    private float screenWidth;
    private float screenHeight;

    private float coreOffsetX = GameScreenDimensions.CORE_OFFSET_X1;
    private float coreOffsetX2 = GameScreenDimensions.CORE_OFFSET_X2;
    private float coreOffsetY = GameScreenDimensions.CORE_OFFSET_Y;
    private float coreOffsetY2 = GameScreenDimensions.CORE_OFFSET_Y2;
    private float coreColGap = GameScreenDimensions.CORE_COL_GAP;
    private float coreRowGap = GameScreenDimensions.CORE_ROW_GAP;

    private float blocksCol = GameScreenDimensions.BLOCKS_COL;
    private float blocksRow = GameScreenDimensions.BLOCKS_ROW;

    private Net testNet;
    private ImageButton testButton;

    public GameScreen(CoreWar game, Assets assets, final CoreWarDebug coreWarGame)
    {
        this.coreWarGame = coreWarGame;
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

        // README: Create all blocks of the core
        float blockColSize = (stage.getWidth()/* - (coreColGap * (blocksCol + 1))*/) / blocksCol;
        float blockRowSize = (stage.getWidth()/* - (coreColGap * (blocksCol + 1))*/) / blocksCol;
        if (blockColSize < coreColGap * 2 + 1)
        {
            blockColSize = coreColGap * 2 + 1;
        }
        if (blockRowSize < coreRowGap * 2 + 1)
        {
            blockColSize = coreRowGap * 2 + 1;
        }
        blockTextureRegion = new TextureRegion(blockTexture);
        blockTextureRegion.setRegionWidth((int) blockColSize - (int)coreColGap * 2);
        blockTextureRegion.setRegionHeight((int) blockRowSize - (int)coreRowGap * 2);
        blockTexRegionDrawable = new TextureRegionDrawable(blockTextureRegion);
        blocks = new Array<ImageButton>();
        scrollTable = new Table();
        for (int i = 0; i < blocksRow; i++)
        {
            for (int j = 0; j < blocksCol; j++)
            {
                blocks.add(new ImageButton(blockTexRegionDrawable));
                scrollTable.add(blocks.peek()).size(blockColSize)/*.pad(0coreColGap / 2)*/;
            }
            scrollTable.row();
        }
        scroller = new ScrollPane(scrollTable);

        // TODO: Test Net
        testNet = new Net(screenWidth, screenHeight);
        testButton = new ImageButton(blockTexRegionDrawable);
        Table testTable = new Table();
        testTable.setFillParent(true);
        testTable.add(testButton);
        ScrollPane scroller2 = new ScrollPane(testTable);

        // README: SplitPane here
        SplitPane splitter = new SplitPane(scroller, scroller2, true, skin);

        // README: Create an overlay table for the stack
        final Table underLayTable = new Table();
        underLayTable.setFillParent(true);
        underLayTable.add(splitter).expandX().fillX().bottom();
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
                scroller.setScrollPercentY(((float) coreWarGame.getCurrentAddress() -
                        blocksCol / scroller.getWidth() * scroller.getHeight() / 2f * blocksCol) /
                        (float) coreWarGame.getCoreSize());
                coreWarGame.cycle();
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

        // README: Add stack to the stage
        this.stage.addActor(stack);
        input.setInputProcessor(this.stage);

        // TODO: Put all startup debug information here
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug("Game Screen", "Created");
        Gdx.app.debug("Block size", Float.toString(blockColSize));
        Gdx.app.debug("Stage width", Float.toString(stage.getWidth()));
        Gdx.app.debug("Block width", Float.toString(blockColSize));
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

        coreWar.batch.end();

        for (int i = 0; i < (int) (0 * Gdx.graphics.getDeltaTime()); i++)
        {
            coreWarGame.cycle();
        }


        for (int i = 0; i < coreWarGame.getCoreSize(); i++)
        {
            ImageButton button = blocks.get(i);
            switch (coreWarGame.getType(i))
            {
                case MOV:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.YELLOW);
                    break;
                case ADD:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.RED);
                    break;
                case SUB:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.BLUE);
                    break;
                case MUL:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.ORANGE);
                    break;
                case DIV:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.GREEN);
                    break;
                case MOD:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.CYAN);
                    break;
                case JMP:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.PINK);
                    break;
                case JMZ:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.PURPLE);
                    break;
                case DAT:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.GRAY);
                    if (coreWarGame.isUnempty(i))
                    {
                        button.getImage().setColor(com.badlogic.gdx.graphics.Color.BLACK);
                    }
                    break;
            }
        }

        // TODO: Test Net
        testNet.update();
        Gdx.app.debug(Float.toString((float)testNet.x), Float.toString((float)testNet.y));
        testButton.setPosition((float)testNet.x, (float)testNet.y);


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
        coreWar.setScreen(new MainMenuScreen(coreWar, assets));
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
