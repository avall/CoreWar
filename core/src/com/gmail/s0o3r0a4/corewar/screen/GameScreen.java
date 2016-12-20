package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.game.CoreWarDebug;

import static com.badlogic.gdx.Gdx.input;

public class GameScreen implements Screen, InputProcessor
{
    private CoreWarDebug coreWarGame;
    private CoreWar coreWar;

    private final float screenWidth;
    private final float screenHeight;

    private final float coreOffsetX = 0;
    private final float coreOffsetX2 = 0;
    private final float coreOffsetY = 0;
    private final float coreOffsetY2 = 0;
    private final float coreColGap = 1;
    private final float coreRowGap = 1;

    private final float blocksCol = 64;
    private final float blocksRow = 125;

    private OrthographicCamera camera;

    private Array<Rectangle> blocks;

    private Texture blockImg;

    public GameScreen(CoreWarDebug coreWarGame, CoreWar coreWar)
    {
        this.coreWarGame = coreWarGame;
        this.coreWar = coreWar;

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);

        Gdx.app.debug("Screen size", "w: " + Float.toString(screenWidth) + " h: " + Float.toString(screenHeight));

        // Load sound effect, music, and images

        // create camera
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.setToOrtho(true, screenWidth, screenHeight);

        // create rectangles to represent the instructions

        blocks = new Array<Rectangle>();

        float blockWidth = (screenWidth - (coreOffsetX + coreOffsetX2 + coreColGap * (blocksCol - 1))) / blocksCol;
        if (blockWidth < 1f)
        {
            blockWidth = 1f;
        }

        float blockHeight = (screenHeight - (coreOffsetY + coreOffsetY2 + coreRowGap * (blocksRow - 1))) / blocksRow;
        if (blockHeight < 1f)
        {
            blockHeight = 1f;
        }

        Gdx.app.debug("Block size", "w: " + Float.toString(blockWidth) + " h: " + Float.toString(blockHeight));

        float blockX;
        float blockY;

        for (int i = 0; i < 125; i++)
        {
            for (int j = 0; j < 64; j++)
            {
                blockX = j * (blockWidth + coreColGap) + coreOffsetX;
                blockY = i * (blockHeight + coreRowGap) + coreOffsetY;

                Rectangle rectangle = new Rectangle();

                rectangle.setWidth(blockWidth);
                rectangle.setHeight(blockHeight);

                rectangle.setX(blockX);
                rectangle.setY(blockY);

                blocks.add(rectangle);
            }
        }

        blockImg = new Texture("block.png");
//        shapeRenderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show()
    {

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

        for (int i = 0; i < coreWarGame.getCoreSize(); i++)
        {
            Rectangle block = blocks.get(i);
            switch(coreWarGame.getType(i))
            {
                    case MOV:
                        coreWar.batch.setColor(Color.YELLOW);
                        break;
                    case ADD:
                        coreWar.batch.setColor(Color.RED);
                        break;
                    case SUB:
                        coreWar.batch.setColor(Color.BLUE);
                        break;
                    case MUL:
                        coreWar.batch.setColor(Color.ORANGE);
                        break;
                    case DIV:
                        coreWar.batch.setColor(Color.GREEN);
                        break;
                    case MOD:
                        coreWar.batch.setColor(Color.CYAN);
                        break;
                    case JMP:
                        coreWar.batch.setColor(Color.PINK);
                        break;
                    case JMZ:
                        coreWar.batch.setColor(Color.PURPLE);
                        break;
                    case DAT:
                        coreWar.batch.setColor(Color.GRAY);
                        if (coreWarGame.isUnempty(i))
                        {
                            coreWar.batch.setColor(Color.BLACK);
                        }
                        break;
            }
            coreWar.batch.draw(blockImg, block.x, block.y, block.width, block.height);
//            coreWar.batch.setColor(Color.WHITE);
        }

        coreWar.font.setColor(Color.WHITE);
        coreWar.font.draw(coreWar.batch, "Core War", 0, coreWar.font.getXHeight());

        coreWar.batch.end();


//        for (int i = 0; i < 64/*coreWarGame.getCoreSize()*/; i++)
//            {
//
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//                switch(coreWarGame.getType(i))
//                {
//                    case MOV:
//                        shapeRenderer.setColor(1f, 1f, 0f, 1);
//                        break;
//                    case DAT:
//                        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
//                        break;
//                }
//                Rectangle block = blocks.get(i);
//                shapeRenderer.rect(block.x, block.y, block.width, block.height);
//                shapeRenderer.end();
//        }

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
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        camera.update();

//        coreWar.batch.setProjectionMatrix(camera.combined);

        float blockWidth = (width - (coreOffsetX + coreOffsetX2 + coreColGap * (blocksCol - 1))) / blocksCol;
        if (blockWidth < 1f)
        {
            blockWidth = 1f;
        }

        float blockHeight = (height - (coreOffsetY + coreOffsetY2 + coreRowGap * (blocksRow - 1))) / blocksRow;
        if (blockHeight < 1f)
        {
            blockHeight = 1f;
        }

        float blockX;
        float blockY;

        for (int i = 0; i < 125; i++)
        {
            for (int j = 0; j < 64; j++)
            {
                blockX = j * (blockWidth + coreColGap) + coreOffsetX;
                blockY = i * (blockHeight + coreRowGap) + coreOffsetY;

                Rectangle rectangle = new Rectangle();

                rectangle.setWidth(blockWidth);
                rectangle.setHeight(blockHeight);

                rectangle.setX(blockX);
                rectangle.setY(blockY);

                blocks.set(j+i*64 , rectangle);
            }
        }

        Gdx.app.debug("Resized Screen size", "w: " + Float.toString(Gdx.graphics.getWidth()) + " h: " + Float.toString(Gdx.graphics.getHeight()));
        Gdx.app.debug("Resized Block size", "w: " + Float.toString(blockWidth) + " h: " + Float.toString(blockHeight));
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

    Vector3 tp = new Vector3();

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
        coreWarGame.cycle();
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
