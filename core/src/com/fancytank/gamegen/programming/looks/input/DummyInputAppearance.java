package com.fancytank.gamegen.programming.looks.input;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.looks.BlockAppearance;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class DummyInputAppearance extends BlockInputAppearance {
    Label label;

    DummyInputAppearance(InputFragment inputFragment) {
        super(inputFragment);
        label = new Label(inputFragment.labelText, new Label.LabelStyle(BlockAppearance.font, Color.BLACK));
        setPreferredSize();
    }

    private void setPreferredSize() {
        patchData.width = label.getWidth() + padding + spacing;
        patchData.height = label.getHeight() + spacing;
    }

    @Override
    public void drawInput(Batch batch, float alpha) {
        super.drawInput(batch, alpha);
        label.draw(batch, alpha);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        label.setPosition(x + spacing / 2, y);
    }
}
