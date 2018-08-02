package com.example.acer.soccerinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamDetailsDisplayer extends AppCompatActivity {

    ArrayList<JsonTeam> jsonTeams;
    int pos;
    Button button;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_deta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        jsonTeams = bundle.getParcelableArrayList(getResources().getString(R.string.TeamData));
        pos = bundle.getInt(getResources().getString(R.string.POS));


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);


    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(ArrayList<JsonTeam> jsonTeams, int pos, Context context) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putParcelable(context.getResources().getString(R.string.TeamData), jsonTeams.get(pos));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_team_details_displayer, container, false);
            final JsonTeam jsonTeam = getArguments().getParcelable(getResources().getString(R.string.TeamData));
            ImageView teamImageView = rootView.findViewById(R.id.team_image_logo);
            Picasso.with(getContext()).load(jsonTeam.getImage()).into(teamImageView);
            TextView teamName = rootView.findViewById(R.id.team_title);
            teamName.setText(jsonTeam.getTeam());
            TextView teamOtherName = rootView.findViewById(R.id.team_other_name);
            if (jsonTeam.getOtherName().isEmpty())
                teamOtherName.setVisibility(View.GONE);
            else
                teamOtherName.setText(getContext().getString(R.string.teamOtherName) + jsonTeam.getOtherName());
            TextView teamWebsite = rootView.findViewById(R.id.team_website);
            if (jsonTeam.getWebsite().isEmpty())
                teamWebsite.setVisibility(View.GONE);
            else
                teamWebsite.setText(getContext().getString(R.string.team_website) + jsonTeam.getWebsite());
            TextView description = rootView.findViewById(R.id.team_descrption);
            description.setText(getContext().getString(R.string.TeamDescription) + jsonTeam.getDescription());
            Button button = rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PlayerActivity.class);
                    intent.putExtra(getResources().getString(R.string.ID), jsonTeam.getId());
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(jsonTeams, position, TeamDetailsDisplayer.this);
        }

        @Override
        public int getCount() {
            return jsonTeams.size();
        }
    }
}
