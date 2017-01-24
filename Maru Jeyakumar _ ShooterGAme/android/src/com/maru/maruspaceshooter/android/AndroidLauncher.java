package com.maru.maruspaceshooter.android;
import android.os.Bundle;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.maru.maruspaceshooter.ShooterGame;

public class AndroidLauncher extends AndroidApplication
{
	 
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new ShooterGame(), config);
		 
	}
	

}
