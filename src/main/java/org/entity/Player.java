package org.entity;

import org.main.GamePanel;
import org.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    // position du joueur au à l'ecran
    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // on fixe la position du joueur comme étant fixe au centre de la caméra
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        this.setDefaults();
        getPlayerImage();
    }

    public void setDefaults(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            up1= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if (isKeyPressed()){
            if(keyH.upPressed){
                direction = "up";
                worldY -= speed;
            }
            if(keyH.downPressed){
                direction = "down";
                worldY += speed;
            }
            if(keyH.leftPressed){
                direction = "left";
                worldX -= speed;
            }
            if(keyH.rightPressed){
                direction = "right";
                worldX += speed;
            }
            spriteCounter ++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    private boolean isKeyPressed(){
        return (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed);
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch(direction) {
            case "up" :
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down" :
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left" :
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right" :
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image,screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
