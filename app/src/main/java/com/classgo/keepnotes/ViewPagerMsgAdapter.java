package com.classgo.keepnotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerMsgAdapter extends FragmentPagerAdapter {
    public ViewPagerMsgAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return new Monday();
        } else if (position==1) {
            return new Tuesday();

        } else if (position==2) {
            return new Wednesday();

        }
        else if(position==3)
        {
            return new Thursday();
        } else if (position==4) {
            return  new Friday();

        }
        else if(position==5)
        {
            return  new Saturday();
        }
        else
        {
            return  new sunday();
        }


    }

    @Override
    public int getCount() {
        return 7;  // no of tabs
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0)
        {
            return "Monday";
        }
        else if(position==1)
        {
            return "Tuesday";
        }
        else if(position==2)
        {
            return  "Wednesday";
        }
        else if(position==3)
        {
            return "Thursday";
        }
        else if(position==4)
        {
            return "Friday";
        }
        else if (position==5) {
            return "Saturday";

        } else{
            return "Sunday";

        }

    }
}
