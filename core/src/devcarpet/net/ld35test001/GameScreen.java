package devcarpet.net.ld35test001;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;
import java.util.Vector;

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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import devcarpet.net.ld35test001.LD35001.Direction;

public class GameScreen implements Screen {
	final LD35001 game;


	ShapeRenderer shapeRenderer;

	SpriteBatch batch;
	Texture background;
	Texture backgroundOut;
	Texture crossair;
	Texture hoof;

	Direction scrollDirection;
	Direction scrollOutDirection;
	ParticleEffect effect;

	ParticleEffect effectExplosion;

	Sound shootSound;
	Sound explosionSound;

	Character character;

	int currentLevel;
	float timeCounter;
	float spawnCounter;

	Vector<Vote> votingCards;

	float backPosX;
	float backOutPosX;
	Music gameMusic;
	OrthographicCamera camera;
	final int arraySize;
	Character characters[];

	final int arraySizeSounds;
	Sound soundsAw[];
	int deerLives;
	int points;
	Rectangle rectHoof;

	int levelsEnemies[];
	String names[][];
	//    Vote votingCard;

	Vector3 getMousePosInGameWorld() {
		return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}


	void addVotingCard() {
		Vote card = new Vote(currentLevel);
		votingCards.add(card);	
	}


	public GameScreen(final LD35001 gam, int level, int newPoints) {
		currentLevel = level;
		deerLives = 1;
		this.game = gam;
		background = new Texture("bg-alpha-2400wide.png");
		backgroundOut = new Texture("sky-bg.jpg");
		hoof = new Texture("hoof.png");
		crossair = new Texture("crossair.png");
		scrollDirection = Direction.RIGHT;
		backPosX = backOutPosX = 0.0f;
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("theme-chill.ogg"));
		shootSound = Gdx.audio.newSound(Gdx.files.internal("cast.ogg"));
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));


		gameMusic.setLooping(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		effect = new ParticleEffect();
		effectExplosion = new ParticleEffect();

		effect.load(Gdx.files.internal("explosion.p"), Gdx.files.internal(""));
		effectExplosion.load(Gdx.files.internal("huge-explosion.p"), Gdx.files.internal(""));

		levelsEnemies = new int[]{4,12,38};

		arraySize = levelsEnemies[currentLevel];
		characters = new Character[arraySize];
		Random rn = new Random();


		arraySizeSounds = 6;
		soundsAw =    new Sound [arraySizeSounds];
		for (int i=0;i<arraySizeSounds;i++){
			soundsAw[i] = Gdx.audio.newSound(Gdx.files.internal("aw0"+(i+1)+".ogg"));
		}

		 names = new String[][] {			{"char-3.png"},
			{"char-2.png", "char-3.png"},
			{"char-1.png","char-2.png", "char-3.png"},
			{"char-1.png","char-2.png", "char-final.png"}};
		

		int distStep = 1600/arraySize;

		for (int i=0;i<arraySize;i++)
		{
			int answer = rn.nextInt(names[currentLevel].length);
			String filename = names[currentLevel][answer];
			//			float posX = (i * 70) + (answer*25) + (150*i);

			float posX = 0 + (i * distStep) + (i * 70)+100;
			System.out.println(distStep + " " + posX);
			float posY = rn.nextInt(70);
			int lives = rn.nextInt((currentLevel+1)*3)+5;
			characters[i] = new Character(posX, posY, filename, lives);

		}


		rectHoof = new Rectangle(360,0,hoof.getWidth(), hoof.getHeight());

		points = newPoints;

		//		votingCard = new Vote();
		votingCards = new Vector();
		for (int i=0;i<3*(currentLevel+1);i++)
		{
			addVotingCard();

		}
		for (int i=0;i<arraySize;i++)
		{
			characters[i].update(0);
		}

		timeCounter = spawnCounter = 0.0f;
		shapeRenderer = new ShapeRenderer();
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

				for (int i = votingCards.size()-1; i >= 0; i--)
				{
					Rectangle rectVote = votingCards.get(i).getBoundingRectangle();	

					if ((rectVote.contains(mousePos.x, mousePos.y)) && (votingCards.get(i).life>0)) {
						votingCards.get(i).life -= 1;
						effectExplosion.start();
						effectExplosion.setPosition(mousePos.x, mousePos.y);
						points += 42;
						votingCards.remove(i);
					}
				}

				for (int i=0;i<arraySize;i++)
				{
					Rectangle rect = characters[i].getBoundingRectangle(backPosX);
					//		    		System.out.println(rect.x);

					if ((rect.contains(mousePos.x, mousePos.y)) && (characters[i].life>0)) {
						System.out.println("shoot");
						characters[i].life -= 1;
						if (characters[i].life==0)
						{
							explosionSound.play();
							points += 10;

							effectExplosion.start();
							effectExplosion.setPosition(mousePos.x, mousePos.y);

						}

						Random rn = new Random();
						int soundNumber = rn.nextInt(6);
						System.out.println("shoot");
						soundsAw[soundNumber].play(0.2f);
						points += 1;

					}
				}
				return true;
			}

		});
		gameMusic.play();


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
		timeCounter += delta;

		spawnCounter +=delta;
		if (spawnCounter>8/(currentLevel+1))
		{
			addVotingCard();
			spawnCounter = 0.0f;
		}

		for (int i=0;i<arraySize;i++)
		{
			characters[i].update(delta);
		}
		for (int i = votingCards.size()-1; i >= 0; i--)
		{
			votingCards.get(i).update(delta);
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(backgroundOut,backOutPosX,0);


		game.batch.draw(background,backPosX,0);


		for (int i = votingCards.size()-1; i >= 0; i--)
		{
			if (votingCards.get(i).life>0)
			{
				game.batch.draw(votingCards.get(i).card,
						votingCards.get(i).position.x,votingCards.get(i).position.y);
			}
		}

		//        if(votingCard.life>0)
		//        {
		//        	game.batch.draw(votingCard.card,votingCard.position.x,votingCard.position.y);
		//        }

		for (int i=0;i<arraySize;i++)
		{
			if (characters[i].life>0)
				game.batch.draw(characters[i].currentFrame, backPosX+characters[i].position.x, characters[i].position.y);             // #17
		}


		effect.draw(game.batch, delta);
		effectExplosion.draw(game.batch, delta);


		if (timeCounter<5)
		{        

			game.font60.draw(game.batch, "level: "+(currentLevel+1), 290, 280);


		}

		game.fontBlack.draw(game.batch, "lives: "+deerLives, 30, 472);
		game.fontBlack.draw(game.batch, "points: "+points, 600, 472);
		game.fontBlack.draw(game.batch, "time: "+(int)timeCounter, 300, 472);


		game.font20.draw(game.batch, "cards: "+votingCards.size(), 30, 20);

		int counterDevelopers = 0;
		for (int i=0;i<arraySize;i++)
		{
			if (characters[i].life>0)
				counterDevelopers++;
		}
		game.font20.draw(game.batch, "developers: "+counterDevelopers, 620, 20);

		//        game.font.draw(game.batch, "cards: "+votingCards.size(), 30, 10);
		game.batch.draw(hoof,360,0);

		game.batch.end();

		if (counterDevelopers==0)
		{
			nextLevel();
		}

		// TODO Auto-generated method stub
		if (Gdx.input.isButtonPressed(Buttons.LEFT)){
			//        	game.setScreen(new MainMenuScreen(game));

			//            dispose();
		}




		scrollBackground(delta);

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);

		for (int i = votingCards.size()-1; i >= 0; i--)
		{
			if (votingCards.get(i).life>0)
			{
				if (votingCards.get(i).shootTimeLeft>0)
				{
					float x,y,x2,y2;
					x = votingCards.get(i).position.x;
					y = votingCards.get(i).position.y;
					shapeRenderer.line(x, y, 
							votingCards.get(i).posShoot.x,
							votingCards.get(i).posShoot.y);	
				}


			}

		}
		shapeRenderer.end();


		for (int i = votingCards.size()-1; i >= 0; i--)
		{
			if (votingCards.get(i).life>0)
			{
				if (votingCards.get(i).shootTimeLeft>0)

				{
					if (rectHoof.contains(votingCards.get(i).posShoot))
					{
						votingCards.elementAt(i).shootTimeLeft=0;
						deerLives -= 1;

						if (deerLives==0)
						{
							gameMusic.stop();
							game.setScreen(new GameOverScreen(game, points, deerLives, currentLevel));
							dispose();
						}


					}
				}

			}
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
		Gdx.input.setInputProcessor(new InputAdapter ());
		hoof.dispose();
		gameMusic.dispose();
		shootSound.dispose();
		

	}

	void nextLevel() {
		gameMusic.stop();
		
		if (currentLevel+1==1)
		{
			game.setScreen(new GameFinishScreen(game, points, deerLives, currentLevel));
			dispose();

		} else
		{
			game.setScreen(new GameScreen(game, currentLevel+1, points));
			dispose();
		}
		
		dispose();
	}

}