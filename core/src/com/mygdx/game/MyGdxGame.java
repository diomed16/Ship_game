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
import com.badlogic.gdx.InputAdapter;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private SpriteBatch sBackground;
	private Texture tBackground;
	private Texture tPlayerShip;
	private Sound playerShot;
	private Music backgndMusic;
	private OrthographicCamera camera;
	private Viewport viewport;
	private PlayerShip playerShip;
	private Texture tBullet;
	private Vector3 touchDownPos;
	private List<Updatable> updatables;

	private Background background;

	@Override
	public void create () {
		touchDownPos = new Vector3();
		batch = new SpriteBatch();
		sBackground= new SpriteBatch();
		background = new Background("tail_backgnd.png");
	camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new FitViewport(640, 800, camera);  // Задаем желаемые пропорции
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

		tBackground = new Texture(Gdx.files.internal("Background.png"));
		tPlayerShip = new Texture(Gdx.files.internal("user_ship2.png"));
		tBullet = new Texture(Gdx.files.internal("bullet.png"));
		playerShot = Gdx.audio.newSound(Gdx.files.internal("shoot_user.wav"));
		backgndMusic = Gdx.audio.newMusic(Gdx.files.internal("game_title.wav"));
		// start the playback of the background music immediately
		backgndMusic.setLooping(true);
		backgndMusic.play();

		float originalWidth = 137;//Примеряемся для правильной стартовой позиции корабля
		float originalHeight = 216;
		float maxWidth = 128;
		float maxHeight = 128;
		float scaleWidth = maxWidth / originalWidth;
		float scaleHeight = maxHeight / originalHeight;
		float scale = Math.min(scaleWidth, scaleHeight);
		int scaledWidth = (int) (originalWidth * scale);
		int scaledHeight = (int) (originalHeight * scale);
		float startX = (Gdx.graphics.getWidth() - scaledWidth) / 2;
		float startY = 20;
		playerShip = new PlayerShip(tPlayerShip, tBullet, startX, startY);//создаём корабль с учётом кода выше


		Gdx.input.setInputProcessor(new InputAdapter() {
			private boolean isDragging = false;
			private boolean hasMoved = false;
			private Vector3 initialTouchPos = new Vector3();
			private Vector3 touchPos = new Vector3();
			private float offsetX;

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				touchPos.set(screenX, screenY, 0);
				camera.unproject(touchPos);
				float shipX = playerShip.getX();
				float shipY = playerShip.getY();
				float shipWidth = playerShip.getWidth();
				float shipHeight = playerShip.getHeight();

				if (touchPos.x >= shipX && touchPos.x <= shipX + shipWidth && touchPos.y >= shipY && touchPos.y <= shipY + shipHeight) {
					isDragging = true;
					hasMoved = false;
					initialTouchPos.set(touchPos);
					offsetX = touchPos.x - shipX;  // Save the offset from the touch point to the ship's position
					return true;
				}
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				if (isDragging) {
					touchPos.set(screenX, screenY, 0);
					camera.unproject(touchPos);
					if (!hasMoved && touchPos.dst(initialTouchPos) > 10) {
						hasMoved = true;
					}
					playerShip.setPosition((int)(touchPos.x - offsetX));  // Use the saved offset to position the ship
					return true;
				}
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				if (isDragging) {
					isDragging = false;
					touchPos.set(screenX, screenY, 0);
					camera.unproject(touchPos);

					if (!hasMoved) {
						playerShip.shoot();
						playerShot.play();
					}
					return true;
				}
				return false;
			}
		});
	}


	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		// tell the camera to update its matrices.


		float deltaTime = Gdx.graphics.getDeltaTime();
		sBackground.begin();
		background.render(sBackground);
		sBackground.end();
		playerShip.update(deltaTime);
		batch.begin();
		playerShip.draw(batch);
		batch.end();


	}



	@Override
	public void resize(int width, int height) {
		//viewport.update(width, height, true);  // Обновление viewport при изменении размеров окна
	}
	@Override
	public void dispose () {
		sBackground.dispose();
		background.dispose();
		playerShip.dispose();
		batch.dispose();
	}
}
