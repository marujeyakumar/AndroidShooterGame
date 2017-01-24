package com.maru.maruspaceshooter;

public class CollisionManager
{

	private AnimatedSprite spaceshipAnimated;
	private Enemy enemy;
	private ShotManager shotManager;

	public CollisionManager(AnimatedSprite spaceshipAnimated, Enemy enemy, ShotManager shotManager)
	{
				this.spaceshipAnimated = spaceshipAnimated;
				this.enemy = enemy;
				this.shotManager = shotManager;
	}

	public void handleCollisions()
	{
		handleEnemyShot(); 
		handlePlayerShot(); 
		
	}
	
	
	
   //helps handle if the player is shot
	private void handlePlayerShot() 
	{
		if (shotManager.enemyShotTouches(spaceshipAnimated.getBoundingBox()))
		{
			spaceshipAnimated.setDead(true);
		}
		
	}

	//handles if the enemy is shot
	private void handleEnemyShot()
	{
		if (shotManager.playerShotTouches(enemy.getBoundingBox()))
		{
			enemy.hit(); 
		}
		
	}

	
	
	
	
	
	
}//end CollisionManager class
