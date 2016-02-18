package isminterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * handle socket callbacks or events
 * Created by c85 on 11/01/16.
 */
public interface OnSocketResponse {

    public void onNewMessage(JSONObject message) throws JSONException;
}
