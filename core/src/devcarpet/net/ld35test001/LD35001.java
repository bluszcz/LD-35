package devcarpet.net.ld35test001;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LD35001 extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont fontBlack;
    public BitmapFont font20;
    public BitmapFont font60;


    public String[] menuTextStory;
    public float[][] menuTextCords;
    
    public Cursor customCursor;

    
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        
        fontBlack = new BitmapFont(Gdx.files.internal("ld35001-gradient.fnt"));
        font20 = new BitmapFont(Gdx.files.internal("ld35001-gradient-20.fnt"));
        font60 = new BitmapFont(Gdx.files.internal("ld35001-gradient-60.fnt"));

        
//        fontBlack.setColor(Color.BLACK);
        
        menuTextStory = new String[13];
        menuTextStory[0] = "Earth, 2016.";
        menuTextStory[1] = "This game";
        menuTextStory[2] = "is about a game designer";
        menuTextStory[3] = "who was disappointed";
        menuTextStory[4] = "with ld 35 voting results";
        menuTextStory[5] = "and transformed himself";
        menuTextStory[6] = "into a Deer.";
        menuTextStory[7] = "The Deer, looking for a revenge.";
        menuTextStory[8] = "Deershifter";
        menuTextStory[9] = "code: bluszcz";
        menuTextStory[10] = "visual art: ukata";
        menuTextStory[11] = "sfx / music: bluszcz";
        menuTextStory[12] = "Enjoy the game!";
        
        
        customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("crossair.png")), 16, 16);
        Gdx.graphics.setCursor(customCursor);

        menuTextCords = new float[13][];

        float startTextX = 720;
        float startTextY = 30;
        for (int i=0;i<13;i++)
        	
        {
        	menuTextCords[i]= new float[]{startTextX,startTextY};
        }
        
        
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    
    public enum Direction {
    	LEFT, RIGHT
    }
    
    
}
