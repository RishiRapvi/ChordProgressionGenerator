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

            System.out.println("Available chords: C_MAJOR, D_MINOR");
            System.out.println("Enter the chords for the progression separated by spaces:");
            List<String> userChordProgression = Arrays.asList(scanner.nextLine().trim().split("\\s+"));

            // Allow the user to specify if they want multiple chords in the same beat
            System.out.print("Do you want multiple chords in the same beat? (y/n): ");
            boolean multipleChordsInSameBeat = scanner.nextLine().equalsIgnoreCase("y");

            // Adjust the number of notes per chord based on user input
            int notesPerChord = multipleChordsInSameBeat ? 2 : 4;

            // Create a Sequencer
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Create a Sequence
            Sequence sequence = new Sequence(Sequence.PPQ, 480); // Specify the division type and resolution

            // Continue with MIDI generation
            ChordProgressionGenerator.generateMIDI(sequence, userChordProgression, numChords, notesPerChord, 480);

            // Set the sequence for the sequencer
            sequencer.setSequence(sequence);

            // Start playing
            sequencer.start();

            // Close the scanner
            scanner.close();

        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
}
