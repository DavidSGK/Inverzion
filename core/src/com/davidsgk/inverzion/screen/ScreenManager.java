package com.davidsgk.inverzion.screen;

public class ScreenManager {

    private static Screen currentScreen;

    public static void setScreen(Screen screen){
        if(currentScreen != null)
            currentScreen.dispose();    //dispose screen before creating new one
        currentScreen = screen;     //switch to new screen
        currentScreen.create();     //create the new screen
    }

    public static Screen getCurrentScreen(){
        return currentScreen;
    }

}
