import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopSearchRanking {

    public static void main(String[] args) {

        String keyword = "dia";

        List<String> diseases = new ArrayList<>();

        diseases.add("diabetes");
        diseases.add("dialysis");
        diseases.add("diabetic ketoacidosis");
        diseases.add("heart disease");
        diseases.add("diagnosis");

        List<String> result = rankSearch(diseases, keyword);

        System.out.println("=== SEARCH RESULT ===");

        for (String r : result) {
            System.out.println(r);
        }
    }

    public static List<String> rankSearch(
            List<String> data,
            String keyword
    ) {

        List<String> matched = new ArrayList<>();

        // prefix matching
        for (String item : data) {

            if (item.toLowerCase()
                    .startsWith(keyword.toLowerCase())) {

                matched.add(item);
            }
        }

        // ranking
        Collections.sort(matched, new Comparator<String>() {

            @Override
            public int compare(String a, String b) {

                // shortest string first
                return Integer.compare(a.length(), b.length());
            }
        });

        return matched;
    }
}
