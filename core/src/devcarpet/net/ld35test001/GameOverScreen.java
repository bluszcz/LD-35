package devcarpet.net.ld35test001;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameOverScreen implements Screen {
    final LD35001 game;
    OrthographicCamera camera;
    float timeCounter;

    int points;
    int deerLives;
    int currentLevel;
    
	
    public GameOverScreen(final LD35001 gam, int newPoints, int lives, int level) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        timeCounter = 0.0f;
        currentLevel = level;
        deerLives = lives;
        points = newPoints;
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		timeCounter += delta;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
		game.font60.draw(game.batch, "Game Over", 290, 280);
		game.font20.draw(game.batch, "Points: "+points, 40, 180);
		game.font20.draw(game.batch, "Lives: "+deerLives, 40, 120);
		game.font20.draw(game.batch, "Level: "+(currentLevel+1), 40, 60);
		
        if (timeCounter>3)
        {
    		game.font20.draw(game.batch, "Click / touch to continue", 40, 420);

        	if (Gdx.input.isTouched()) {
//        		menuMusic.stop();{
        		
        		game.setScreen(new MainMenuScreen(game));

            
            dispose();
        	}
        }
		
        game.batch.end();


		
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
