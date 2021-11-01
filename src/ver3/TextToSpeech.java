package ver3;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
  Voice voice;

  public TextToSpeech(String words) {
    System.setProperty(
        "freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    voice = VoiceManager.getInstance().getVoice("kevin16");
    if (voice != null) {
      voice.allocate();
      try {
        voice.setRate(160);
        voice.setPitch(130);
        voice.setVolume(3);
        voice.speak(words);
      } catch (Exception e1) {
        e1.printStackTrace();
      }

    } else {
      throw new IllegalStateException("Cannot find voice: kevin16");
    }
  }
}
