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
public class Enemy extends Entity {
    
    public Enemy(int locX, int locY) {
        this.locX = locX;
        this.locY = locY;
        this.velX = -20;
        createUI("/img/Enemy.png", 5);
    }

}
