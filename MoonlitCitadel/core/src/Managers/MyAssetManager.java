package Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MyAssetManager {

    private static final String TAG = MyAssetManager.class.getSimpleName();

    private AssetManager assetManager;

    public MyAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void unloadAsset(String assetFilePath){
        if(assetManager.isLoaded(assetFilePath)){
            assetManager.unload(assetFilePath);
        }
        else {
            Gdx.app.log(TAG, "asset not in memory: " + assetFilePath);
        }
    }
    public float loadCompleted(){
        return assetManager.getProgress();
    }
    public int numberAssetsQueued(){
        return assetManager.getQueuedAssets();
    }
    public boolean updateAssetloading(){
        return assetManager.update();
    }
    public boolean isAssetLoaded(String fileName){
        return assetManager.isLoaded(fileName);
    }
    public void loadMapAsset(String filePath){

        if (filePath == null || filePath.isEmpty()){
            return;
        }
        if(assetManager.isLoaded(filePath)){
            return;
        }
        if(assetManager.getFileHandleResolver().resolve(filePath).exists()){
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));
            assetManager.load(filePath,TiledMap.class);

          //  assetManager.finishLoadingAsset(filePath);
            Gdx.app.log(TAG, "Map loaded: " + filePath);
        }
        else{
            Gdx.app.log(TAG, "Map doesn't exist: " +filePath);
        }
    }
    public TiledMap getMapAsset(String filePath){
        TiledMap map = null;
        if (assetManager.isLoaded(filePath)){
            map = assetManager.get(filePath,TiledMap.class);
            return map;
        }
        else {
            Gdx.app.log(TAG, "Map is not loaded: " +filePath);
            return map;

        }
    }
    public void loadTextureAsset(String filePath){

        if(assetManager.isLoaded(filePath)){
            return;
        }
        if (filePath == null || filePath.isEmpty()){
            return;
        }
        if(assetManager.getFileHandleResolver().resolve(filePath).exists()){
            assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(assetManager.getFileHandleResolver()));
            assetManager.load(filePath,TextureAtlas.class);

           // assetManager.finishLoadingAsset(filePath);
            Gdx.app.log(TAG, "texture loaded: " + filePath);
        }
        else{
            Gdx.app.log(TAG, "texture doesn't exist: " +filePath);
        }
    }
    public TextureAtlas getTextureAsset(String filePath){
        TextureAtlas atlas = null;
        if (assetManager.isLoaded(filePath)){
            atlas = assetManager.get(filePath,TextureAtlas.class);
            return atlas;
        }
        else {
            Gdx.app.log(TAG, "Map is not loaded: " +filePath);
            return atlas;

        }
    }

    public void dispose(){
        assetManager.dispose();
    }
}
