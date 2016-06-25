package com.appdevelopers.seanwade.familymap.main_screen;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appdevelopers.seanwade.familymap.FMS;
import com.appdevelopers.seanwade.familymap.MainActivity;
import com.appdevelopers.seanwade.familymap.R;
import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.model.User;
import com.appdevelopers.seanwade.familymap.utilities.JsonResponseParser;
import com.appdevelopers.seanwade.familymap.web_server_access.HttpClient;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

public class LoginFragment extends Fragment {

    private final int REQ_CODE_ORDER_INFO = 1;

    private Button button;
    private Button existingUserButton;

    private TextView mUserName;
    private TextView mPassword;
    private TextView mServerHost;
    private TextView mServerPort;

    private String userName;
    private String password;
    private String serverHost;
    private String serverPort;

    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    private EditText mServerHostEditText;
    private EditText mServerPortEditText;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        button = (Button) v.findViewById(R.id.signInButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                onButtonClicked(true);
            }
        });

        existingUserButton = (Button) v.findViewById(R.id.existingUserButton);
        existingUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(false);
            }
        });

        mUserName = (TextView) v.findViewById(R.id.userName);
        mPassword = (TextView) v.findViewById(R.id.password);
        mServerHost = (TextView) v.findViewById(R.id.serverHost);
        mServerPort = (TextView) v.findViewById(R.id.serverPort);

        mUserNameEditText = (EditText) v.findViewById(R.id.userNameEditText);
        mPasswordEditText = (EditText) v.findViewById(R.id.passwordEditText);
        mServerHostEditText = (EditText) v.findViewById(R.id.serverHostEditText);
        mServerPortEditText = (EditText) v.findViewById(R.id.serverPortEditText);

        return v;
    }

    public void onButtonClicked(boolean signIn) {
        userName = mUserNameEditText.getText().toString();
        password = mPasswordEditText.getText().toString();
        serverHost = mServerHostEditText.getText().toString();
        serverPort = mServerPortEditText.getText().toString();

        if (signIn == true) {

            if (!(userName.equals("") || password.equals("") || serverHost.equals("") || serverPort.equals(""))) {
                FMS.getInstance().setUser(new User(userName, password));
                FMS.getInstance().setServerPort(serverPort);
                FMS.getInstance().setServerHost(serverHost);

                LoginTask loginTask = new LoginTask();
                loginTask.execute(new LoginTaskParams(userName, password, serverHost, serverPort));

            }
            else {
                Toast.makeText(getActivity(), "Enter all the info!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Log.e("mine", "made it in");
            userName = "sean";
            password = "sean";
            serverHost = "192.168.1.237";
            serverPort = "8000";
            FMS fms = FMS.getInstance();
            fms.setUser(new User(userName, password));
            fms.setLogedIn(true);

            List<Person> peopleList = JsonResponseParser.parsePeople(loadJSONFromAsset("people.json"));
            fms.setPeopleList(peopleList);
            List<Event> eventList = JsonResponseParser.parseEvents(loadJSONFromAsset("events.json"));
            fms.setEventList(eventList);
            android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
            MainActivity mainActivity = (MainActivity) getActivity();
            fm.beginTransaction()
                    .replace(R.id.mainFrameLayout, new MainMapFragment())
                    .addToBackStack(null)
                    .commit();
            mainActivity.setIconsVisible();
        }
    }

    public JSONObject loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            return obj;
        }
        catch (Exception e){
            return null;
        }
    }

    public class LoginTaskParams {

        private String userName;
        private String password;
        private String serverHost;
        private String serverPort;

        public LoginTaskParams(String userName, String password, String serverHost, String serverPort) {
            this.userName = userName;
            this.password = password;
            this.serverHost = serverHost;
            this.serverPort = serverPort;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getServerHost() {
            return serverHost;
        }

        public void setServerHost(String serverHost) {
            this.serverHost = serverHost;
        }

        public String getServerPort() {
            return serverPort;
        }

        public void setServerPort(String serverPort) {
            this.serverPort = serverPort;
        }
    }

    public class LoginTask extends AsyncTask<LoginTaskParams, Void, Integer> {

        private FMS fms;

        public LoginTask() {
            fms = FMS.getInstance();
        }

        @Override
        protected Integer doInBackground(LoginTaskParams... loginParamsList) {
            try {
                HttpClient httpClient = new HttpClient();
                LoginTaskParams loginParams = loginParamsList[0];
                String url = "http://" + loginParams.getServerHost() + ":" + loginParams.getServerPort();
                Log.e("mine", url);
                JSONObject loginResult = httpClient.login(loginParams.getUserName(),
                        loginParams.getPassword(), url);
                if (loginResult == null) {
                    throw new Exception();
                }
                User user = new User(loginParams.getUserName(), loginParams.getPassword());
                fms.setUser(JsonResponseParser.parseUser(loginResult, user));
                return 0;
            }
            catch (HttpClient.LoginFailedException e) {
                Log.e("Ex", "Ex in loginFailedEx");
                return 1;
            }
            catch (Exception e) {
                Log.e("Ex", "Ex in doInBackgound");
                return 1;
            }
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
        }

        @Override
        protected void onPostExecute(Integer i) {
            if (i == 0) {
                if (fms.getUser().getAuthorizeCode() == null) {
                    Toast.makeText(getActivity(), "Invalid Login......",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Login Successful!",
                            Toast.LENGTH_LONG).show();

                    PeopleTask peopleTask = new PeopleTask();
                    peopleTask.execute(new LoginTaskParams(userName, password, serverHost, serverPort));
                }
            }
            else {
                Toast.makeText(getActivity(), "Invalid Login...",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public class PeopleTask extends AsyncTask<LoginTaskParams, Void, Integer> {

        private FMS fms;

        public PeopleTask() {
            fms = FMS.getInstance();
        }

        @Override
        protected Integer doInBackground(LoginTaskParams... loginParamsList) {
            try {
                HttpClient httpClient = new HttpClient();
                LoginTaskParams loginParams = loginParamsList[0];
                String url = "http://" + loginParams.getServerHost() + ":" + loginParams.getServerPort();
                JSONObject peopleResult = httpClient.getPeople(fms.getUser().getAuthorizeCode(), url);
                List<Person> peopleList = JsonResponseParser.parsePeople(peopleResult);
                fms.setPeopleList(peopleList);
                return 0;
            }
            catch (HttpClient.HttpFailedException e) {

            }
            return 1;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
        }

        @Override
        protected void onPostExecute(Integer id) {
            EventTask eventTask = new EventTask();
            eventTask.execute(new LoginTaskParams(userName, password, serverHost, serverPort));

        }
    }

    public class EventTask extends AsyncTask<LoginTaskParams, Void, Integer> {

        private FMS fms;

        public EventTask() {
            fms = FMS.getInstance();
        }

        @Override
        protected Integer doInBackground(LoginTaskParams... loginParamsList) {
            try {
                HttpClient httpClient = new HttpClient();
                LoginTaskParams loginParams = loginParamsList[0];
                String url = "http://" + loginParams.getServerHost() + ":" + loginParams.getServerPort();
                JSONObject eventResult = httpClient.getEvents(fms.getUser().getAuthorizeCode(), url);
                List<Event> eventList = JsonResponseParser.parseEvents(eventResult);
                fms.setEventList(eventList);

                return 0;
            }
            catch (HttpClient.HttpFailedException e) {

            }
            return 1;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
        }

        @Override
        protected void onPostExecute(Integer id) {
            FMS.getInstance().setLogedIn(true);
            android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
            MainActivity mainActivity = (MainActivity) getActivity();
            fm.beginTransaction()
                    .replace(R.id.mainFrameLayout, new MainMapFragment())
                    .addToBackStack(null)
                    .commit();
            mainActivity.setIconsVisible();

        }
    }

    public void skipLoginGoToMap() {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        MainActivity mainActivity = (MainActivity) getActivity();
        fm.beginTransaction()
                .replace(R.id.mainFrameLayout, new MainMapFragment())
                .addToBackStack(null)
                .commit();
        mainActivity.setIconsVisible();
    }

}
