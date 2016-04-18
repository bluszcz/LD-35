package devcarpet.net.ld35test001.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import devcarpet.net.ld35test001.LD35001;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		config.title = "Deershifter";
		config.addIcon("logo-128.png", Files.FileType.Internal);
		config.addIcon("logo-32.png", Files.FileType.Internal);
		config.addIcon("logo-16.png", Files.FileType.Internal);

		new LwjglApplication(new LD35001(), config);
	}
}
