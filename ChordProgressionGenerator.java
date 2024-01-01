import javax.sound.midi.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChordProgressionGenerator {
    // Define basic chords and their corresponding MIDI notes
    static final List<Integer> C_MAJOR = Arrays.asList(60, 64, 67);
    static final List<Integer> D_MINOR = Arrays.asList(62, 65, 69);
    static final List<Integer> E_MINOR = Arrays.asList(64, 67, 71);
    static final List<Integer> F_MAJOR = Arrays.asList(65, 69, 72);
    static final List<Integer> G_MAJOR = Arrays.asList(67, 71, 74);
    static final List<Integer> A_MINOR = Arrays.asList(69, 72, 76);
    static final List<Integer> B_DIMINISHED = Arrays.asList(71, 74, 77);

    // Define chord progressions
    static final List<List<String>> PROGRESSIONS = Arrays.asList(
            Arrays.asList("C", "G", "Am", "F"),
            Arrays.asList("Dm", "G", "C", "Em")
    );

    public static List<String> generateRandomProgression() {
        Random random = new Random();
        return PROGRESSIONS.get(random.nextInt(PROGRESSIONS.size()));
    }


    public static void chordsToMIDI(List<String> chordProgression, String outputFile) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        // Create a Sequencer and a Sequence
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        Sequence sequence = new Sequence(Sequence.PPQ, 480); // 480 ticks per beat

        // Create a Track
        Track track = sequence.createTrack();

        // Use a piano instrument
        ShortMessage programChange = new ShortMessage();
        programChange.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 12, 0);
        track.add(new MidiEvent(programChange, 0));

        // Convert chords to MIDI notes
        for (String chord : chordProgression) {
            for (int note : C_MAJOR) { // Change to the corresponding chord
                ShortMessage noteOn = new ShortMessage();
                noteOn.setMessage(ShortMessage.NOTE_ON, 0, note, 64);
                track.add(new MidiEvent(noteOn, 0));

                ShortMessage noteOff = new ShortMessage();
                noteOff.setMessage(ShortMessage.NOTE_OFF, 0, note, 64);
                track.add(new MidiEvent(noteOff, 480)); // Duration: 1 beat
            }
        }

        // Save the MIDI sequence to a file
        MidiSystem.write(sequence, 1, new java.io.File(outputFile));
        sequencer.close();
    }


}