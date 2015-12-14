package com.pmc.Activityroute.Model;

/**
 * Created by luluteam on 2015/5/28.
 */
public class Route {
    private int createTime;
    private String name;

    public Route(){}

    public Route(int createTime, String name) {
        this.createTime = createTime;
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getName() {
        return name;
    }


}
