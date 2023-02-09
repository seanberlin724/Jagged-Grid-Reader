public class GameFileTester {
    public static void main(String[] args) {
        // Create a new GridReader object
        String filename = "heads.txt";
        JaggedGridReader jaggedGrid = new JaggedGridReader(filename);

        // Display grid
        System.out.print(jaggedGrid);
    }
}
