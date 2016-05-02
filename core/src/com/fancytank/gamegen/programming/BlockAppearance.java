package com.fancytank.gamegen.programming;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.fancytank.gamegen.programming.Direction.*;

public class BlockAppearance {
    Label label;
    PatchData[] patch = new PatchData[4];
    static Skin skin;
    static int padding = 16;

    BlockAppearance(Actor root, String labelText, BlockShape shape) {
        label = new Label(labelText, skin);

        for (Direction dir : values())
            patch[dir.ordinal()] = new PatchData(getPatchTexture(shape.connects(dir), dir));

        setSize(root);
        setPosition(0, 0);
    }

    public static void loadSkin(Skin uiSkin) {
        skin = uiSkin;
    }

    void setPosition(float x, float y) {
        patch[DOWN.ordinal()].setPosition(x, y);
        patch[LEFT.ordinal()].setPosition(x, y + padding);
        patch[RIGHT.ordinal()].setPosition(x + padding, y + padding);
        patch[UP.ordinal()].setPosition(x, y + label.getHeight() + padding);
        label.setPosition(x + padding, y + padding);
    }

    void translate(float x, float y) {
        setPosition(patch[DOWN.ordinal()].startX + x, patch[DOWN.ordinal()].startY + y);
    }

    private void setSize(Actor root) {
        patch[UP.ordinal()].setSize(label.getWidth() + padding * 2, padding);
        patch[LEFT.ordinal()].setSize(padding, label.getHeight());
        patch[RIGHT.ordinal()].setSize(label.getWidth() + padding, label.getHeight());
        patch[DOWN.ordinal()].setSize(label.getWidth() + padding * 2, padding);
        root.setBounds(0, 0, label.getWidth() + padding * 2, label.getHeight() + padding * 2);
    }

    void drawShape(Batch batch, float alpha) {
        for (Direction dir : values())
            drawPatch(patch[dir.ordinal()], batch);
        label.draw(batch, alpha);
    }

    private void drawPatch(PatchData patchData, Batch batch) {
        patchData.patch.draw(batch, patchData.startX, patchData.startY, patchData.width, patchData.height);
    }

    private NinePatch getPatchTexture(boolean isConnected, Direction direction) {
        if (isConnected)
            return BlockTextureManager.connected[direction.ordinal()];
        else
            return BlockTextureManager.plain[direction.ordinal()];
    }
}