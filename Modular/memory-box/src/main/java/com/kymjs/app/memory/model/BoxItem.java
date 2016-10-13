package com.kymjs.app.memory.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZhangTao on 10/13/16.
 */

public class BoxItem implements Parcelable, Comparable<BoxItem> {
    private int id;
    private String name;
    private String key;
    private String value;
    private String description;
    private String image;
    private int position;

    public int getId() {
        return id;
    }

    public BoxItem setId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BoxItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public BoxItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getKey() {
        return key;
    }

    public BoxItem setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public BoxItem setValue(String value) {
        this.value = value;
        return this;
    }

    public String getImage() {
        return image;
    }

    public BoxItem setImage(String image) {
        this.image = image;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public BoxItem setPosition(int position) {
        this.position = position;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.key);
        dest.writeString(this.value);
        dest.writeString(this.image);
        dest.writeInt(this.position);
    }

    public BoxItem() {
    }

    protected BoxItem(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.key = in.readString();
        this.value = in.readString();
        this.image = in.readString();
        this.position = in.readInt();
    }

    public static final Creator<BoxItem> CREATOR = new Creator<BoxItem>() {
        @Override
        public BoxItem createFromParcel(Parcel source) {
            return new BoxItem(source);
        }

        @Override
        public BoxItem[] newArray(int size) {
            return new BoxItem[size];
        }
    };

    @Override
    public int compareTo(BoxItem o) {
        if (o == null) {
            return 1;
        } else {
            return position - o.position;
        }
    }
}
