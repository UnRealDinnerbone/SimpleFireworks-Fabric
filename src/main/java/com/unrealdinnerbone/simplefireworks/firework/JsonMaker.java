package com.unrealdinnerbone.simplefireworks.firework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unrealdinnerbone.simplefireworks.lib.FileHelper;
import com.unrealdinnerbone.simplefireworks.lib.firework.EnumExplodeEffect;
import com.unrealdinnerbone.simplefireworks.lib.firework.FireworkBase;
import com.unrealdinnerbone.simplefireworks.lib.firework.FireworkObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonMaker
{
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static final String[][][] ALL = {
            FireworkLetter.A,
            FireworkLetter.B,
            FireworkLetter.C,
            FireworkLetter.D,
            FireworkLetter.E,
            FireworkLetter.F,
            FireworkLetter.G,
            FireworkLetter.H,
            FireworkLetter.I,
            FireworkLetter.J,
            FireworkLetter.K,
            FireworkLetter.L,
            FireworkLetter.M,
            FireworkLetter.N,
            FireworkLetter.O,
            FireworkLetter.P,
            FireworkLetter.Q,
            FireworkLetter.R,
            FireworkLetter.S,
            FireworkLetter.T,
            FireworkLetter.U,
            FireworkLetter.V,
            FireworkLetter.W,
            FireworkLetter.X,
            FireworkLetter.Y,
            FireworkLetter.Z,
    };

    public static void makeBaseJsonForObjects(File file)  {
        FireworkObjectWrapper objectWrapper = new FireworkObjectWrapper();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("W", "white");
        char letter = 'A';
        for (String[][] array : ALL) {
            FireworkObject object = new FireworkObject();
            object.setIdentifiers(stringStringHashMap);
            object.setId(String.valueOf(letter++));
            object.setObjects(array);
            objectWrapper.fireworkObjects.add(object);
        }
        File letters = new File(file, "letters.json");
        String json = gson.toJson(objectWrapper, FireworkObjectWrapper.class);
        FileHelper.writeStringToFile(json, letters);
    }

    public static void makeBaseFireworkFile(File file) {
        FireworkWrapper fireworkWrapper = new FireworkWrapper();
        FireworkBase base = new FireworkBase();
        List<Integer> whiteList = new ArrayList<>();
        whiteList.add(16777215);
        base.setFadeColors(whiteList);
        base.setColors(whiteList);
        base.setExplodeEffect(EnumExplodeEffect.SMALL_BALL);
        File white = new File(file, "white.json");
        fireworkWrapper.fireworks.add(base);
        String json = gson.toJson(fireworkWrapper, FireworkWrapper.class);
        FileHelper.writeStringToFile(json, white);
    }


    public static class FireworkObjectWrapper {

        List<FireworkObject> fireworkObjects;

        public FireworkObjectWrapper() {
            fireworkObjects= new ArrayList<>();
        }

        public List<FireworkObject> getFireworkObjects() {
            return fireworkObjects;
        }

    }

    public static class FireworkWrapper {

        List<FireworkBase> fireworks;

        public FireworkWrapper() {
            fireworks = new ArrayList<>();
        }

        public List<FireworkBase> getFireworks() {
            return fireworks;
        }

    }
}
