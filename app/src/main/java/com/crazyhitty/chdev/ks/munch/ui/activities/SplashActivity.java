package com.crazyhitty.chdev.ks.munch.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageButton;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

import com.crazyhitty.chdev.ks.munch.R;
import com.crazyhitty.chdev.ks.munch.models.SettingsPreferences;
import com.crazyhitty.chdev.ks.munch.ui.adapters.SplashPagerAdapter;
import com.crazyhitty.chdev.ks.munch.utils.FadePageTransformerUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.container)
    ViewPager viewPager;
    @Bind(R.id.image_button_next)
    ImageButton imgBtnNext;

    private SplashPagerAdapter mSplashPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        runOnce();

        WritePhoneContact("Jazz Media", "9999999999",getApplicationContext());
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSplashPagerAdapter = new SplashPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSplashPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        //set page transformer
        viewPager.setPageTransformer(false, new FadePageTransformerUtil());

        viewPager.addOnPageChangeListener(this);
    }

    private void runOnce() {
        if (!SettingsPreferences.isNewInstall(SplashActivity.this)) {
            runIntent(HomeActivity.class);
            finish();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                imgBtnNext.setImageResource(R.drawable.ic_arrow_forward_24dp);
                break;
            case 1:
                imgBtnNext.setImageResource(R.drawable.ic_arrow_forward_24dp);
                break;
            case 2:
                imgBtnNext.setImageResource(R.drawable.ic_done_24dp);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.image_button_next)
    public void OnNext() {
        if (viewPager.getCurrentItem() == 2) {
            SettingsPreferences.setNewInstall(SplashActivity.this);
            runIntent(HomeActivity.class);
            finish();
        } else if (viewPager.getCurrentItem() < 2) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
    }

    private void runIntent(Class resultActivity) {
        Intent intent = new Intent(SplashActivity.this, resultActivity);
        startActivity(intent);
    }

    public void WritePhoneContact(String displayName, String number,Context cntx /*App or Activity Ctx*/)
    {
        Context contetx 	= cntx; //Application's context or Activity's context
        String strDisplayName 	=  displayName; // Name of the Person to add
        String strNumber 	=  number; //number of the person to add with the Contact

        ArrayList<ContentProviderOperation> cntProOper = new ArrayList<ContentProviderOperation>();
        int contactIndex = cntProOper.size();//ContactSize

        //Newly Inserted contact
        // A raw contact will be inserted ContactsContract.RawContacts table in contacts database.
        cntProOper.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)//Step1
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null).build());

        //Display name will be inserted in ContactsContract.Data table
        cntProOper.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)//Step2
                .withValueBackReference(Data.RAW_CONTACT_ID,contactIndex)
                .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.DISPLAY_NAME, strDisplayName) // Name of the contact
                .build());
        //Mobile number will be inserted in ContactsContract.Data table
        cntProOper.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)//Step 3
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,contactIndex)
                .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER, strNumber) // Number to be added
                .withValue(Phone.TYPE, Phone.TYPE_MOBILE).build()); //Type like HOME, MOBILE etc
        try
        {
            // We will do batch operation to insert all above data
            //Contains the output of the app of a ContentProviderOperation.
            //It is sure to have exactly one of uri or count set
            ContentProviderResult[] contentProresult = null;
            contentProresult = contetx.getContentResolver().applyBatch(ContactsContract.AUTHORITY, cntProOper); //apply above data insertion into contacts list
        }
        catch (RemoteException exp)
        {
            //logs;
        }
        catch (OperationApplicationException exp)
        {
            //logs
        }
    }

}
