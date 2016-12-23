package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.assets.Assets;
import com.gmail.s0o3r0a4.corewar.game.CoreWarDebug;
import com.gmail.s0o3r0a4.corewar.screen.actor.CoreBlocks;

public class GameScreen implements Screen, InputProcessor
{
    private CoreWarDebug coreWarGame;
    private CoreWar coreWar;

    private float screenWidth;
    private float screenHeight;

    private final float coreOffsetX = 0f;
    private final float coreOffsetX2 = 0f;
    private final float coreOffsetY = 0f;
    private final float coreOffsetY2 = 0f;
    private final float coreColGap = 1f;
    private final float coreRowGap = 1f;

    private final float blocksCol = 16;
    private final float blocksRow = 500;

    private Array<Rectangle> blocks;

    private OrthographicCamera camera;
    private FitViewport fitViewport;

    private Stage stage;

    private TextureRegion blockTextureRegion;
    private TextureRegionDrawable blockTexRegionDrawable;
    public Array<ImageButton> buttons;
    private Texture blockImg;

    private CoreBlocks coreBlocks;
    private ScrollPane scroller;
    private Table scrollTable;

    public GameScreen(CoreWar game, Assets assets, final CoreWarDebug coreWarGame)
    {
        this.coreWarGame = coreWarGame;
        this.coreWar = game;
        this.stage = new Stage();

        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

//        Gdx.app.debug("Screen size", "w: " + Float.toString(screenWidth) + " h: " + Float.toString(screenHeight));

        // Load sound effect, music, and images

        // create camera
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.setToOrtho(false, screenWidth, screenHeight);
        // Create viewport
        fitViewport = new FitViewport(screenWidth, screenHeight, camera);

//        Table table = new Table();

        Skin skin = assets.manager.get(Assets.uiSkin);
        blockImg = assets.manager.get(Assets.blockTexture);

        // create rectangles to represent the instructions

        blocks = new Array<Rectangle>();
        buttons = new Array<ImageButton>();

//        float blockWidth = (screenWidth - (coreOffsetX + coreOffsetX2 + coreColGap * (blocksCol - 1))) / blocksCol;
//        if (blockWidth < 1f)
//        {
//            blockWidth = 1f;
//        }

        float blockSize = (stage.getWidth()/* - (coreColGap * (blocksCol + 1))*/)/ blocksCol;
        if (blockSize < 1f)
        {
            blockSize = 1f;
        }

        Gdx.app.debug("Block size", Float.toString(blockSize));

        float blockX;
        float blockY;

        for (int i = 0; i < blocksRow; i++)
        {
            for (int j = 0; j < blocksCol; j++)
            {
                blockX = j * (blockSize + coreColGap) + coreOffsetX;
                blockY = i * (blockSize + coreRowGap) + coreOffsetY;

                Rectangle rectangle = new Rectangle();

                rectangle.setWidth(blockSize);
                rectangle.setHeight(blockSize);

                rectangle.setX(blockX);
                rectangle.setY(blockY);

                blocks.add(rectangle);

                blockTextureRegion = new TextureRegion(blockImg);

                blockTextureRegion.setRegionWidth((int)blockSize - 2);
                blockTextureRegion.setRegionHeight((int)blockSize - 2);

                blockTexRegionDrawable = new TextureRegionDrawable(blockTextureRegion);

//                blockTexRegionDrawable.setMinHeight(blockSize);
//                blockTexRegionDrawable.setMinWidth(blockSize);

                ImageButton button = new ImageButton(blockTexRegionDrawable); //Set the button up
//                button.setOrigin(button.getImage().getWidth(), button.getImage().getHeight());
//                button.getImage().setScale(1f);

//                button.setX(blockX);
//                button.setY(blockY);

//                button.setWidth(blockWidth);
//                button.setHeight(blockHeight);

                buttons.add(button);
            }
        }

        scrollTable = new Table();
//        scrollTable.add(new CoreBlocks(this.coreWarGame, assets));

        Gdx.app.debug("Stage width", Float.toString(stage.getWidth()));
        Gdx.app.debug("Block width", Float.toString(blockSize));

        for (int i = 0; i < blocksRow; i++)
        {
            for (int j = 0; j < blocksCol; j++)
            {
                ImageButton button = buttons.get(j + i * (int)(blocksCol));
                scrollTable.add(button).size(blockSize)/*.pad(0coreColGap / 2)*/;
            }
            scrollTable.row();
        }
//        scrollTable.setWidth(800);
//        scrollTable.setHeight(640);

        scrollTable.setWidth(screenWidth);
        scrollTable.setHeight(screenHeight);

        scroller = new ScrollPane(scrollTable);

        blockTextureRegion = new TextureRegion(blockImg);
        blockTexRegionDrawable = new TextureRegionDrawable(blockTextureRegion);

        ImageButton stepButton = new ImageButton(blockTexRegionDrawable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).expandX().fillX().bottom();
        table.row();
        table.add(stepButton);

        stepButton.addListener(new ChangeListener()
        {
            public void changed(ChangeEvent event, Actor actor)
            {
//                scroller.layout();
                scroller.setScrollPercentY(((float)coreWarGame.getCurrentAddress() -
                        blocksCol / scroller.getWidth() * scroller.getHeight() / 2f * blocksCol) /
                        (float)coreWarGame.getCoreSize());
                coreWarGame.cycle();
            }
        });

//        table.setTransform(true);
//        table.setOrigin(0, 0);
//        table.setScale(10f);
//        table.setTransform(false);

        this.stage.addActor(table);

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(this.stage);
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

//        coreWar.batch.setTransformMatrix(camera.combined);
        stage.setViewport(fitViewport);
        stage.act();
        stage.draw();
//            coreWar.batch.setColor(Color.WHITE);

//        coreWar.font.setColor(Color.WHITE);
//        coreWar.font.draw(coreWar.batch, "Core War", 0, coreWar.font.getXHeight());

        coreWar.batch.end();

        for (int i = 0; i < (int)(0 * Gdx.graphics.getDeltaTime()); i++)
        {
            coreWarGame.cycle();
        }


        for (int i = 0; i < coreWarGame.getCoreSize(); i++)
        {
            ImageButton button = buttons.get(i);
            switch (coreWarGame.getType(i))
            {
                case MOV:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.YELLOW);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
                    break;
                case ADD:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.RED);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.RED);
                    break;
                case SUB:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.BLUE);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.BLUE);
                    break;
                case MUL:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.ORANGE);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.ORANGE);
                    break;
                case DIV:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.GREEN);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.GREEN);
                    break;
                case MOD:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.CYAN);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.CYAN);
                    break;
                case JMP:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.PINK);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.PINK);
                    break;
                case JMZ:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.PURPLE);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.PURPLE);
                    break;
                case DAT:
                    button.getImage().setColor(com.badlogic.gdx.graphics.Color.GRAY);
