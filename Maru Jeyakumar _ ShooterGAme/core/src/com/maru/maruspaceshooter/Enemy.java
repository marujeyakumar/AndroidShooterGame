package com.maru.maruspaceshooter;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy
{
	private static final float ENEMY_SPEED = 250;
	private final Texture enemyTexture; 
	private AnimatedSprite animatedSprite;
	private final ShotManager shotManager;
	private float spawnTimeOut = 0f; 

	//constructor for enemy
	public Enemy(Texture enemyTexture, ShotManager shotManager) 
	{
		this.enemyTexture = enemyTexture;
		this.shotManager = shotManager; 
		
		//method to spawn the enemy
		spawn(); 
	}

	//this method spawns the enemies newly onto the screen, and
	//is also used for respawning things
	private void spawn() 
	{
		Sprite enemySprite = new Sprite(enemyTexture);
		animatedSprite = new AnimatedSprite(enemySprite);
		
		//this randomizes the x coordinate at which the enemy spawns 
		int xPosition = createRandomPosition(); 
		
		animatedSprite.setPosition(xPosition, ShooterGame.SCREEN_HEIGHT - animatedSprite.getHeight());
		
		//setting the velocity for the enemy so it could move
		animatedSprite.setVelocity(new Vector2(ENEMY_SPEED, 0));
		
		//when the enemy respawns set their dead state to false because they're not dead
		animatedSprite.setDead(false);
	}

	//this method sets the random position for the enemy 
	//randomizes their movement 
	private int createRandomPosition() 
	{
		Random random = new Random(); 
		int randomNumber = random.nextInt(ShooterGame.SCREEN_WIDTH - animatedSprite.getWidth() + 1);
		return randomNumber  + animatedSprite.getWidth() / 2; 
	}

	public void draw(SpriteBatch batch)
	{
		//if the enemy is dead we shouldn't draw
		//so we'll only end up drawing the alive enemies 
		if (!animatedSprite.isDead())
		{
			animatedSprite.draw(batch);
		}
		
		
	}

	public void update()
	{
		//making sure we handle all the necessary actions if the enemy is dead
		if (animatedSprite.isDead())
		{
			spawnTimeOut -= Gdx.graphics.getDeltaTime(); 
			if (spawnTimeOut <= 0)
			{
				//it's time to respawn
				spawn(); 
			}
		}
		else
		{
		
			if (shouldChangeDirection())
			{
				animatedSprite.changeDirection(); 
			}
			
			if (shouldFire())
			{
				shotManager.fireEnemyShot(animatedSprite.getX());
			}
			
			animatedSprite.move(); 
		}
		
	}

	
	
	private boolean shouldFire()
	{
		Random random = new Random(); 
		return random.nextInt(51) ==0;
	}

	//this method helps us determine whether the direction of the enenmy should actually be changed
	private boolean shouldChangeDirection() 
	{
		Random random = new Random(); 
		return random.nextInt(21) ==0;
	}
	
	
	
	

	public Rectangle getBoundingBox()
	{
		return animatedSprite.getBoundingBox(); 
	}

	public void hit() 
	{
		animatedSprite.setDead(true);
		spawnTimeOut = 1f; 
	}

}//end of Enemy class
