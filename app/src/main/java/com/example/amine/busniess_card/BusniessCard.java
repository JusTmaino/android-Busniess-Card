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

    /*public static String[] splitStringToCard(String s) {
        String [] array = s.split(";");
        return array;
    }*/

    public static BusniessCard getCardObject(String s) {
        BusniessCard bc = new BusniessCard();
        String [] data = s.split(";");
        bc.setmName(data[0]);
        bc.setmJobTitle(data[1]);
        bc.setmPhoneNumber(data[2]);
        bc.setmAddress(data[3]);
        bc.setmEmail(data[4]);
        return bc;
    }
}
