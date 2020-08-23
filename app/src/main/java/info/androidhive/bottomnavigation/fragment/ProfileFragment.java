package info.androidhive.bottomnavigation.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.bottomnavigation.LoginActivity;
import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.SessionHandler;
import info.androidhive.bottomnavigation.User;
import info.androidhive.bottomnavigation.accountText;
import info.androidhive.bottomnavigation.accountTextAdapter;

public class ProfileFragment extends Fragment {

    private SessionHandler session;

    public String username;
    public String phone;
    public User user;





    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getContext());
        user=session.getUserDetails();
        username=user.getFullName();
        phone=user.getPhoneNumber();


    }

    public final int REQUEST_IMAGE_CAPTURE=1;
    CircleImageView pic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_profile, container, false);

        final ArrayList<accountText> accountTexts = new ArrayList<>();

        accountTexts.add(new accountText(username,"Name"));
        accountTexts.add(new accountText(phone,"Phone Number"));
        accountTexts.add(new accountText("Settings",""));
        accountTexts.add(new accountText("Contact Us",""));
        accountTexts.add(new accountText("Logout",""));


        accountTextAdapter adapter = new accountTextAdapter(getContext(),accountTexts);

        ListView listView = (ListView) v.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(accountTexts.get(position).getMainString()=="Logout"){
                    session.logoutUser();
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    Toast.makeText(getContext(),"You have logged out!",Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
            }
        });


        // for camera operation

        CircleImageView cmr = (CircleImageView) v.findViewById(R.id.camera_photo);
        pic = (CircleImageView) v.findViewById(R.id.outside_img);


        cmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE ) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pic.setImageBitmap(imageBitmap);
        }
    }
}
