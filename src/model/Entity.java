/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author aqua.tamlong
 */
public class Entity extends JLabel {
    Image image;
    ImageIcon imageIcon;
    double locX = 0;
    double locY = 0;
    double velX = 0;
    double velY = 0;
    boolean alive = true;
    
    /**
     * This method is use to load and create UI for entity
     * @param imagePath path to image file
     * @param imageScale scale object base on the size of image
     */
    public final void createUI(String imagePath, int imageScale) {
        image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(imagePath));
        imageIcon = new ImageIcon(image);
        image = image.getScaledInstance(imageIcon.getIconWidth()*imageScale, imageIcon.getIconHeight()*imageScale, Image.SCALE_FAST);
        imageIcon = new ImageIcon(image);
        setIcon(imageIcon);      
        setBounds((int)locX, (int)locY, imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }
    
    /**
     * This method is use to update object velocity and location
     */
    public void update() {
        locX += velX;
        locY += velY;
        setLocation((int)locX, (int)locY);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public double getLocX() {
        return locX;
    }

    public void setLocX(double locX) {
        this.locX = locX;
    }

    public double getLocY() {
        return locY;
    }

    public void setLocY(double locY) {
        this.locY = locY;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
       
}
