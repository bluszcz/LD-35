package devcarpet.net.ld35test001;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import devcarpet.net.ld35test001.LD35001.Direction;

public class GameScreen implements Screen {
    final LD35001 game;

	SpriteBatch batch;
	Texture background;
	Texture backgroundOut;
	Texture crossair;
	
	Direction scrollDirection;
	Direction scrollOutDirection;

    Sound shootSound;

	
	float backPosX;
	float backOutPosX;
    Music gameMusic;

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
		
		
		Gdx.input.setInputProcessor(new InputAdapter () {
			public boolean  touchDown(int screenX,
					int screenY,
					int pointer,
					int button) {
	        	shootSound.play();
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
		
		
		
		
		
		System.out.println(backOutPosX);

	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(backgroundOut,backOutPosX,0);


        game.batch.draw(background,backPosX,0);
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