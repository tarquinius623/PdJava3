package official;

import java.io.IOException;

import org.puredata.core.PdBase;


public class OfficialTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		JavaSoundThread audioThread = new JavaSoundThread(44100, 2, 16);
		int patch = PdBase.openPatch("src/test3.pd");
		audioThread.start();
		Thread.sleep(1000);
		int success = PdBase.sendFloat("freq", (float) 1000);
		success = PdBase.sendFloat("volume", (float) 0.6);
		System.out.println(success);
		Thread.sleep(1000);
		success = PdBase.sendFloat("freq", (float) 440);
		System.out.println(success);
		Thread.sleep(1000);
		success = PdBase.sendFloat("freq", (float) 880);
		System.out.println(success);
		Thread.sleep(2000);
		audioThread.interrupt();
		audioThread.join();
		PdBase.closePatch(patch);
	}

}
