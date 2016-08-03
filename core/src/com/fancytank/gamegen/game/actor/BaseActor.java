package com.fancytank.gamegen.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.fancytank.gamegen.game.Constant;
import com.fancytank.gamegen.game.script.Executable;
import com.fancytank.gamegen.game.script.ExecutableProducer;

import java.util.LinkedList;
import java.util.List;

import static com.fancytank.gamegen.game.actor.ActorInitializer.askClassName;
import static com.fancytank.gamegen.game.actor.ActorInitializer.getActionsPerTick;
import static com.fancytank.gamegen.game.actor.ActorInitializer.getListenerList;

public abstract class BaseActor extends Actor {
    public int x, y;
    public Color tint;
    private LinkedList<Executable> actions = new LinkedList<>();

    /**
     * najpierw definiuj klase, potem rob obiekty a nikomu nie stanie sie krzywda
     */
    public BaseActor(int x, int y) {
        this.x = x;
        this.y = y;
        setBounds(getX(), getY(), Constant.BLOCK_SIZE, Constant.BLOCK_SIZE);
        for (InputListener listener : initListenersList())
            addListener(listener);
        actions = initActiionsPerTick();
    }

    @Override
    public void act(float delta) {
        for (Executable action : actions)
            action.performAction();
    }

    private LinkedList<Executable> initActiionsPerTick() {
        LinkedList<Executable> output = new LinkedList<>();
        for (ExecutableProducer producer : getActionsPerTick(getClassName())) {
            Executable instance = producer.getInstance();
            instance.init(this);
            output.add(instance);
        }
        return output;
    }

    private LinkedList<InputListener> initListenersList() {
        List<ExecutableProducer> executables = getListenerList(getClassName());
        LinkedList<InputListener> output = new LinkedList<>();
        final BaseActor local = this;

        for (ExecutableProducer executableClass : executables) {
            final Executable myInstance = executableClass.getInstance();
            myInstance.init(local);
            output.add(new InputListener() {
                           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                               myInstance.performAction();
                               return true;
                           }
                       }
            );
        }
        return output;
    }

    private String className;

    public String getClassName() {
        if (className == null)
            className = ActorInitializer.askClassName(this.getClass());
        return className;
    }
}
