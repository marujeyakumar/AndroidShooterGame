package com.maru.maruspaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AnimatedSprite 
{
	private static final int SHIP_SPEED = 300;
	//these constants keep track of the number of frames that the animated sprite sheet has
	//it's divided into rows and columns
	private static final int FRAMES_COL = 2; 
	private static final int FRAMES_ROW = 2; 
	
	
	//variable declaration
	private Sprite sprite; 
	private Animation animation; 
	private TextureRegion[] frames; 
	private TextureRegion currentFrame; 
	private Vector2 velocity = new Vector2(); //initializing it gives a 0,0 velocity  
	
	private float stateTime;
	private boolean isDead = false;  
	public AnimatedSprite(Sprite sprite)
	{
		this.sprite = sprite; 
		Texture texture = sprite.getTexture(); 
		//temporary 2D array to hold the initial table of frames. 
		TextureRegion[][] temp = TextureRegion.split(texture, (int) getSpriteWidth(), texture.getHeight() / FRAMES_ROW);
		frames = new TextureRegion[FRAMES_COL * FRAMES_ROW]; //instatiate size of array frames
		
		int index = 0;
		//fill the frames array up with pictures 
		for( int i = 0; i < FRAMES_ROW; i++)
		{
			for (int j = 0; j < FRAMES_COL; j++)
			{
				frames[index++]  = temp[i][j];
			}
			
		}
		
		//animation to play the frames 
		animation = new Animation(0.1f, frames);
		stateTime = 0f; 
		
		
;	}//end of public AnimatedSprite
	
	public void draw(SpriteBatch spriteBatch)
	{
		stateTime += Gdx.graphics.getDeltaTime(); 
		currentFrame = animation.getKeyFrame(stateTime, true);
		
		spriteBatch.draw(currentFrame, sprite.getX(), sprite.getY());
	}//end of method public void draw 
	
	
	public void setPosition(float x, float y)
	{
		sprite.setPosition(x - getSpriteCenterOffset(), y);
	}//end of method setPosition
	
	
	//just some boring calculations
	public float getSpriteCenterOffset()
	{
		return getSpriteWidth() / 2; 
	}
	
	
	
	private float getSpriteWidth() {
		// TODO Auto-generated method stub
		return sprite.getWidth() / FRAMES_COL;
	}

	
	
	//method to move the ship to the right
	public void moveRight()
	{
		velocity = new Vector2(SHIP_SPEED, 0); //moves 300 pixels per second in x direction and no movement in y direction
	}
	public void moveLeft()
	{
		velocity = new Vector2(-SHIP_SPEED, 0); //moves 300 pixels per second in x direction and no movement in y direction
	}

	//get the x coordinate of the middle of the spritesheet 
	public int getX() 
	{
		return (int) (sprite.getX() + getSpriteCenterOffset());
	}

	
	
	//to legitimately move the spaceship 
	public void move() 
	{
		int xMovement = (int) (velocity.x * Gdx.graphics.getDeltaTime()); 
		int yMovement = (int) (velocity.y * Gdx.graphics.getDeltaTime());
		sprite.setPosition(sprite.getX() + xMovement, sprite.getY() + yMovement);
		
		//CHECKING FOR BOUNDARIES WHEN THE SHIP IS MOVING
		//left boundary 
		if (sprite.getX()  < 0)
		{
			sprite.setX(0);
			
		}
		//right boundary 
		if (sprite.getX() + getSpriteWidth() > ShooterGame.SCREEN_WIDTH)
		{
			sprite.setX(ShooterGame.SCREEN_WIDTH - getSpriteWidth());
			
		}
	}//end of public void move 

	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity; 
		
	}

	//aids in cleaning up the shots
	public int getY()
	{
		return (int) sprite.getY(); 
		
	}

	public int getWidth()
	{
		return (int) getSpriteWidth(); 
	}

	public int getHeight()
	{
		return (int)sprite.getHeight() / FRAMES_ROW; 
	}

	//inverts the direction (Changes it from positive to negative)
	public void changeDirection()
	{
		velocity.x = -velocity.x; 
		
	}

	public Rectangle getBoundingBox()
	{
		return new Rectangle(sprite.getX(), sprite.getY(), getWidth(), getHeight());
	}

	//setting the state of deadness for the enemy
	public void setDead(boolean isDead) 
	{
		this.isDead = isDead; 
		
	}

	
	//checking to see if this the animatedSprite in enemy class is dead or not
	//to ensure that we are only drawing enemies that are alive
	//and leaving out the ones that have been shot by the user
	public boolean isDead()
    {
		return isDead; 
	}

	
	
	
	
	
	
}//end of AnimatedSprite class
