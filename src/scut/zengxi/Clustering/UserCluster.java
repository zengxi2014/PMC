package scut.zengxi.Clustering;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengxi on 2015/12/8.
 *
 * 用户簇类，即第二层聚类中的簇类
 */
public class UserCluster {
    private String name;  //簇类名
    private List<String> member=new ArrayList<>();  //簇类中的数据点
    public UserCluster(){}

    public List<String> getMember() {
        return member;
    }

    public void setMember(List<String> member) {
        this.member = member;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = "Cluster"+name;
    }

    public boolean isEmpty(){
        return this.member.size()==0;
    }

    public void add(String user){
        this.member.add(user);
    }

    /**
     * 打印簇类的方法
     */
    public void printCluster(){
        System.out.println("================="+this.name+"====================");
        for(String men:member){
            System.out.println(men);
        }
    }
}
