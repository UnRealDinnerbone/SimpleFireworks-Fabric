package com.unrealdinnerbone.simplefireworks.api;

public enum ExplodeEffect
{
    SMALL_BALL(0),
    LAVAGE_BALL(2),
    STAR(3),
    CREEPER_FACE(4),
    SPARKLE(5);

    private int explodeID;

    ExplodeEffect(int i) {
        this.explodeID = i;
    }

    public int getExplodeID() {
        return explodeID;
    }
}
