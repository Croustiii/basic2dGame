package org.tile;

import org.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;

public class TileManager {


    GamePanel gp;
    Tile[] tiles;

    int mapTileNum [][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage(){

        ClassLoader classLoader = getClass().getClassLoader();
        URL ressource = classLoader.getResource("tiles");
        System.out.println(ressource.getPath().toString());
        File resFolder = new File(ressource.getPath());
        File[] fichiers = resFolder.listFiles();

        

        for (File f : fichiers) System.out.println(f.getName());

        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void loadMap(String pathMapFile){
        try {
            InputStream is = getClass().getResourceAsStream(pathMapFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row  = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while(col < gp.maxWorldCol){
                    String numbers [] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row ++;
                }
            }

            br.close();
        }catch (Exception e){

        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        // itérer sur toutes les position ou on est censé avoir une tuile a déssiner
        while ( worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];

            //position de la tuile en valeur absolue sur la carte
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            //
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Optimisation : Ne dessiner les tuiles que si elles doivent être visibles à l'ecran
            if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY + gp.player.screenY)
            {
                g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }


            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow ++;
            }
        }

    }

}
