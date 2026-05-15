import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchEngine {

    public static void main(String[] args) {

        List<String> database = new ArrayList<>();

        database.add("diabetes");
        database.add("dialysis");
        database.add("heart disease");
        database.add("diabetic ketoacidosis");
        database.add("diagnosis");

        String keyword = "diabtes";

        System.out.println("=== AUTOCOMPLETE ===");

        List<String> auto =
                autocomplete(database, "dia");

        for (String a : auto) {
            System.out.println(a);
        }

        System.out.println("\n=== FUZZY SEARCH ===");

        List<String> result =
                search(database, keyword);

        for (String r : result) {

            int score = similarity(keyword, r);

            System.out.println(
                    r + " | similarity = " + score
            );
        }
    }

    // GENERATE NGRAM

    public static List<String> generateNgram(
            String text,
            int n
    ) {

        List<String> grams = new ArrayList<>();

        for (int i = 0; i <= text.length() - n; i++) {

            grams.add(
                    text.substring(i, i + n)
            );
        }

        return grams;
    }


    // SIMILARITY

    public static int similarity(
            String a,
            String b
    ) {

        List<String> aGram =
                generateNgram(a.toLowerCase(), 3);

        List<String> bGram =
                generateNgram(b.toLowerCase(), 3);

        int match = 0;

        for (String g : aGram) {

            if (bGram.contains(g)) {
                match++;
            }
        }

        return match;
    }

  
    // AUTOCOMPLETE

    public static List<String> autocomplete(
            List<String> data,
            String prefix
    ) {

        List<String> result = new ArrayList<>();

        for (String item : data) {

            if (item.toLowerCase()
                    .startsWith(prefix.toLowerCase())) {

                result.add(item);
            }
        }

        return result;
    }

 
    // SEARCH + RANKING
    
    public static List<String> search(
            List<String> data,
            String keyword
    ) {

        List<String> result = new ArrayList<>();

        for (String item : data) {

            int score =
                    similarity(keyword, item);

            if (score > 0) {
                result.add(item);
            }
        }

        // ranking by similarity
        Collections.sort(result,
                new Comparator<String>() {

                    @Override
                    public int compare(
                            String a,
                            String b
                    ) {

                        int scoreA =
                                similarity(keyword, a);

                        int scoreB =
                                similarity(keyword, b);

                        return Integer.compare(
                                scoreB,
                                scoreA
                        );
                    }
                });

        return result;
    }
}
