package com.maru.maruspaceshooter;

/*Name: Maru Jeyakumar
 * Date of Submission: June 17 2014
 * Project: ICS4U Culminating Project
 * Purpose: This is an Android Application - known as space shooter
 * The user touches either of side to move their ship back and forth and each time they tap a side
 * a bullet is shot from the user. The main objective of the game is to kill the aliens which are also
 * shooting green globs of alien poo at the user. */








import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class ShooterGame extends ApplicationAdapter
{
	public static final int SCREEN_HEIGHT = 480;
	public static final int SCREEN_WIDTH = 800;
	
	
	private SpriteBatch batch;
	private Texture background; //texture for the game's background

	private static int userMasterPoints; 
	
	private OrthographicCamera camera; //helps display the images properly onto the game screen
	private AnimatedSprite spaceshipAnimated; 
	private ShotManager shotManager;
	
	//for the background game music
	private Music gameMusic; 
	
	//for the game's enemies
	private Enemy enemy; 
	
	private CollisionManager collisionManager;
	private boolean isGameOver = false; 
	
	
	
	@Override
	public void create () 
	{
		camera = new OrthographicCamera(); 
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		batch = new SpriteBatch();
		
	
		//this texture is for the background of the game
		background = new Texture(Gdx.files.internal("background.png"));
		
		//this texture is for the spaceship in the game
		Texture spaceshipTexture = new Texture(Gdx.files.internal("spaceshipSprite.png"));
		
		//now to create spaceship sprite out of the texture
		//texture will pass it's data to the temporary sprite constructor and the spaceship will get the properties of
		//the texture
		Sprite spaceshipSprite = new Sprite(spaceshipTexture);
		//spaceshipSprite.setPosition(800/2 - (spaceshipSprite.getWidth() / 2) + 50, 0);
		
		spaceshipAnimated = new AnimatedSprite(spaceshipSprite);
		spaceshipAnimated.setPosition(800/2, 0);
		
		//managing the shots
				Texture shotTexture = new Texture(Gdx.files.internal("bulletSprite.png"));
				Texture enemyShotTexture = new Texture(Gdx.files.internal("enemyBulletSprite.png"));
				shotManager = new ShotManager(shotTexture, enemyShotTexture); 
		
		//Texture for the enemy
		Texture enemyTexture = new Texture(Gdx.files.internal("enemySprites.png"));
		enemy = new Enemy(enemyTexture, shotManager);
		
	
		collisionManager = new CollisionManager(spaceshipAnimated, enemy, shotManager);
		
		//load the music
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.mp3"));
		gameMusic.setVolume(.15f);
		gameMusic.setLooping(true);
		gameMusic.play(); 
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(background, 0, 0); //draws background
		
		if (isGameOver)
		{
			//Message on screen to the user that the game is over 
			BitmapFont font = new BitmapFont(); 
			font.setScale(4);
			font.draw(batch, "Player Hit! Game over!!", 100, 200);
		}
		
	
		spaceshipAnimated.draw(batch);
		enemy.draw(batch);
		shotManager.draw(batch);

		
		batch.end();
		
		
	    //call to the method in order to handle the input
	    handleInput();
			
	    if (!isGameOver)
	    {
	    	shotManager.update(); 
			spaceshipAnimated.move();
			enemy.update(); 
			collisionManager.handleCollisions();
	    }
		
		
		if (spaceshipAnimated.isDead())
		{
			isGameOver = true; 
		}
	
	}//end of render (aids in the game loop)

	//this method handles the input, rather it handles where the user touches 
	private void handleInput() 
	{
		if (Gdx.input.isTouched())
		{
			
			
			
			
			if (isGameOver)
			{
				spaceshipAnimated.setDead(false);
				isGameOver = false; 
			}
			
			
			
			
			
			Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPosition);
			
			int xTouch = Gdx.input.getX(); 
	
			//moving spaceship right
			//if the x value of where the user touches is greater than the x coordinate
			//of where the spaceship's centre is, move the spaceship to the right
			if (xTouch > spaceshipAnimated.getX())
			{
				spaceshipAnimated.moveRight(); 
			}
			else //if the spaceship does not move right then the only option is that the spaceship would move left
			{
				spaceshipAnimated.moveLeft(); 
			}
			//managing shots when user touches the screen 
			shotManager.firePlayerShot(spaceshipAnimated.getX());
		
		}//end outer if 
		
		
		
	}
	
	
}//end of public class ShooterGame.java <>