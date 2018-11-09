package Helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameInput extends InputAdapter implements GestureDetector.GestureListener {
    private Viewport gameViewport;
    private OrthographicCamera camera;
    private Vector3 touch;

    private boolean left, right, up, down;

    public GameInput(Viewport gameViewport){
        super();
        this.gameViewport = gameViewport;
        camera = (OrthographicCamera) gameViewport.getCamera();
        touch = new Vector3(Vector3.Zero);
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(count ==1){
            touch.x = x;
            touch.y = y;

            gameViewport.unproject(touch);
            touch.set(MathUtils.floor(touch.x), MathUtils.floor(touch.y), 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean keyPressed = false;
        switch (keycode){
            case Input.Keys.A:
                left = true;
                keyPressed = true;
                break;
            case Input.Keys.W:
                up = true;
                keyPressed = true;
                break;
            case Input.Keys.D:
                right = true;
                keyPressed = true;
                break;
            case Input.Keys.S:
                down = true;
                keyPressed = true;
                break;
        }


        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
