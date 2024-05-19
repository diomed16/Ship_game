package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private SpriteBatch sPlayerShip;
	private Texture background;
	private Texture tPlayerShip;
	private Sound playerShot;
	private Music backgndMusic;
	private OrthographicCamera camera;
	//private Rectangle playerShip;
	private Viewport viewport;
	private PlayerShip playerShip;
	private Texture tBullet;

	private List<Updatable> updatables;

	@Override
	public void create () {

		camera = new OrthographicCamera();
		//camera.setToOrtho(false,640,800);
		viewport = new FitViewport(640, 800, camera);  // Задаем желаемые пропорции
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		sPlayerShip = new SpriteBatch();
		background = new Texture(Gdx.files.internal("Background.png"));
		tPlayerShip = new Texture(Gdx.files.internal("user_ship2.png"));
		tBullet = new Texture(Gdx.files.internal("User_Bullet.png"));
		playerShot = Gdx.audio.newSound(Gdx.files.internal("shoot_user.wav"));
		backgndMusic = Gdx.audio.newMusic(Gdx.files.internal("game_title.wav"));
		// start the playback of the background music immediately
		backgndMusic.setLooping(true);
		backgndMusic.play();

		float originalWidth = 137;
		float originalHeight = 216;

// Максимальный размер области
		float maxWidth = 128;
		float maxHeight = 128;

// Рассчитываем масштаб для ширины и высоты
		float scaleWidth = maxWidth / originalWidth;
		float scaleHeight = maxHeight / originalHeight;

// Выбираем наименьший масштаб, чтобы изображение вписалось в область и сохраняло пропорции
		float scale = Math.min(scaleWidth, scaleHeight);

// Вычисляем новые размеры изображения
		int scaledWidth = (int) (originalWidth * scale);
		int scaledHeight = (int) (originalHeight * scale);

// Задаём новые размеры и позицию прямоугольника

		//playerShip.width = scaledWidth;
		//playerShip.height = scaledHeight;
		float startX = 320 - scaledWidth / 2; // Центрируем изображение по горизонтали
		float startY = 20; // Позиция по вертикали остаётся 20
		playerShip = new PlayerShip(tPlayerShip, tBullet, startX, startY);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		// tell the camera to update its matrices.


		float deltaTime = Gdx.graphics.getDeltaTime();
		playerShip.update(deltaTime);
		batch.begin();
		playerShip.draw(batch);
		batch.end();
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
			camera.unproject(touchPos);
			playerShip.setPosition(touchPos.x - 128 / 2);
			playerShip.shoot();
		}

	}

	private void updateGame(float deltaTime) {
		for (Updatable updatable : updatables) {
			updatable.update(deltaTime);
		}
		// Здесь могут быть добавлены дополнительные вызовы, например, для проверки столкновений
	}
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);  // Обновление viewport при изменении размеров окна
	}
	@Override
	public void dispose () {
		playerShip.dispose();
		batch.dispose();
	}
}
