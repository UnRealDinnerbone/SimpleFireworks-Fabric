//package com.unrealdinnerbone.simplefireworks.parsar;
//
//import com.unrealdinnerbone.simplefireworks.SimpleFirework;
//import org.apache.logging.log4j.Level;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class SimpleParser
//{
//    protected final String name;
//    protected final File folder;
//
//    private static final List<SimpleParser> simpleParsers = new ArrayList<>();
//
//    public SimpleParser(String name, File folder) {
//        this.name = name;
//        this.folder = folder;
//        simpleParsers.add(this);
//    }
//
//
//    public abstract void scan();
//
//    protected void log(Level level, Object o) {
//        SimpleFirework.LOG_HELPER.log(level, "[" + name + "]" + o);
//    }
//
//    public static List<SimpleParser> getSimpleParsers() {
//        return simpleParsers;
//    }
//
//    public String getName() {
//        return name;
//    }
//}
