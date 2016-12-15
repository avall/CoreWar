package com.gmail.s0o3r0a4.corewar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.gmail.s0o3r0a4.corewar.game.DebugGame;

public class GameActivity extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    DebugGame game = new DebugGame(2, 8000);

    @Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        game.run();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
