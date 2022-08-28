package com.ojsusuandloans;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutFragment extends Fragment {



    public AboutFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new AboutPage(getContext())

                .isRTL(false)
                .setCustomFont(ResourcesCompat.getFont(getActivity(), R.font.montsemibold))
                .setImage(R.drawable.ic_group_2)
                .setDescription("Apply for an online loan to grow your business or pay bills.\n" +
                        "\n" +
                        "Make your daily susu at your free will. With bimbo finance you can apply for a loan without collateral.\n" +
                        "\n" +
                        "Can borrow money to pay bills at any time with less interest. Just join our millions of satisfied bimbo finance customers and access small loans right into your mobile money wallet or bank wallet. We work as agent for other international banks.")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect with us")
                .addEmail("bimbofinance@yahoo.com", "bimbofinance@yahoo.com")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addFacebook("the.medy")
                .addTwitter("medyo80")
                .addInstagram("medyo80")
                .addItem(getCopyRightsElement())
                .create();

    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        copyRightsElement.setTitle("2022 All Rights Reserved");
        copyRightsElement.setIconDrawable(R.drawable.ic_copyright);
        copyRightsElement.setAutoApplyIconTint(true);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);

        return copyRightsElement;
    }
}