package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.assets.Assets;
import com.gmail.s0o3r0a4.corewar.game.CoreWarDebug;

public class MainMenuScreen implements Screen
{
    public MainMenuScreen(CoreWar game, Assets assets)
    {
        // TODO: Make main menu
        // README: Temp
//        game.setScreen(new GameScreen(game, assets, new CoreWarDebug(8000)));
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
    }

    @Override
    public void resize(int width, int height)
    {

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
}
