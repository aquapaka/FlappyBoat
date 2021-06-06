/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author aqua.tamlong
 */
public class Boat extends Entity {
    final double JUMP_POWER = 20;
    final double GRAVITY = 0.6;

    public Boat() {
    }
    
    public Boat(int locX, int locY) {
        this.locX = locX;
        this.locY = locY;
        this.velY = 0;
        createUI("/img/Player_Boat.png", 5);
    }
    
    public void jump() {
        velY -= JUMP_POWER;
    }
    
    @Override
    public void update() {
        velY += GRAVITY;
        locX += velX;
        locY += velY;
        setLocation((int)locX, (int)locY);
    }

}
