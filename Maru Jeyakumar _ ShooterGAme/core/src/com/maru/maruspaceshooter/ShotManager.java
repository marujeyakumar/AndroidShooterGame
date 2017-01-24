package com.maru.maruspaceshooter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ShotManager 
{
	private static final int SHOT_SPEED = 300;

	private static final float MINIMUM_TIME_BETWEEN_SHOTS = 0.5f; //this means user only fires every 1/2 a second
	private static final float ENEMY_SHOT_Y_OFFSET = 400;
	//VARIABLE DECLARATION
	private final Texture shotTexture;
	private List<AnimatedSprite> shots = new ArrayList<AnimatedSprite>();
	private float timeSinceLastShot = 0;
	
	//for the sound effect
	private Sound laser = Gdx.audio.newSound(Gdx.files.internal("soundEffectShot.mp3"));
	private List<AnimatedSprite> enemyShots = new ArrayList<AnimatedSprite>();
	private Texture enemyShotTexture;
	
	
	
	
	public ShotManager(Texture shotTexture, Texture enemyShotTexture) 
	{
		this.shotTexture = shotTexture;
		this.enemyShotTexture = enemyShotTexture; 
		
	} //end of constructor ShotManager

	
	
	
	
	
	//this method ensures that the bullet is shot from the middle of the spaceship
	//whenever it's fired in order to create that real effect 
	public void firePlayerShot(int shipCenterXLocation)
	{
		if (canFireShot())
		{
			Sprite newShot = new Sprite(shotTexture);
			AnimatedSprite newShotAnimated = new AnimatedSprite(newShot);
			newShotAnimated.setPosition(shipCenterXLocation, 100);
			newShotAnimated.setVelocity( new Vector2(0,300));
			shots.add(newShotAnimated);
			timeSinceLastShot = 0f; 
			//play the sound
			laser.play(); 
		}


		
	}

	
	
	
	private boolean canFireShot()
	{
		
		return timeSinceLastShot > MINIMUM_TIME_BETWEEN_SHOTS;
	}

	
	
	
	public void update() 
	{
		Iterator<AnimatedSprite> i = shots.iterator(); 
		
		while (i.hasNext())
		{
			AnimatedSprite shot = i.next(); 
			shot.move(); 
			//checking bounds of the bullets 
			if (shot.getY() > ShooterGame.SCREEN_HEIGHT) //if it exceeds the bounds of the height of game
				i.remove(); 
		}
			
      Iterator<AnimatedSprite> j = enemyShots.iterator(); 
		
		while (j.hasNext())
		{
			AnimatedSprite shot = j.next(); 
			shot.move(); 
			//checking bounds of the bullets 
			if (shot.getY() < 0) //if enemy shot exceeds the bounds of the bottom of game screen
				j.remove(); 
		}
			
		timeSinceLastShot += Gdx.graphics.getDeltaTime();  //update the time to help keep track of shots fired 
	}

	public void draw(SpriteBatch batch)
	{
		for (AnimatedSprite shot: shots)
		{
			shot.draw(batch);
		}
		
		for (AnimatedSprite shot: enemyShots)
		{
			shot.draw(batch);
		}
	}






	public void fireEnemyShot(int enemyCenterXLocation)
	{
		Sprite newShot = new Sprite(enemyShotTexture);
		AnimatedSprite newShotAnimated = new AnimatedSprite(newShot);
		newShotAnimated.setPosition(enemyCenterXLocation, ENEMY_SHOT_Y_OFFSET);
		newShotAnimated.setVelocity( new Vector2(0,-SHOT_SPEED));
		enemyShots.add(newShotAnimated);
		
		
	}






	public boolean playerShotTouches(Rectangle boundingBox) 
	{
		return shotTouches(shots, boundingBox); 
	}



	public boolean enemyShotTouches(Rectangle boundingBox)
	{
		return shotTouches(enemyShots, boundingBox);
	}



	private boolean shotTouches(List<AnimatedSprite> shots, Rectangle boundingBox)
	{
		Iterator<AnimatedSprite> i = shots.iterator(); 
		while (i.hasNext())
		{
			AnimatedSprite shot = i.next(); 
			//if it's a hit
			if (Intersector.intersectRectangles(shot.getBoundingBox(), boundingBox, boundingBox))
			{
				i.remove();
				
				return true; 
				//Increments points for the user!!!!
				
			}
		}
		
		return false; 
		
	
	}

	
	
	
	
	
	
	
}//end of ShotManager class
