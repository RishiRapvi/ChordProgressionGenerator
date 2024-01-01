import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Welcome to the Chord Progression Generator!");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the number of beats in the progression: ");
            int numberOfBeats = scanner.nextInt();

            List<List<String>> chordProgression = getChordProgression(numberOfBeats, scanner);

            System.out.println("Enter the chords you want in the same beat (separated by spaces), or leave blank for default: ");
            scanner.nextLine(); // Consume the newline character
            String chordsInSameBeatInput = scanner.nextLine();
            List<String> chordsInSameBeat = Arrays.asList(chordsInSameBeatInput.trim().split("\\s+"));

            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            ChordProgressionGenerator.generateMIDI(sequence, chordProgression, 480, chordsInSameBeat);

            File outputFile = new File("output.mid");
            MidiSystem.write(sequence, 1, outputFile);

            System.out.println("MIDI file saved: " + outputFile.getAbsolutePath());
        } catch (InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }

    private static List<List<String>> getChordProgression(int numberOfBeats, Scanner scanner) {
        // Implement logic to collect chord progression from the user
        // You can adapt the existing logic from your program
        // For simplicity, I'll just return a sample progression
        return Arrays.asList(
                Arrays.asList("C_MAJOR", "D_MINOR", "E_MINOR"),
                Arrays.asList("F_MAJOR", "G_MAJOR", "A_MINOR"),
                Arrays.asList("B_DIMINISHED", "C_MAJOR", "D_MINOR")
        );
    }
}
