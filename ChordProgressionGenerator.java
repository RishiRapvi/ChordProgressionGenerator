import javax.sound.midi.*;
import java.util.Arrays;
import java.util.List;

public class ChordProgressionGenerator {
    public static void generateMIDI(Sequence sequence, List<String> chordProgression, int numberOfBeats, int notesPerChord, int ticksPerBeat, List<String> chordsInSameBeat) throws InvalidMidiDataException {
        Track track = sequence.createTrack();

        int currentChordIndex = 0;


        for (int beat = 0; beat < numberOfBeats; beat++) {
            for (int i = 0; i < notesPerChord; i++) {
                String currentChord;
                if (i < chordsInSameBeat.size()) {
                    currentChord = chordsInSameBeat.get(i);
                } else {
                    currentChord = chordProgression.get(currentChordIndex);
                }

                // Directly including the chords without ellipses
                List<Integer> currentChordNotes = getChordNotes(currentChord);

                for (int note : currentChordNotes) {
                    ShortMessage noteOn = new ShortMessage();
                    noteOn.setMessage(ShortMessage.NOTE_ON, 0, note, 64);
                    track.add(new MidiEvent(noteOn, (beat * notesPerChord + i) * ticksPerBeat));

                    ShortMessage noteOff = new ShortMessage();
                    noteOff.setMessage(ShortMessage.NOTE_OFF, 0, note, 64);
                    track.add(new MidiEvent(noteOff, (beat * notesPerChord + i + 1) * ticksPerBeat)); // Duration: 1/n of a beat


                }
            }
            currentChordIndex = (currentChordIndex + 1) % chordProgression.size();
        }
    }

    public static String[] getAllChordNames() {
        return new String[]{"C_MAJOR", "D_MINOR", "E_MINOR", "F_MAJOR", "G_MAJOR", "A_MINOR", "B_DIMINISHED"};
    }

    private static List<Integer> getChordNotes(String chordName) {
        switch (chordName) {
            case "C_MAJOR":
                return Arrays.asList(60, 64, 67);
            case "D_MINOR":
                return Arrays.asList(62, 65, 69);
            case "E_MINOR":
                return Arrays.asList(64, 67, 71);
            case "F_MAJOR":
                return Arrays.asList(65, 69, 72);
            case "G_MAJOR":
                return Arrays.asList(67, 71, 74);
            case "A_MINOR":
                return Arrays.asList(69, 72, 76);
            case "B_DIMINISHED":
                return Arrays.asList(71, 74, 77);
            default:
                return Arrays.asList(); // Handle unknown chords
        }

    }
}
