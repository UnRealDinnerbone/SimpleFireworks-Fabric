//package com.unrealdinnerbone.simplefireworks.parsar;
//
//import com.google.gson.Gson;
//import com.google.gson.stream.JsonReader;
//import com.unrealdinnerbone.yaum.api.firework.FireworkObject;
//import org.apache.logging.log4j.Level;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ObjectParser extends SimpleParser {
//
//    private List<FireworkObject> fireworkObjects;
//    private Gson gson;
//
//    public ObjectParser(File file) {
//        super("FireworkObjectParser", file);
//        this.fireworkObjects = new ArrayList<>();
//        this.gson = new Gson();
//    }
//
//    @Override
//    public void scan() {
//        this.fireworkObjects = new ArrayList<>();
//        scanFolder(folder);
//    }
//
//    private void scanFolder(File folder) {
//        for (File file : folder.listFiles()) {
//            if (file.isDirectory()) {
//                scanFolder(file);
//            } else {
//                if (file.getName().endsWith(".json")) {
//                    try {
//                        String name = file.getName().replace(".json", "");
//                        JsonReader jsonReader = new JsonReader(new FileReader(file));
//                        FireworkObjectWrapper objectWrapper = gson.fromJson(jsonReader, FireworkObjectWrapper.class);
//                        objectWrapper.fireworkObjects.forEach(object -> object.setId(name + ":" + object.getID()));
//                        fireworkObjects.addAll(objectWrapper.getFireworkObjects());
//                    } catch (FileNotFoundException e) {
//                        log(Level.ERROR, e);
//                    }
//                }
//            }
//        }
//    }
//
//
//    public List<FireworkObject> getFireworkObjects() {
//        return fireworkObjects;
//    }
//
//    public FireworkObject getFormName(String name) {
//        for(FireworkObject object1: fireworkObjects) {
//            if(object1.getID().equalsIgnoreCase(name)) {
//                return object1;
//            }
//        }
//        return null;
//    }
//
//    public boolean contains(String name) {
//        return getFormName(name) != null;
//    }
//
//    public static class FireworkObjectWrapper {
////
////        List<FireworkObject> fireworkObjects;
////
////        public List<FireworkObject> getFireworkObjects() {
////            return fireworkObjects;
////        }
////    }
//}
