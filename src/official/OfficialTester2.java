package official;

import java.io.IOException;

import org.puredata.core.PdBase;

public class OfficialTester2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		JavaSoundThread audioThread = new JavaSoundThread(44100, 2, 16);
		int patch = PdBase.openPatch("src/test2.pd");
		audioThread.start();
		int success = 0;
		boolean b = audioThread.isAlive();
		//success = PdBase.sendBang("file");
		System.out.println(b);
		Thread.sleep(100000);
		audioThread.interrupt();
		audioThread.join();
		PdBase.closePatch(patch);
	}

}
