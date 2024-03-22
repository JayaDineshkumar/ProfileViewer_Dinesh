import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GitHubProfileViewer {

    public static void main(String[] args) {
        String username = "JayaDineshkumar";
        try {
            String userProfileUrl = "https://api.github.com/users/" + username;
            String userRepositoriesUrl = "https://api.github.com/users/" + username + "/repos";
            String userProfileResponse = sendHttpRequest(userProfileUrl);
            String userRepositoriesResponse = sendHttpRequest(userRepositoriesUrl);

            JSONObject userProfileJson = new JSONObject(userProfileResponse);
            JSONArray userRepositoriesJson = new JSONArray(userRepositoriesResponse);

            System.out.println("GitHub Profile of " + username);
            System.out.println("Username: " + userProfileJson.getString("login"));
            System.out.println("Followers: " + userProfileJson.getInt("followers"));
            System.out.println("Repositories:");

            for (int i = 0; i < userRepositoriesJson.length(); i++) {
                JSONObject repo = userRepositoriesJson.getJSONObject(i);
                System.out.println("- " + repo.getString("name"));
            }
        } catch (IOException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }

    private static String sendHttpRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        connection.disconnect();

        return response.toString();
    }
}
