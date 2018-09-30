package com.example.thanh.android_project_mob204.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.activity.ListUser;
import com.example.thanh.android_project_mob204.activity.ChangePasswordActivity;
import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.activity.RegisterActivity;
import com.example.thanh.android_project_mob204.adapter.MoreAdapter;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.More;
import com.example.thanh.android_project_mob204.model.User;
import com.example.thanh.android_project_mob204.sqlitedao.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends Fragment {
    private Toolbar toolbar;
    private ListView lvMore;
    private List<More> listMore;
    private MoreAdapter adapter;
    public TextView tvName;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewMore = inflater.inflate(R.layout.fragment_more, container, false);
//        Lấy tên từ database
        databaseHelper = new DatabaseHelper(getContext());
        UserDAO userDAO = new UserDAO(databaseHelper);
        tvName = viewMore.findViewById(R.id.tvName);

//        LoginActivity loginActivity = (LoginActivity) getActivity();
//        String username = loginActivity.getMyData();
//        Log.e("data", username);

        final Bundle bundle = getArguments();
        if(bundle!=null){
            bundle.getString("usernameLogin");
        }
        Log.e("usernameFromBottom",bundle.getString("usernameLogin"));

        User user = userDAO.getUser(bundle.getString("usernameLogin"));
        tvName.setText(user.getName());


        toolbar = viewMore.findViewById(R.id.toolbarMore);
        lvMore = viewMore.findViewById(R.id.lvMore);
        toolbar.setTitle("Acount");
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        createData();
        adapter = new MoreAdapter(getContext(), R.layout.item_more, listMore);
        lvMore.setAdapter(adapter);
        lvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                        intent.putExtra("usernameFromMoreFragment", bundle.getString("usernameLogin"));
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getContext(), RegisterActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getContext(), ListUser.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Toast.makeText(getContext(), "About Us!", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getContext(), "Setting!", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                        builder.setTitle("Exit");
                        builder.setMessage("Do you want exit program?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                        break;
                }
            }
        });
        return viewMore;
    }

    private void createData() {
        listMore = new ArrayList<>();
        listMore.add(new More(R.drawable.change_password, "Change Password"));
        listMore.add(new More(R.drawable.create_acount, "Create Acount"));
        listMore.add(new More(R.drawable.ic_list_user, "List Users"));
        listMore.add(new More(R.drawable.infomation, "About Us"));
        listMore.add(new More(R.drawable.ic_settings_black_24dp, "Setting"));
        listMore.add(new More(R.drawable.exit, "Exit"));
    }
}
