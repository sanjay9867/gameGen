package com.fancytank.gamegen.programming;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import com.badlogic.gdx.graphics.Color;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.blocks.CustomBlockPattern;
import com.fancytank.gamegen.programming.data.BlockData;
import com.fancytank.gamegen.programming.data.BlockShape;
import com.fancytank.gamegen.programming.data.InputFragment;
import com.fancytank.gamegen.programming.data.ValueType;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.util.ArrayList;

public class BlocksExpendableList {
    private ExpandableListView expandableList;
    private BlockExpendableListAdapter adapter;
    private Activity activity;

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<BlockActorPattern[]> childItems = new ArrayList<>();

    public BlocksExpendableList(ExpandableListView expandableList, Activity context) {
        this.expandableList = expandableList;
        activity = context;
        setListView();

        adapter = new BlockExpendableListAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        expandableList.setAdapter(adapter);
    }

    private void setListView() {
        expandableList.setClickable(true);
        expandableList.setDividerHeight(2);
    }

    public void populateList() {
        if (parentItems.size() == 0) {
            setGroupParents();
            setChildData();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void setGroupParents() {
        parentItems.add("Demo");
    }

    private void setChildData() {
        BlockActorPattern[] demoPatterns = {
                new CustomBlockPattern("on click", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "when this object is PRESSED").setExpectedValue(ValueType.CLASS_NAME),
                        new InputFragment(InputType.DUMMY, "do"),
                        new InputFragment(InputType.SOCKET, "ON_PRESS_SOCKET").setExpectedValue(ValueType.METHOD)}, BlockShape.ACTION_LISTENER), Color.PURPLE),

                new CustomBlockPattern("empty space", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "empty space")}, BlockShape.VARIABLE).setValue("empty"), Color.SKY),
                new CustomBlockPattern("placeholder actor", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "placeholder")}, BlockShape.VARIABLE).setValue("generic"), Color.SKY),

                new CustomBlockPattern("move object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "move self"),
                        new InputFragment(InputType.VARIABLE, "vertically").setExpectedValue(ValueType.INT_NUMBER),
                        new InputFragment(InputType.VARIABLE, "horizontally").setExpectedValue(ValueType.INT_NUMBER)}, BlockShape.CHAIN_METHOD), Color.ORANGE),

                new CustomBlockPattern("number +1", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "1")}, BlockShape.VARIABLE).setValue("1"), Color.SKY),
                new CustomBlockPattern("number -1", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "-1")}, BlockShape.VARIABLE).setValue("-1"), Color.SKY),

                new CustomBlockPattern("spawn object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.VARIABLE, "spawn new").setExpectedValue(ValueType.CLASS_NAME),
                        new InputFragment(InputType.DUMMY, ""),
                        new InputFragment(InputType.VARIABLE, "at x").setExpectedValue(ValueType.INT_NUMBER),
                        new InputFragment(InputType.VARIABLE, "at y").setExpectedValue(ValueType.INT_NUMBER)}, BlockShape.CHAIN_METHOD), Color.ORANGE),

                new CustomBlockPattern("delete object", new BlockData(new InputFragment[]{
                        new InputFragment(InputType.DUMMY, "delete self")}, BlockShape.LAST_METHOD), Color.RED),
        };
        childItems.add(demoPatterns);

    }
}
