
import java.io.*;
import java.util.*;
/**
 * The A1 class contains helper methods for processing and analyzing text data.
 */
public class A1 {
	/**
	 * The main class for text processing and analysis.
     * @author Sri Nandan Bharadwaj Velamakanni
	 */
    public static void main(String[] args) {
        // Check if the correct number of command-line arguments is provided
        if (args.length != 2) {
            System.err.println("Two arguments expected (java A1 input.txt output.txt)");
            System.exit(1);
        }

        // Extract input and output file names from command-line arguments
        String inputFile = args[0];
        String outputFile = args[1];

        try {
            // Initialize a BufferedReader to read the input file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            // Initialize a BufferedWriter to write the output file
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            // Create a list to store WordFrequency objects to keep track of word frequencies
            List<WordFrequency> wordFrequencies = new ArrayList<>();

            // Create a list of stop words from the provided stop words array
            List<String> stopWords = Arrays.asList(getStopWords());

            String line;
            int totalWords = 0;
            int stopWordCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    // Process and clean the word
                    word = processWord(word);

                    if (!word.isEmpty()) {
                        totalWords++;

                        if (!isStopWord(word, stopWords)) {
                            // Update word frequencies if the word is not a stop word
                            updateWordFrequencies(wordFrequencies, word);
                        } else {
                            stopWordCount++;
                        }
                    }
                }
            }

            reader.close();

            // Sort word frequencies in ascending order by frequency and then alphabetically
            Collections.sort(wordFrequencies, new WordFrequencyComparator());

            // Write statistics to the output file
            writer.write("Total Words: " + totalWords + "\n");
            writer.write("Unique Words: " + wordFrequencies.size() + "\n");
            writer.write("Stop Words: " + stopWordCount + "\n\n");

            writer.write("10 Most Frequent\n");
            writeTopWords(writer, wordFrequencies, 10);

            writer.write("\n10 Least Frequent\n");
            writeLeastFrequentWords(writer, wordFrequencies, 10);

            writer.write("\nAll\n");
            writeAllWords(writer, wordFrequencies);

            // Close the output file
            writer.close();
        } catch (IOException e) {
            System.err.println("Error reading/writing files: " + e.getMessage());
        }
    }

    /**
	 * Helper method to process and clean a word.
	 *
	 * @param word The input word to be processed.
	 * @return The processed word with leading/trailing whitespace removed and
	 *         converted to lowercase.
	 */
    private static String processWord(String word) {
        return word.trim().toLowerCase().replaceAll("[^a-zA-Z]", "");
    }

    /**
	 * Helper method to check if a word is a stop word.
	 *
	 * @param word      The word to check.
	 * @param stopWords A list of stop words to compare against.
	 * @return True if the word is a stop word, false otherwise.
	 */
    private static boolean isStopWord(String word, List<String> stopWords) {
        return stopWords.contains(word);
    }

    /**
     * Helper method to update word frequencies in the list.
     *
     * @param wordFrequencies A list of WordFrequency objects.
     * @param word            The word to update.
     */
    private static void updateWordFrequencies(List<WordFrequency> wordFrequencies, String word) {
        for (WordFrequency wf : wordFrequencies) {
            if (wf.getWord().equals(word)) {
                wf.incrementFrequency();
                return;
            }
        }

        wordFrequencies.add(new WordFrequency(word));
    }

    // Helper method to get a list of stop words
    private static String[] getStopWords() {
        return new String[] {
            "a", "about", "all", "am", "an", "and", "any", "are", "as", "at", "be", "been", "but", "by", "can",
            "cannot", "could", "did", "do", "does", "else", "for", "from", "get", "got", "had", "has", "have", "he",
            "her", "hers", "him", "his", "how", "i", "if", "in", "into", "is", "it", "its", "like", "more", "me", "my",
            "no", "now", "not", "of", "on", "one", "or", "our", "out", "said", "say", "says", "she", "so", "some",
            "than", "that", "the", "their", "them", "then", "there", "these", "they", "this", "to", "too", "us",
            "upon", "was", "we", "were", "what", "with", "when", "where", "which", "while", "who", "whom", "why",
            "will", "you", "your"
        };
    }

    // Helper method to write the top N words to the output
    private static void writeTopWords(BufferedWriter writer, List<WordFrequency> wordFrequencies, int count)
            throws IOException {
        for (int i = 0; i < Math.min(count, wordFrequencies.size()); i++) {
            String word = wordFrequencies.get(i).getWord();
            int frequency = wordFrequencies.get(i).getFrequency();
            writer.write(word + " : " + frequency + "\n");
        }
    }

    // Helper method to write the least N frequent words to the output
    private static void writeLeastFrequentWords(BufferedWriter writer, List<WordFrequency> wordFrequencies, int count)
            throws IOException {
        // Sort word frequencies in descending order by frequency and then alphabetically
        Collections.sort(wordFrequencies, Collections.reverseOrder(new WordFrequencyComparator()));

        for (int i = 0; i < Math.min(count, wordFrequencies.size()); i++) {
            String word = wordFrequencies.get(i).getWord();
            int frequency = wordFrequencies.get(i).getFrequency();
            writer.write(word + " : " + frequency + "\n");
        }
    }

    // Helper method to write all words to the output
    private static void writeAllWords(BufferedWriter writer, List<WordFrequency> wordFrequencies) throws IOException {
        // Sort word frequencies alphabetically
        Collections.sort(wordFrequencies, new WordFrequencyComparator());

        for (WordFrequency wf : wordFrequencies) {
            String word = wf.getWord();
            int frequency = wf.getFrequency();
            writer.write(word + " : " + frequency + "\n");
        }
    }
}

// Class representing word frequencies
class WordFrequency {
    private String word;
    private int frequency;

    public WordFrequency(String word) {
        this.word = word;
        this.frequency = 1;
    }

    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        frequency++;
    }
}

// Comparator to compare WordFrequency objects based on frequency and then alphabetically
class WordFrequencyComparator implements Comparator<WordFrequency> {
    @Override
    public int compare(WordFrequency wf1, WordFrequency wf2) {
        int freqComparison = Integer.compare(wf1.getFrequency(), wf2.getFrequency());
        if (freqComparison != 0) {
            return freqComparison;
        }
        return wf1.getWord().compareTo(wf2.getWord());
    }
}
