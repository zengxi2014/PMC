package scut.zengxi.Clustering;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengxi on 2015/12/8.
 */
public class UserCluster {
    private String name;
    private List<String> member=new ArrayList<>();
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

    public void printCluster(){
        System.out.println("================="+this.name+"====================");
        for(String men:member){
            System.out.println(men);
        }
    }
}
