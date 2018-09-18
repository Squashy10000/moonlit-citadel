package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Hashtable;

import Screens.CreditScreen;
import Screens.GameScreen;
import Screens.LoadScreen;
import Screens.MenuScreen;

public class MoonlitCitadel extends Game {

	public static final String TAG = MoonlitCitadel.class.getSimpleName();


	SpriteBatch batch;
	Texture img;

    private LoadScreen loadScreen;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private CreditScreen creditScreen;

    private Hashtable<SCREENTYPE,Screen> screenTable;

    public enum SCREENTYPE{
        LOAD, MENU, GAME, CREDITS
    }

    public void createScreen(SCREENTYPE type){
        Screen screen = null;
        switch(type){
            case GAME:
            	if(gameScreen == null) {
            		gameScreen = new GameScreen(this, batch);
            		screenTable.put(SCREENTYPE.GAME, gameScreen);
				}
            case LOAD:
                if(loadScreen == null){
					loadScreen = new LoadScreen(this, batch);
					screenTable.put(SCREENTYPE.LOAD, loadScreen);
                }
                break;
            case MENU:
            	if(menuScreen == null){
            		menuScreen = new MenuScreen(this, batch);
            		screenTable.put(SCREENTYPE.MENU, menuScreen);
				}
				break;
            case CREDITS:
            	if(creditScreen == null){
            		creditScreen = new CreditScreen(this, batch);
            		screenTable.put(SCREENTYPE.CREDITS, creditScreen);
				}
        }
    }

    public void setScreen(SCREENTYPE type){
    	createScreen(type);
    	setScreen(screenTable.get(type));
	}

	@Override
	public void create () {
		Gdx.app.log(TAG,"In create method for main game class");
    	screenTable = new Hashtable<SCREENTYPE, Screen>();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		setScreen(SCREENTYPE.LOAD);

	}

/*	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}*/
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
