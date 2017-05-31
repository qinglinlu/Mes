package com.mes.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by monkey on 2017/5/29.
 */

public class QuanXianData {
    public String shuid;
    public String mc;
    public List<myqx> qx;
    public class myqx{
        @SerializedName("shuid")
        public String qxshuid;
        @SerializedName("mc")
        public String qxmc;
    }
}
