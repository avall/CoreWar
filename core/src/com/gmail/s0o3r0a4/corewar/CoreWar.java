package com.gmail.s0o3r0a4.corewar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.s0o3r0a4.corewar.game.CoreWarDebug;
import com.gmail.s0o3r0a4.corewar.screen.GameScreen;

public class CoreWar extends Game
{
	public SpriteBatch batch;
    public BitmapFont font;
//	Texture img;

    CoreWarDebug coreWarDebug = new CoreWarDebug(8000);

    @Override
	public void create () {
		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
        font = new BitmapFont();
		this.setScreen(new GameScreen(coreWarDebug, this));
	}

	@Override
	public void render () {
        super.render();
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
