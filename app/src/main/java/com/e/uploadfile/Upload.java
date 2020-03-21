package com.e.uploadfile;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;

    //MenuItem Implement... code
    private String mKey;
    //MenuItem Implement... code

    public Upload(){
        //empty constructor needed

    }
    public Upload(String mName, String mImageUrl) {
        if(mName.trim().equals("")){
            mName = "No Name";
        }

        this.mName = mName;
        this.mImageUrl = mImageUrl;
        //this.mKey = mKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    //MenuItem Implement... code
    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
    //MenuItem Implement... code
}
