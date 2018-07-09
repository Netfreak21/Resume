package com.example.lokesh.keepmemanaged;

import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by lokesh on 1/7/18.
 */
@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {
    @View(R.id.profileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.emailTxt)
    private TextView emailTxt;

    private String mname,memail;

    public DrawerHeader(String name,String email)
    {
        mname=name;
        memail=email;
    }

    @Resolve
    private void onResolved() {
        nameTxt.setText(mname);
        emailTxt.setText(memail);
    }
}

