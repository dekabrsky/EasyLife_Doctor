package gree.uniq.minigameleaves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class TreeScreen implements Screen {

	final Leave game;
	OrthographicCamera camera;
	Texture texture;
	Texture yellowbox;
	Texture trees;
	Texture leave;
	TextureRegion bg;
	Music music;
	Rectangle box1;
	Vector3 touchbox1;
	Array<Rectangle> leaves;
	long lastdropleave;
	int dropleaves;
	BitmapFont font;
	private float timeSeconds = 60f;
	private float period = 0f;

	public TreeScreen(final Leave gam) {
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		touchbox1 = new Vector3();

		font = new BitmapFont();

		yellowbox = new Texture("boxyellow.png");
		trees = new Texture("tree.png");
		leave = new Texture("leave.png");
		texture = new Texture(Gdx.files.internal("tree.png"));
		bg = new TextureRegion(texture,0, 0, 720, 1566);

		music = Gdx.audio.newMusic(Gdx.files.internal("musicback.mp3"));
		music.setLooping(true);
		music.play();

		box1 = new Rectangle();
		box1.x = Gdx.graphics.getHeight() - 64;
		box1.y = 20;
		box1.width = 180;
		box1.height = 180;

		leaves = new Array<Rectangle>();
		spawnLeavedrop();
	}

	private void spawnLeavedrop(){
		Rectangle leavedrop = new Rectangle();
		leavedrop.x = MathUtils.random(0, Gdx.graphics.getHeight()-64);
		leavedrop.y = Gdx.graphics.getWidth();
		leavedrop.width = 102;
		leavedrop.height = 150;
		leaves.add(leavedrop);
		lastdropleave = TimeUtils.nanoTime();
	}

	@SuppressWarnings("DefaultLocale")
	@Override
	public void render(float delta) {
		ScreenUtils.clear(1, 1, 1, 1);
		camera.update();
		game.batch.begin();
		game.batch.draw(bg,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font.draw(game.batch, "Поймано листочков:" + dropleaves, 0, Gdx.graphics.getHeight()-40);
		font.getData().setScale(5);
		font.setColor(Color.BLACK);
		game.batch.draw(yellowbox, box1.x, box1.y, 351, 363);
		for (Rectangle leavedrop: leaves){
			game.batch.draw(leave,leavedrop.x,leavedrop.y, 102, 150);
		}
		timeSeconds -=Gdx.graphics.getRawDeltaTime();
		if(timeSeconds > period){
			timeSeconds -= period;
		} else {
			timeSeconds = 0f;
			game.setScreen(new MenuScreen(game));
			dispose();
		}
		font.draw(game.batch, "00:"+ String.format("%.0f",timeSeconds), Gdx.graphics.getWidth()/2-40, Gdx.graphics.getHeight()-150);
		game.batch.end();

		if(Gdx.input.isTouched()){
			touchbox1.set(Gdx.input.getX(), Gdx.input.getY(),0);
			camera.unproject(touchbox1);
			box1.x = (int) (touchbox1.x - 64/2);
		}

		if(TimeUtils.nanoTime() - lastdropleave > 1000000000) spawnLeavedrop();

		Iterator<Rectangle> iter = leaves.iterator();
		while (iter.hasNext()){
			Rectangle leavedrop = iter.next();
			leavedrop.y -= 300 * Gdx.graphics.getDeltaTime();
			if (leavedrop.y + 64 < 0) iter.remove();
			if (leavedrop.overlaps(box1)){
				dropleaves++;
				iter.remove();
			}
		}
	}

	@Override
	public void show() {
		music.play();
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
	public void dispose () {
		yellowbox.dispose();
		trees.dispose();
		music.dispose();
		font.dispose();
	}
}