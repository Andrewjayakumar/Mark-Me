package com.cybersix.markme;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

public class ElasticSearchIOController {

    private static JestDroidClient client = null;

    /**
     * Create a singleton of the JestDroidClient.
     */
    public static void setClient() {
        if (client == null) {
            DroidClientConfig config = new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080").build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    /**
     * Queries the elasticsearch database for a user. Do NOT call directly, call getUserTask
     * instead.
     * @param username - The username you want to find.
     * @return - Returns a list of users that match the username, which should be 0 or 1.
     */
    public static List<UserModel> getUser(String username) {
        setClient();

        // Case does not matter.
        String query = "{ \"query\" : \n" +
                       "{ \"match\" :\n" +
                       "{ \"username\" : \" " + username + " \" }}}";

        Search search = new Search.Builder(query)
                .addIndex("cmput301f18t24test")
                .addType("users")
                .build();

        try {
            JestResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<UserModel> userList;
                userList = result.getSourceAsObjectList(UserModel.class);
                return userList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<UserModel>();
    }

    /**
     * Queries a list the elastic search database for a list of users. See also getUser().
     */
    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<UserModel>> {

        protected ArrayList<UserModel> doInBackground(String... strings) {
            ArrayList<UserModel> users = new ArrayList<UserModel>();
            for (String s: strings) {
                users.addAll(getUser(s));
            }
            return users;
        }
    }

}
