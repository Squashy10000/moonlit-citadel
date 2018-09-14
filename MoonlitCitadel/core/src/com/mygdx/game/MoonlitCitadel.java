package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screens.CreditScreen;
import Screens.GameScreen;
import Screens.LoadScreen;
import Screens.MenuScreen;

public class MoonlitCitadel extends Game {
	SpriteBatch batch;
	Texture img;

    private LoadScreen loadScreen;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private CreditScreen creditScreen;

    public enum SCREENTYPE{
        LOAD, MENU, GAME, CREDITS
    }

    public void createScreen(SCREENTYPE type){
        Screen screen = null;
        switch(type){
            case GAME:
            case LOAD:
                if(loadScreen == null){

                }
            case MENU:
            case CREDITS:
        }
    }
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
