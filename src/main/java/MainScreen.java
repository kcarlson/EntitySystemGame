import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainScreen extends ScreenAdapter {
  private final MyGame game;
  private final OrthographicCamera guiCam;

  public MainScreen(final MyGame game) {
    this.game = game;
    this.guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
  }

  public void draw () {
		final GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		// game.batcher.disableBlending();
		// game.batcher.begin();
		// game.batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
		// game.batcher.end();

		// game.batcher.enableBlending();
		// game.batcher.begin();
		// game.batcher.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
		// game.batcher.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
		// game.batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
		// game.batcher.end();
	}

	@Override
	public void render (float delta) {
		// update();
		draw();
	}
}