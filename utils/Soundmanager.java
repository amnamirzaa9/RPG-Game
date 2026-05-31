package rpg.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Soundmanager {
    private Clip backgroundClip;
    FloatControl gainControl;


    public void playBackgroundMusic(String resourcePath) {
        try {
            InputStream is = getClass().getResourceAsStream(resourcePath);
            // Buffer the stream to support "mark/reset" which Clip needs
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);

            gainControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20);

            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();

        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    public void setVolume(float volume) {
        if (backgroundClip != null && backgroundClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            gainControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);

            float volumeLevel = Math.max(volume, 0.0001f);

            float dB = (float) (Math.log(volumeLevel) / Math.log(10.0) * 20.0);

            gainControl.setValue(dB);
        }
    }
}