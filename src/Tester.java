import java.io.IOException;

import org.puredata.core.*;


public class Tester {

	/**
	 * @param args
	 * @throws InterruptedException, IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		JavaSoundThread audioThread = new JavaSoundThread(44100, 2, 16);
		int patch = PdBase.openPatch("src/test.pd");
		audioThread.start();
		Thread.sleep(1000);
		audioThread.interrupt();
		audioThread.join();
		PdBase.closePatch(patch);
	}

}
