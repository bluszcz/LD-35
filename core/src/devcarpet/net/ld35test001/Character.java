package devcarpet.net.ld35test001;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
public class Character {
    private static final int        FRAME_COLS = 2;         // #1
    private static final int        FRAME_ROWS = 1;         // #2
    Texture                         walkSheet;              // #4
    TextureRegion[]                 animFrames;             // #5
    TextureRegion                   currentFrame;           // #7	
	float stateTime;       
    public Vector2 position;

    Animation                       charAnimation;          // #3
    public Polygon shape;
	int life;
    PolygonSprite poly;
    PolygonSpriteBatch polyBatch;
    Texture textureSolid;
    public ShapeRenderer shapeRenderer;
    
    public float vertices[];
    public Rectangle getBoundingRectangle(float backPos){
    	int height = currentFrame.getRegionHeight();
    	int width = currentFrame.getRegionWidth();
    	Rectangle rect = new Rectangle(position.x+backPos, position.y, width/FRAME_COLS, height);
		return rect;
    	
    };
	public Character(float posX, float posY, String filename, int lives){
		life = lives;
		polyBatch = new PolygonSpriteBatch(); // To assign at the beginning
		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pix.setColor(0xDEADBEFF); // DE is red, AD is green and BE is blue.
		pix.fill();
		position = new Vector2(posX,posY);
//		position.x = 0;
//		position.y = 0;
//		
		 walkSheet = new Texture(Gdx.files.internal(filename)); // #9
		 
	     TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
	     animFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index = 0;

	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	            	animFrames[index++] = tmp[i][j];
	            }
	        }
	        charAnimation = new Animation(1.0f, animFrames);      // #11
	        stateTime = 0f;                         // #13
	        shape  = new Polygon();
	       vertices = new float[] {
	        	    -2,   -2,
	        	    -2,    2,
	        	    -0.5f, 2,
	        	    -0.5f, 3,
	        	     0.5f, 3,
	        	     0.5f, 2,
	        	     2,    2,
	        	     2,   -2
	        	};
	        shape.setVertices(vertices);
	        
	        
	        shapeRenderer = new ShapeRenderer();
	        shapeRenderer.setAutoShapeType(true);
			 shapeRenderer.begin(ShapeType.Line);
			 shapeRenderer.setColor(1, 1, 0, 1);
			 shapeRenderer.polygon(vertices);
			 shapeRenderer.end();
			 
			 
	        textureSolid = new Texture(pix);
//	        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid),vertices, null);
//	        poly = new PolygonSprite(polyReg);
//	        poly.setOrigin(a, b);
//	        polyBatch = new PolygonSpriteBatch();
	        
	}

	void update(float delta)
	{
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = charAnimation.getKeyFrame(stateTime, true);  // #16
        
	}

	
	
}
