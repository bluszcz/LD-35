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
        
        menuTextStory = new String[20];
        menuTextStory[0] = "Earth, 2016";
        menuTextStory[1] = "Our planet.";
        menuTextStory[2] = "You are a game designer,";
        menuTextStory[3] = "who had decided to participate";
        menuTextStory[4] = "in Ludum Dare 35.";
        menuTextStory[5] = "was awaiting all night long";
        menuTextStory[6] = "to see the voting results.";
        menuTextStory[7] = "Until 3 fucking am.";
        menuTextStory[8] = "What he had seen";
        menuTextStory[9] = "scarried shit out of him";
        menuTextStory[10] = "Shapeshifter theme,";
        menuTextStory[11] = "just badly based werewolves bla bla.";
        menuTextStory[12] = "He had decided to go the forest";
        menuTextStory[13] = "and suicide!";
        menuTextStory[14] = "(like in Lars von Trier movie)";
        menuTextStory[15] = "Yeah - nice hhuh?";
        menuTextStory[16] = "and then thunderbolt hit (kbnaoom madafuaka)";
        menuTextStory[17] = "changing int revengey deer!";
        menuTextStory[18] = "Yes yes";
        menuTextStory[19] = "He is coming after you!";
        
        customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("crossair.png")), 16, 16);
        Gdx.graphics.setCursor(customCursor);

        menuTextCords = new float[20][];

        float startTextX = 720;
        float startTextY = 30;
        for (int i=0;i<20;i++)
        	
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
