package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aqua.tamlong
 */
public class Bullet extends Entity {   
    private final double SPEED = 35;   
    
    public Bullet(double locX, double locY) {
        this.locX = locX;
        this.locY = locY;
        this.velX = SPEED;
        createUI("/img/Bullet.png", 5);
    }
    
}