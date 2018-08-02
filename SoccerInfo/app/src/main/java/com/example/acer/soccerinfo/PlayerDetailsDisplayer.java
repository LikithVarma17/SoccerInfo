package com.example.acer.soccerinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayerDetailsDisplayer extends AppCompatActivity {

    ArrayList<JsonPlayer> jsonPlayers;
    int pos;
    int flag = 0;
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
        setContentView(R.layout.activity_player_details_displayer);
        Bundle bundle = getIntent().getExtras();
        jsonPlayers = bundle.getParcelableArrayList(getResources().getString(R.string.PlayerData));
        pos = bundle.getInt(getResources().getString(R.string.POS));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        int flag = 0;

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
        public static PlaceholderFragment newInstance(ArrayList<JsonPlayer> jsonPlayers, int pos, Context context) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putParcelable(context.getResources().getString(R.string.PlayerData), jsonPlayers.get(pos));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_player_details_displayer, container, false);
            final JsonPlayer jsonPlayer = getArguments().getParcelable(getResources().getString(R.string.PlayerData));
            ImageView playerImageView = rootView.findViewById(R.id.player_image_logo);
            Picasso.with(getContext()).load(jsonPlayer.getImage()).placeholder(R.drawable.foot).into(playerImageView);
            TextView playerName = rootView.findViewById(R.id.player_title);
            playerName.setText(jsonPlayer.getPlayerName());
            TextView teamName = rootView.findViewById(R.id.player_team_name);
            teamName.setText(getContext().getString(R.string.pLayer_team_name) + jsonPlayer.getTeamName());
            TextView playerNationality = rootView.findViewById(R.id.player_nationality);
            playerNationality.setText(getContext().getString(R.string.pLayer_nationality) + jsonPlayer.getNationality());
            TextView description = rootView.findViewById(R.id.player_descrption);
            description.setText(getContext().getString(R.string.pLayer_description) + jsonPlayer.getDescription());
            final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            Uri uri = FavouriteContract.CONTENT_URI;
            Uri myUri = uri.buildUpon().appendPath(jsonPlayer.getId()).build();
            Cursor cursor = getContext().getContentResolver().query(myUri, null, null, null, null);
            if (cursor.getCount() > 0) {
                fab.setImageResource(R.mipmap.checked);
                flag = 1;
            } else {
                flag = 0;
            }
            cursor.close();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (flag == 0) {
                        fab.setImageResource(R.mipmap.checked);
                        flag = 1;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(FavouriteContract.FavouriteContractEntry.COLUMN_ID, jsonPlayer.getId());
                        contentValues.put(FavouriteContract.FavouriteContractEntry.COLUMN_NAME, jsonPlayer.getPlayerName());
                        contentValues.put(FavouriteContract.FavouriteContractEntry.COLOUMN_TEAM, jsonPlayer.getTeamName());
                        contentValues.put(FavouriteContract.FavouriteContractEntry.COLUMN_IMAGE, jsonPlayer.getImage());
                        contentValues.put(FavouriteContract.FavouriteContractEntry.COLUMN_NATIONALITY, jsonPlayer.getNationality());
                        contentValues.put(FavouriteContract.FavouriteContractEntry.COLUMN_DESCRIPTION, jsonPlayer.getDescription());
                        Uri uri = getContext().getContentResolver().insert(FavouriteContract.CONTENT_URI, contentValues);
                        if (uri != null) {
                            Toast.makeText(getContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                        }
                        flag = 1;
                    } else {
                        fab.setImageResource(R.mipmap.unchecked);
                        flag = 0;
                        String stringid = jsonPlayer.getId();
                        Uri uri = FavouriteContract.CONTENT_URI;
                        int res = getContext().getContentResolver().delete(uri, stringid, null);
                    }
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
            return PlaceholderFragment.newInstance(jsonPlayers, position, PlayerDetailsDisplayer.this);
        }

        @Override
        public int getCount() {
            return jsonPlayers.size();
        }
    }
}
