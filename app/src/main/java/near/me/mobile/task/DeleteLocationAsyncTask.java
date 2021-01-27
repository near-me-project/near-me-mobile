package near.me.mobile.task;

import android.os.AsyncTask;

import near.me.mobile.shared.Constants;
import near.me.mobile.shared.RestClient;

public class DeleteLocationAsyncTask extends AsyncTask<String, Void, Void> {

    private RestClient restClient;

    public DeleteLocationAsyncTask() {
        restClient = new RestClient();
    }

    @Override
    protected Void doInBackground(String... strings) {
        System.out.println("[DeleteLocationAsyncTask]: outcome request >>> delete strings[0]");
        restClient.delete(Constants.URL.LOOKUP_DELETE_LOCATION + strings[0]);
        return null;
    }
}
