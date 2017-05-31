package com.example.amine.busniess_card;

import android.content.Context;

/**
 * Created by Amine on 30/05/2017.
 */

public class BusniessCard {

    public BusniessCard() {
    }

    private String
            mName,
            mPhoneNumber,
            mJobTitle,
            mAddress,
            mEmail,
            mPicture;

    private byte[] mQRcode;// this will be holding QR code which is later converted in to BitMap


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmJobTitle() {
        return mJobTitle;
    }

    public void setmJobTitle(String mJobTitle) {
        this.mJobTitle = mJobTitle;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPicture() {
        return mPicture;
    }

    public void setmPicture(String mPicture) {
        this.mPicture = mPicture;
    }

    public byte[] getmQRcode()
    {
        return mQRcode;
    }
    public void setmQRcode(byte[] qrCode)
    {
        this.mQRcode=qrCode;
    }


    public String getDetails()
    {
        String detail=mName+";"+mJobTitle+";"+mPhoneNumber+";"+mAddress+";"+mEmail;
        return detail;
    }

    public static String[] splitStringToCard(String s) {
        String [] array = s.split(";");

        /*String [] array_Busniesscard = new String[5];
        array_Busniesscard[0] = array[1];
        array_Busniesscard[1] = array[2];
        array_Busniesscard[2] = array[3];
        String [] array_address = array[4].split(":");
        array_Busniesscard[3] = array_address[1];
        String [] array_tel1 = array[5].split(":");
        array_Busniesscard[4] = array_tel1[1];
        String [] array_tel2 = array[6].split(":");
        array_Busniesscard[5] = array_tel2[1];
        String [] array_site = array[7].split(":");
        array_Busniesscard[6] = array_site[1];*/

        return array;
    }

    public static BusniessCard getCardObject(String s) {
        BusniessCard bc = new BusniessCard();
        String [] data = splitStringToCard(s);
        bc.setmName(data[0]);
        bc.setmJobTitle(data[1]);
        bc.setmPhoneNumber(data[2]);
        bc.setmAddress(data[3]);
        bc.setmEmail(data[4]);
        return bc;
    }
}
