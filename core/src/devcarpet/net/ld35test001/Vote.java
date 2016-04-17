package devcarpet.net.ld35test001;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import devcarpet.net.ld35test001.LD35001.Direction;

public class Vote {

	Vector2 position;
	Direction direction;

	Vector2 posShoot;
	public float shootTimeLeft;
	
	
	
	int variety;
	
	int currentLevel;
	float osc;
	int life;
	Texture card;
	public Vote(int level) {
		card = new Texture("votingcard.png");
		currentLevel = level+1;
		
		Random rn = new Random();

		
		variety = rn.nextInt(10)+1;
		
		position = new Vector2(0,200);
		position.x = rn.nextInt(1500);
		position.y = rn.nextInt(400)
				;
		direction = Direction.RIGHT;
		posShoot = new Vector2(0,0);
		osc = 0.0f;
		life = 1;
		
	}
	
	void update(float delta)
	{
    	float move;
    	
    	float speed = 0.03f * currentLevel;
    	

		move = (float) (speed/delta);
		if (direction==Direction.RIGHT)
		{
			position.x += move;
		}
		else if (direction==Direction.LEFT)
		{
			position.x -= move;
		} 
		
		if (position.x>1600)
		{
			direction=Direction.LEFT;
			
		}
		if (position.x<-800)
		{
			direction=Direction.RIGHT;
			
		}		
		osc += delta*20/variety;
		float result = MathUtils.sin(osc);
		position.y += result;
		
		if (shootTimeLeft>0)
		{
			shootTimeLeft -= delta;
			
		}
		
		if (shootTimeLeft<0)
			shootTimeLeft=0;
		
		
		Random rn = new Random();
		int resultDice = rn.nextInt(400);
		if (resultDice>390)
			makeShoot();
		
		
	}
	
	Rectangle getBoundingRectangle() {
		int width = card.getWidth();
		int height = card.getHeight();
		Rectangle rect = new Rectangle(position.x, position.y, width, height);
		return rect;
		
	}
	
	void makeShoot(){
		if ((position.x>0) && (position.x<800))
		{
			Random rn = new Random();
			posShoot.x = rn.nextInt(500)+200;
			posShoot.y = rn.nextInt(200);
			shootTimeLeft = 0.1f;
		}
	}
	
	
}
