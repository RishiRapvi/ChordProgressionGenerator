import javax.sound.midi.*;
import java.util.Arrays;
import java.util.List;

public class ChordProgressionGenerator {
    public static void generateMIDI(Sequence sequence, List<List<String>> chordProgression, int ticksPerBeat, List<String> chordsInSameBeat) throws InvalidMidiDataException {
        Track track = sequence.createTrack();

        for (int beat = 0; beat < chordProgression.size(); beat++) {
            List<String> chordsForBeat = chordProgression.get(beat);
            int notesPerChord = chordsForBeat.size();

            for (int i = 0; i < notesPerChord; i++) {
                String currentChord;
                if (chordsInSameBeat != null && !chordsInSameBeat.isEmpty()) {
                    int indexInSameBeat = i % chordsInSameBeat.size();
                    currentChord = chordsInSameBeat.get(indexInSameBeat);
                } else {
                    currentChord = chordsForBeat.get(i);
                }

                System.out.println("Processing beat " + (beat + 1) + ", chord " + (i + 1) + ": " + currentChord);

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
        }

        // New code to play the MIDI file using the default synthesizer
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);

            sequencer.start();
            Thread.sleep(5000); // Sleep for 5 seconds
            sequencer.close();
        } catch (MidiUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
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

    public static List<String> getAllChordNames() {
        return Arrays.asList("C_MAJOR", "D_MINOR", "E_MINOR", "F_MAJOR", "G_MAJOR", "A_MINOR", "B_DIMINISHED");
    }
}