//                    batch.setColor(com.badlogic.gdx.graphics.Color.GRAY);
                    if (coreWarGame.isUnempty(i))
                    {
                        button.getImage().setColor(com.badlogic.gdx.graphics.Color.BLACK);
//                        batch.setColor(com.badlogic.gdx.graphics.Color.BLACK);
                    }
                    break;
            }
//            batch.draw(blockImg, block.x, block.y, block.width, block.height);
        }

        // process user input
//        if (Gdx.input.isTouched()) {

//            Vector3 touchPos = new Vector3();
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPos);
//            if (TimeUtils.nanoTime() - lastCycleTime > 100000000)
//            {
//                coreWarGame.cycle();
//                lastCycleTime = TimeUtils.nanoTime();
//            }
//        }

    }


    @Override
    public void resize(int width, int height)
    {
//        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();

        fitViewport.setCamera(camera);
        fitViewport.update(width, height, true);
;

//        coreWar.batch.setProjectionMatrix(camera.combined);

//        float blockWidth = (width - (coreOffsetX + coreOffsetX2 + coreColGap * (blocksCol - 1))) / blocksCol;
//        if (blockWidth < 1f)
//        {
//            blockWidth = 1f;
//        }

//        float blockHeight = (height - (coreOffsetY + coreOffsetY2 + coreRowGap * (blocksRow - 1))) / blocksRow;
//        if (blockHeight < 1f)
//        {
//            blockHeight = 1f;
//        }
//
//        float blockX;
//        float blockY;
//
//        for (int i = 0; i < 125; i++)
//        {
//            for (int j = 0; j < 64; j++)
//            {
//                blockX = j * (blockWidth + coreColGap) + coreOffsetX;
//                blockY = i * (blockHeight + coreRowGap) + coreOffsetY;
//
//                Rectangle rectangle = new Rectangle();
//
//                rectangle.setWidth(blockWidth);
//                rectangle.setHeight(blockHeight);
//
//                rectangle.setX(blockX);
//                rectangle.setY(blockY);
//
//                blocks.set(j+i*64 , rectangle);
//            }
//        }

//        Gdx.app.debug("Resized Screen size", "w: " + Float.toString(Gdx.graphics.getWidth()) + " h: " + Float.toString(Gdx.graphics.getHeight()));
//        Gdx.app.debug("Resized Block size", "w: " + Float.toString(blockWidth) + " h: " + Float.toString(blockHeight));
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

    }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
//        camera.unproject(tp.set(screenX, screenY, 0));
//        coreWarGame.cycle();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
//        camera.unproject(tp.set(screenX, screenY, 0));
//        for (int i = 0; i < (int)(100 * Gdx.graphics.getDeltaTime()); i++)
//        {
            coreWarGame.cycle();
//        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
