package devcarpet.net.ld35test001;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
//import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import devcarpet.net.ld35test001.LD35001.Direction;

public class GameScreen implements Screen {
    final LD35001 game;

	SpriteBatch batch;
	Texture background;
	Texture backgroundOut;
	Texture crossair;
	
	Direction scrollDirection;
	Direction scrollOutDirection;
	ParticleEffect effect;
    Sound shootSound;

    Character character;
	
	float backPosX;
	float backOutPosX;
    Music gameMusic;
    OrthographicCamera camera;
    final int arraySize;
    Character characters[];
    
    Vector3 getMousePosInGameWorld() {
    	 return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    	}
    
    public GameScreen(final LD35001 gam) {
    	this.game = gam;
		background = new Texture("bg-alpha-2400wide.png");
		backgroundOut = new Texture("sky-bg.jpg");
		crossair = new Texture("crossair.png");
		scrollDirection = Direction.RIGHT;
		backPosX = backOutPosX = 0.0f;
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("theme-chill.ogg"));
		shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.ogg"));

		gameMusic.play();
		gameMusic.setLooping(true);
	       camera = new OrthographicCamera();
	        camera.setToOrtho(false, 800, 480);
		
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("explosion.p"), Gdx.files.internal(""));
    	arraySize = 38;
		characters = new Character[arraySize];
		Random rn = new Random();

		String names[] = new String[] {"char-1.png","char-2.png"};
		
		
		for (int i=0;i<arraySize;i++)
		{
			int answer = rn.nextInt(2);
			System.out.println(answer);
			String filename = names[answer];
			float posX = (i * 70) + (answer*25) + (150*i);
			float posY = rn.nextInt(70);
			int lives = rn.nextInt(20)+5;
			characters[i] = new Character(posX, posY, filename, lives);
			
		}
//		characters[0] = new Character(750, 0, "char-1.png", 20);
//		characters[1] = new Character(1550, 30, "char-2.png", 5);
//		characters[2] = new Character(350, 30, "char-2.png", 5);
//		characters[3] = new Character(1250, 30, "char-1.png", 15);
//		characters[4] = new Character(2250, 30, "char-1.png", 15);
//		characters[5] = new Character(2550, 30, "char-2.png", 15);
//		characters[6] = new Character(2850, 30, "char-2.png", 15);

		Gdx.input.setInputProcessor(new InputAdapter () {
			public boolean  touchDown(int screenX,
					int screenY,
					int pointer,
					int button) {
	        	shootSound.play();
	        	Vector3 mousePos = getMousePosInGameWorld();
	        	
	    		effect.start();
	    		effect.setPosition(mousePos.x, mousePos.y);
	    		
//	    		rect.x += backOutPosX;
	    		
	    		for (int i=0;i<arraySize;i++)
	    		{
		    		Rectangle rect = characters[i].getBoundingRectangle(backPosX);
//		    		System.out.println(rect.x);

		    		if (rect.contains(mousePos.x, mousePos.y)){
		    			System.out.println("shoot");
		    			characters[i].life -= 1;
		    		}
	    		}
				return true;
			}

		});
		
    }
    
	@Override
	public void show() {
		// TODO Auto-generated method stub	
	}

	public void scrollBackground(float delta) {		
    	float move;
		move = (float) (0.01/delta);
		if (scrollDirection == Direction.RIGHT)
		{
			backPosX = backPosX - move;
		}
		
		if (scrollDirection == Direction.LEFT)
		{
			backPosX = backPosX + move;
		}
		
		if (backPosX<-2400+800)
		{
			scrollDirection = Direction.LEFT;
			
		}
		if (backPosX>=0)
		{
			scrollDirection =  Direction.RIGHT;
			
		}
		
		
		move = (float) (0.006/delta);
		if (scrollDirection == Direction.RIGHT)
		{
			backOutPosX = backOutPosX - move;
			if (backOutPosX<-1600+800)
			{
				backOutPosX = 0.0f;
				
			}

		}
		
		if (scrollDirection == Direction.LEFT)
		{
			backOutPosX = backOutPosX + move;
			
			if (backOutPosX>=000)
			{
				backOutPosX = -800.0f;
				
			}
			

		
		
		
		
		}

	}
	
	@Override
	public void render(float delta) {
		for (int i=0;i<arraySize;i++)
		{
			characters[i].update(delta);
		}
        
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(backgroundOut,backOutPosX,0);


        game.batch.draw(background,backPosX,0);
        for (int i=0;i<arraySize;i++)
        {
        if (characters[i].life>0)
        	game.batch.draw(characters[i].currentFrame, backPosX+characters[i].position.x, characters[i].position.y);             // #17
        }
        

        effect.draw(game.batch, delta);

        
        game.batch.end();
		

		// TODO Auto-generated method stub
        if (Gdx.input.isButtonPressed(Buttons.LEFT)){
//        	game.setScreen(new MainMenuScreen(game));

//            dispose();
        }
        
        
        scrollBackground(delta);
        
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}