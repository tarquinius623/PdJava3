package official;

import java.io.IOException;

import org.puredata.core.PdBase;
import org.puredata.core.PdListener;

public class OfficialTester2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		JavaSoundThread audioThread = new JavaSoundThread(44100, 2, 2);
		int patch = PdBase.openPatch("src/test2.pd");
		//PdUiDispatcher d = new PdUiDispatcher();
		//PdBase.setReceiver(d);
		//d.addListener("pizza", new PdListener.Adapter());
		audioThread.start();
		int success = 0;
		success = PdBase.sendBang("file");
		System.out.println(success);
		/*Thread.sleep(5000);
		audioThread.interrupt();
		audioThread.join();
		PdBase.closePatch(patch);*/
	}

}
