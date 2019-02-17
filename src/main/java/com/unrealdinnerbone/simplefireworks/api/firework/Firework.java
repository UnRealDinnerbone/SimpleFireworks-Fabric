package com.unrealdinnerbone.simplefireworks.api.firework;

import java.util.ArrayList;
import java.util.List;

public class Firework {

    private List<FireworkEffect> fireworkEffects;
    private ExplodeEffect explodeEffect;
    private List<Integer> colors;
    private List<Integer> fadeColors;
    private int flyHeight;

    public Firework() {
        fireworkEffects = new ArrayList<>();
        explodeEffect = null;
        colors = new ArrayList<>();
        fadeColors = new ArrayList<>();
        this.flyHeight = 1;
    }

    public void setFireworkEffects(List<FireworkEffect> fireworkEffects) {
        this.fireworkEffects = fireworkEffects;
    }

    public void addFireworkEffect(FireworkEffect fireworkEffect) {
        if (!this.fireworkEffects.contains(fireworkEffect)) {
            this.fireworkEffects.add(fireworkEffect);
        }
    }

    public void removeFireworkEffect(FireworkEffect fireworkEffect) {
        this.fireworkEffects.remove(fireworkEffect);
    }

    public void setExplodeEffect(ExplodeEffect explodeEffect) {
        this.explodeEffect = explodeEffect;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public void addColor(Integer fireworkColor) {
        if (!this.colors.contains(fireworkColor)) {
            this.colors.add(fireworkColor);
        }
    }

    public void removeColor(Integer fireworkColor) {
        this.colors.remove(fireworkColor);
    }


    public void setFadeColors(List<Integer> fadeColors) {
        this.fadeColors = fadeColors;
    }

    public void addFadeColor(Integer fireworkColor) {
        if (!this.fadeColors.contains(fireworkColor)) {
            this.fadeColors.add(fireworkColor);
        }
    }

    public void removeFadeColor(Integer fireworkColor) {
        this.fadeColors.remove(fireworkColor);
    }

    public List<FireworkEffect> getFireworkEffects() {
        return fireworkEffects;
    }

    public ExplodeEffect getExplodedEffect() {
        return explodeEffect;
    }

    public List<Integer> getBrustColors() {
        return colors;
    }

    public List<Integer> getFadeColors() {
        return fadeColors;
    }

    public int getFlyHeight() {
        return flyHeight;
    }

    public void setFlyHeight(int flyHeight) {
        this.flyHeight = flyHeight;
    }
}




