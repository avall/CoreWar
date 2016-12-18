package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.game.CoreWarDebug;

import static com.badlogic.gdx.Gdx.input;

public class GameScreen implements Screen, InputProcessor
{
    private CoreWarDebug coreWarGame;
    private CoreWar coreWar;

    private final int screenWidth;
    private final int screenHeight;

    private final int coreOffsetX = 10;
    private final int coreOffsetY = 20;
    private final int coreColGap = 1;
    private final int coreRowGap = 1;

    private final int blocksCol = 64;
    private final int blocksRow = 125;

    private OrthographicCamera camera;

    private Array<Rectangle> blocks;
    private ShapeRenderer shapeRenderer;

    private long lastCycleTime;

    public GameScreen(CoreWarDebug coreWarGame, CoreWar coreWar)
    {
        this.coreWarGame = coreWarGame;
        this.coreWar = coreWar;

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Load sound effect, music, and images

        // create camera
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.setToOrtho(true, screenWidth, screenHeight);

        // create rectangles to represent the instructions

        blocks = new Array<Rectangle>();

        int blockWidth = (screenWidth - (coreOffsetX * 2 + coreColGap * (blocksCol - 1))) / blocksCol;
        int blockHeight = (screenHeight - (coreOffsetY * 2 + coreRowGap * (blocksRow - 1))) / blocksRow;

        int blockX;
        int blockY;

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

        shapeRenderer = new ShapeRenderer();

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
        shapeRenderer.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        coreWar.batch.begin();
        coreWar.font.draw(coreWar.batch, "Core War", 0, 0);
        coreWar.batch.end();

        for (int i = 0; i < coreWarGame.getCoreSize(); i++)
        {

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            switch(coreWarGame.getType(i))
            {
                case MOV:
                    shapeRenderer.setColor(1f, 1f, 0f, 1);
                    break;
                case DAT:
                    shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
                    break;
            }
            Rectangle block = blocks.get(i);
            shapeRenderer.rect(block.x, block.y, block.width, block.height);
            shapeRenderer.end();
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
        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(true, width, height);

        int blockWidth = (width - (coreOffsetX * 2 + coreColGap * (blocksCol - 1))) / blocksCol;
        int blockHeight = (height - (coreOffsetY * 2 + coreRowGap * (blocksRow - 1))) / blocksRow;

        int blockX;
        int blockY;

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
