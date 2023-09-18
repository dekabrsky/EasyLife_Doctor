package gree.uniq.minigameleaves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {

    final Leave game;
    OrthographicCamera camera;
    TextureRegion bg;
    Texture texture;

    public MenuScreen(Leave gam){
        this.game = gam;

        texture = new Texture(Gdx.files.internal("tree.png"));
        bg = new TextureRegion(texture,0, 0, 720, 1566);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.begin();
        game.batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.font.draw(game.batch, "Нажми, чтобы начать игру!", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/2);
        game.font.getData().setScale(6);
        game.font.setColor(Color.WHITE);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new TreeScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
