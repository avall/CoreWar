package com.gmail.s0o3r0a4.corewar.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public AssetManager manager = new AssetManager();

    public static final AssetDescriptor<Texture> blockTexture =
            new AssetDescriptor<Texture>("block.png", Texture.class);

    public static final AssetDescriptor<Skin> uiSkin =
            new AssetDescriptor<Skin>("data/uiskin.json", Skin.class);

    public void load() {
        manager.load(blockTexture);
        manager.load(uiSkin);
    }

    public void dispose() {
        manager.dispose();
    }
}

