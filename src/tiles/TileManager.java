package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gamePanel;
	Tile[] tiles;
	int tileSize;
	int mapTileScheme[][];
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		tiles = new Tile[14];
		tileSize = gamePanel.tileSize;
		
		mapTileScheme = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
		
		getTileImage();
		loadMap();
	}
	
	private void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/Maps/maps01.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			while((col < gamePanel.maxScreenCol) && (row < gamePanel.maxScreenRow)) {
				String line = br.readLine();
				
				while(col < gamePanel.maxScreenCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileScheme[col][row] = num;
					col++;
				}
				if(col == gamePanel.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getTileImage() {
		BufferedImage tileSets = importImage();
		
		tiles[0] = new Tile();
		tiles[0].image = tileSets.getSubimage(64, 64, 64, 64); // full hitam
		
		tiles[1] = new Tile(false);
		tiles[1].image = tileSets.getSubimage(128, 192, 64, 64); // background / wallpaper
		
		tiles[2] = new Tile();
		tiles[2].image = tileSets.getSubimage(0, 192, 64, 64); // kayu kiri atas
		
		
		tiles[3] = new Tile();
		tiles[3].image = tileSets.getSubimage(64, 192, 64, 64); // kayu kanan atas
		
		tiles[4] = new Tile();
		tiles[4].image = tileSets.getSubimage(0, 256, 64, 64); // kayu kiri bawah
		
		
		tiles[5] = new Tile();
		tiles[5].image = tileSets.getSubimage(64, 256, 64, 64); // kayu kanan bawah
		
		
		tiles[6] = new Tile();
		tiles[6].image = tileSets.getSubimage(128,64, 64, 64); // kayu kiri tengah
		
		
		tiles[7] = new Tile();
		tiles[7].image = tileSets.getSubimage(0, 64, 64, 64); // kayu kanan tengah
		
		
		tiles[8] = new Tile();
		tiles[8].image = tileSets.getSubimage(64, 0, 64, 64); // kayu tengah bawah
		
		
		tiles[9] = new Tile();
		tiles[9].image = tileSets.getSubimage(64, 128, 64, 64); // kayu tengah atas
		
		tiles[10] = new Tile();
		tiles[10].image = tileSets.getSubimage(0, 0, 64, 64); // kayu tengah bawah kanan
		
		tiles[11] = new Tile();
		tiles[11].image = tileSets.getSubimage(128, 0, 64, 64); // kayu tengah bawah kiri
		
		
		tiles[12] = new Tile();
		tiles[12].image = tileSets.getSubimage(0, 128, 64, 64); // kayu tengah atas kanan
		
		
		tiles[13] = new Tile();
		tiles[13].image = tileSets.getSubimage(128, 128, 64, 64); // kayu tengah atas kiri
		
	}
	
	private BufferedImage importImage() {
	    String path = String.format("/Tile-Sets/Tile-Sets (64-64).png");
	    
	    try (InputStream is = getClass().getResourceAsStream(path)) {
	        if (is == null) {
	            return null;
	        }
	        return ImageIO.read(is);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	public void render(Graphics g) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        
        while((col < gamePanel.maxScreenCol) && (row < gamePanel.maxScreenRow)) {
        	
        	int tileNum = mapTileScheme[col][row];
        	
        	g.drawImage(tiles[tileNum].image, x, y, tileSize, tileSize, null);
        	col++;
        	x += tileSize;
        	
        	if(col == gamePanel.maxScreenCol) {
        		col = 0;
        		x = 0;
        		y += tileSize;
        		row++;
        	}
        }
    }
}
