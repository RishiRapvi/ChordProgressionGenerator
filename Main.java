import javax.sound.midi.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to the Chord Progression Generator!");

            // Allow the user to customize the chord progression
            System.out.print("Enter the number of chords in the progression: ");
            int numChords = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.println("Available chords: " + Arrays.toString(ChordProgressionGenerator.getAllChordNames()));
            System.out.println("Enter the chords for the progression separated by spaces:");
            List<String> userChordProgression = Arrays.asList(scanner.nextLine().trim().split("\\s+"));

            // Allow the user to specify chords in the same beat
            System.out.print("Enter the chords you want in the same beat (separated by spaces), or leave blank for default: ");
            List<String> chordsInSameBeat = Arrays.asList(scanner.nextLine().trim().split("\\s+"));

            // Create a Sequencer
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Create a Sequence
            Sequence sequence = new Sequence(Sequence.PPQ, 480); // Specify the division type and resolution

            // Continue with MIDI generation
            int notesPerChord = chordsInSameBeat.isEmpty() ? 4 : chordsInSameBeat.size();
            ChordProgressionGenerator.generateMIDI(sequence, userChordProgression, numChords, notesPerChord, 480, chordsInSameBeat);

            // Specify the output file name and write the MIDI sequence to a file
            String fileName = "output.mid";
            MidiSystem.write(sequence, 1, new java.io.File(fileName));

            System.out.println("MIDI file saved: " + fileName);

            // Set the sequence for the sequencer
            sequencer.setSequence(sequence);

            // Start playing
            sequencer.start();

            // Allow some time for the sequencer to play
            Thread.sleep(5000); // Sleep for 5 seconds

            // Stop and close the sequencer
            sequencer.stop();
            sequencer.close();

            // Close the scanner
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
