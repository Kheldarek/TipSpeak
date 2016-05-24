package org.ps.sound;

import javax.sound.sampled.*;

/**
 * Created by psend on 24.05.2016.
 */
public class SoundCapturer
{

	TargetDataLine line;

	public void SetupDataLine()
	{/*Encoding encoding, float sampleRate, int sampleSizeInBits,int channels, int frameSize, float frameRate, boolean bigEndian*/
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 2, 32, 44100.0f, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info))
		{
			System.err.println("LINE NOT SUPPORTED");

		}
		try
		{
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
		} catch (LineUnavailableException ex)
		{
			System.err.println("LINE UNAVALIBLE");
		}
	}
}
