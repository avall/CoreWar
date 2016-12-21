package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Screen;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.assets.Assets;
import com.gmail.s0o3r0a4.corewar.game.CoreWarDebug;

public class MainMenuScreen implements Screen
{
    public MainMenuScreen(CoreWar game, Assets assets)
    {
        // TODO: Make main menu
        // README: Temp
        game.setScreen(new GameScreen(game, assets, new CoreWarDebug(8000)));
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {

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
