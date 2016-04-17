package devcarpet.net.ld35test001;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {
	SpriteBatch batch;
	Texture img;
    final LD35001 game;

    Sound keyboard01;
    Sound keyboard02;

    Music menuMusic;
    
    Texture typingDeers[];
    
    float timeCounter;
    int startingLineText;
    String textText;
    float textPosX; 
    float textPosY;
    
    
    int showingDeer;
    float showingDeerCounter;
    OrthographicCamera camera;

    public MainMenuScreen(final LD35001 gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        
        showingDeer = 0;
        
        
		batch = new SpriteBatch();
		img = new Texture("ludum-promo01.jpg");

		typingDeers = new Texture[2];
		typingDeers[0] = new Texture("introdeer-00.png");
		typingDeers[1] = new Texture("introdeer-01.png");
		keyboard01 = Gdx.audio.newSound(Gdx.files.internal("keyboard01.ogg"));
		keyboard02 = Gdx.audio.newSound(Gdx.files.internal("keyboard01.ogg"));
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("ld02.ogg"));
		startingLineText = 0;
        textText = game.menuTextStory[startingLineText];
        textPosX =  game.menuTextCords[startingLineText][0];
        textPosY =  game.menuTextCords[startingLineText][1];
        showingDeerCounter = 0.0f;
		keyboard01.play();
		menuMusic.play();
		menuMusic.setLooping(true);

    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		timeCounter += delta;
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(typingDeers[showingDeer], 0, 0);

		batch.end();
		
		
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.fontBlack.draw(game.batch, textText, textPosX, textPosY);
		
        
        if (timeCounter>3)
        {
    		game.font20.draw(game.batch, "Click / touch to continue", 200, 460);

            if (Gdx.input.isTouched()) {
            	menuMusic.stop();
                game.setScreen(new GameScreen(game, 0,0));
                
                dispose();
            }
        }
        
		
        game.batch.end();

        
//        if (Gdx.input.isButtonPressed(Buttons.LEFT)){
//        	menuMusic.stop();
//        	game.setScreen(new GameScreen(game));        	
//            dispose();
//        }
        
        
        if (textPosX>400)
        	
        {
        	float move = (float) (0.2/delta);
        	textPosX = textPosX - move;
        	
        	
            showingDeerCounter += delta;
//            System.out.println(showingDeerCounter*100);
            
            
            
//            System.out.println((int)(showingDeerCounter*100) % 8) ;
            
            
            if (((int)(showingDeerCounter*100) % 8) == 0)
            {
            	if (showingDeer==0)
            		showingDeer=1;
        		else
        			showingDeer=0;
            			
            }
        	
        	
        	
        }
        else if ((textPosX>30) && (textPosX<=400))
        {
        	float move;
    		move = (float) (0.8/delta);
        	textPosX = textPosX - move;

        }
        
        
        else if (textPosX<=30)
        	
        {
        	textPosX = 30;
        	float move;
        	if (textPosY<=200){
        		move = (float) (0.01/delta);
        	} 
        	else
        	{
        		move = (float) (0.6/delta);
        	}
        	textPosY = textPosY + move;	

        	
        } 
        
        if (textPosY>500)
        {
        	startingLineText = startingLineText+1;
        	if (startingLineText>19)
        	{
        		startingLineText = 0;
        		keyboard02.play();
        	} else 
        	{
        		keyboard01.play();
        	}
        	
            textText = game.menuTextStory[startingLineText];
            textPosX =  game.menuTextCords[startingLineText][0];
            textPosY =  game.menuTextCords[startingLineText][1];
        }
        
        
        
        
        

        
        
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



	
        //...Rest of class omitted for succinctness.

}