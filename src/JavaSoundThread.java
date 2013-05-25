import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.puredata.core.PdBase;


public class JavaSoundThread extends Thread {

	private final float sampleRate;
	private final int outChans;
	private final int ticks;
	private volatile boolean terminated;
	
	public JavaSoundThread(float sampleRate, int outChans, int ticks) {
		this.sampleRate = sampleRate;
		this.outChans = outChans;
		this.ticks = ticks;
		PdBase.openAudio(0, outChans, (int) sampleRate);
		PdBase.computeAudio(true);
		setPriority(Thread.MAX_PRIORITY);
	}
	
	@Override
	public void run() {
		terminated = false;
		try {
			perform();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void interrupt() {
		terminated = true;
		super.interrupt();
	}
	
	private void perform() throws LineUnavailableException {
		int sampleSize = 2;
		AudioFormat audioFormat = new AudioFormat(sampleRate, 8 * sampleSize, outChans, true, true);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
		sourceDataLine.open(audioFormat);
		sourceDataLine.start();
		
		int frames = PdBase.blockSize() * ticks;
		short[] dummy = new short[0];
		short[] samples = new short[frames * outChans];
		byte[] rawSamples = new byte[samples.length * sampleSize];
		ByteBuffer buf = ByteBuffer.wrap(rawSamples);
		ShortBuffer shortBuf = buf.asShortBuffer();
		
		while (!terminated) {
			PdBase.process(ticks, dummy, samples);
			shortBuf.rewind();
			shortBuf.put(samples);
			sourceDataLine.write(rawSamples, 0, rawSamples.length);
		}
		
		sourceDataLine.drain();
		sourceDataLine.stop();
		sourceDataLine.close();
	}
}
