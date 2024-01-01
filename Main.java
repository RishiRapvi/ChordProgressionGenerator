import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final List<Integer> C_MAJOR = Arrays.asList(60, 64, 67);
    static final List<Integer> D_MINOR = Arrays.asList(62, 65, 69);
    // ... other chord definitions ...

    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Chord Progression Generator!");

        // Allow the user to customize the chord progression
        System.out.print("Enter the number of chords in the progression: ");
        int numChords = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Available chords: C_MAJOR, D_MINOR, ect.");
        System.out.println("Enter the chords for the progression separated by spaces: (Type in all caps, such as C_MAJOR with no commas)");

        // Read user input for the chord progression
        List<String> userChordProgression = Arrays.asList(scanner.nextLine().trim().split("\\s+"));

        // Validate user input
        if (isValidChordProgression(userChordProgression)) {
            System.out.println("Generating MIDI for the custom chord progression...");
            ChordProgressionGenerator.chordsToMIDI(userChordProgression, "custom_output.mid");
        } else {
            System.out.println("Invalid chord progression. Exiting.");
        }

        // Close the scanner
        scanner.close();
    }

    static boolean isValidChordProgression(List<String> chords) {
        for (String chord : chords) {
            if (!isValidChord(chord)) {
                System.out.println("Invalid chord: " + chord);
                return false;
            }
        }
        return true;
    }
    static boolean isValidChord(String chord) {
        return chord.equals("C_MAJOR") || chord.equals("D_MINOR") ||
                chord.equals("E_MINOR") || chord.equals("F_MAJOR") ||
                chord.equals("G_MAJOR") || chord.equals("A_MINOR") ||
                chord.equals("B_DIMINISHED");
    }

}