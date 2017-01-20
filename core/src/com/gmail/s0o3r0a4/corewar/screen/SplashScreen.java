package com.gmail.s0o3r0a4.corewar.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gmail.s0o3r0a4.corewar.CoreWar;
import com.gmail.s0o3r0a4.corewar.assets.Assets;

public class SplashScreen implements Screen {
    private CoreWar game;
    private Stage stage;
    private Assets assets;

    public SplashScreen(CoreWar game, Assets assets) {
        this.game = game;
        this.assets = assets;
        this.stage = new Stage();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (assets.manager.update()) {
//            game.setScreen(new GameScreen(game, assets, new CoreDebug(8000)));
            game.setScreen(new NetScreen(game, assets));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
