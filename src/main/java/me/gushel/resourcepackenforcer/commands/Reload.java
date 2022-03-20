package me.gushel.resourcepackenforcer.commands;

import me.gushel.resourcepackenforcer.ResourcePackEnforcer;

public class Reload {

    public static void onCommand(){
        ResourcePackEnforcer.getInstance().reloadConfig();
    }

}
