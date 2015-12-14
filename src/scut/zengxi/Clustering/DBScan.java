package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 *
 * do dbscan clustering
 *
 */


import com.pmc.Activityroute.service.ActivityRouteInfoBasicService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBScan {
    private static List<Session> transRecords= new LinkedList<Session>();   //存储数据库数据
    private static int clusterNum=0;

    /**
     * 执行dbscan的方法
     * @param dataPoints 数据集
     * @param radius 相似度阈值
     * @param ObjectNum  类簇的最小数目
     * @return 返回类簇集合
     */
    public List<Cluster> doDbscanAnalysis(List<DataPoint> dataPoints,double radius,int ObjectNum){
        List<Cluster> clusterList = new ArrayList<Cluster>();
        for (int i = 0; i < dataPoints.size(); i++) {
            DataPoint dp=dataPoints.get(i);
            //求数据节点的可达节点
            List<DataPoint> arrivableObjects=isKeyAndReturnObjects(dp,dataPoints,radius,ObjectNum);
            if(arrivableObjects!=null){
                Cluster tempCluster= new Cluster();
                tempCluster.setClusterName("Cluster"+i);
                tempCluster.setDataPoints(arrivableObjects);
                clusterList.add(tempCluster);
            }
        }

        for (int i = 0; i < clusterList.size(); i++) {
            for (int j = 0; j < clusterList.size(); j++) {
                if(i!=j){
                    Cluster clusterA = clusterList.get(i);
                    Cluster clusterB = clusterList.get(j);

                    List<DataPoint> dpsA= clusterA.getDataPoints();
                    List<DataPoint> dpsB= clusterB.getDataPoints();

                    boolean flag=mergeList(dpsA,dpsB);

                    if(flag){
                        clusterList.set(j, new Cluster());
                    }

                }
            }
        }
        return clusterList;
    }

    /**
     * 把类簇集中非空的类簇显示出来
     * @param stringList
     */
    public void displayCluster(List<List<String>> stringList){
        if(stringList!=null){
            for(int i=0;i<stringList.size();i++){
                System.out.println("=========================Cluster"+i+"======"+stringList.get(i).size()+"==========================");
                for(String str: stringList.get(i)){
                    System.out.println(str);
                }
            }
        }
    }

    /**
     * culsterList的每个簇类中的数据是由数据序号构成的，将其转化为由执行该数据序号数据的用户Id
     * @param clusterList
     * @return  用户ID构成的簇类信息
     */
    public List<List<String>> cluster2StringList(List<Cluster> clusterList){
        List<List<String>> stringList = new ArrayList<>();
        if(clusterList!=null){
            for(Cluster tempCluster:clusterList){
                if(tempCluster.getDataPoints()!=null && tempCluster.getDataPoints().size()>0){
                    List<String> list = new ArrayList<>();
                    for(DataPoint dp:tempCluster.getDataPoints()){
                        list.add(dp.getSession().getUserId());
                    }
                    stringList.add(list);
                }
            }
        }
        return stringList;
    }

    /**
     * 计算两个样本点的距离，调用Similarity中的相似度计算方法
     * @param dp1
     * @param dp2
     * @return
     */
    private double getDistance(DataPoint dp1,DataPoint dp2){
        return Similarity.similarity(dp1.getSession(), dp2.getSession());
    }


    /**
     * 判断节点是否是关键节点并返回他的可达节点
     * @param dataPoint    需要判断的节点
     * @param dataPoints   样本集
     * @param radius       相似度阈值
     * @param objectNum    类簇最小样本数阈值
     * @return    如果是关键节点，返回其可达节点，否则返回null
     */
    private List<DataPoint> isKeyAndReturnObjects(DataPoint dataPoint,
                                                  List<DataPoint> dataPoints, double radius, int objectNum) {

        List<DataPoint> arrivableObjects= new ArrayList<DataPoint>();
        for(DataPoint dp:dataPoints){
            double distance = getDistance(dataPoint, dp);
            if(distance>=radius){
                arrivableObjects.add(dp);
            }

        }

        if(arrivableObjects.size()>=objectNum){
            dataPoint.setKey(true);
            return arrivableObjects;
        }
        return null;
    }

    /**
     * 判断一个样本是否在另一个簇中
     * @param dp   样本
     * @param dps  簇
     * @return
     */
    private boolean isContain(DataPoint dp,List<DataPoint> dps){
        boolean flag=false;
        String name = dp.getDataPointName().trim();
        for(DataPoint tempDp:dps){
            String tempName=tempDp.getDataPointName().trim();
            if(name.equals(tempName)){
                flag=true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断两个节点簇是否可以合并
     * @param dps1
     * @param dps2
     * @return
     */
    private boolean mergeList(List<DataPoint> dps1,List<DataPoint> dps2){
        int count=0;
        boolean flag=false;
        if(dps1==null||dps2==null||dps1.size()==0||dps2.size()==0){
            return flag;
        }

        for(DataPoint dp:dps2){
            if(dp.isKey()&&isContain(dp, dps1)){
                count++;
                if(count> dps1.size()/2&&count>dps2.size()/2){
                    flag=true;
                    break;
                }

            }
        }
        //如果可以合并，把间接密度可达的点加入某一类簇中
        if(flag){
            for(DataPoint dp:dps2){
                if(!isContain(dp,dps1)){
                    DataPoint tempDp= new DataPoint(dp.getSession(),dp.getDataPointName(),dp.isKey());
                    dps1.add(tempDp);
                }
            }
        }

        return flag;
    }


    /**
     * 主函数，用户验证DBScan的聚类结果
     * @param args
     */
    public static void main(String[] args){
        ActivityRouteInfoBasicService activityRouteInfoBasicService = new ActivityRouteInfoBasicService();
        transRecords = activityRouteInfoBasicService.getSessions("85d4a553-ee8d-4136-80ab-2469adcae44d");
        ArrayList<DataPoint> dpoints = new ArrayList<DataPoint>();


        for(int i=0;i<transRecords.size();i++){
            dpoints.add(new DataPoint(transRecords.get(i),i+"",false));
        }

        DBScan dbScan=new DBScan();
        List<Cluster> clusterList=dbScan.doDbscanAnalysis(dpoints, 0.6, 3);
        List<List<String>> stringList=dbScan.cluster2StringList(clusterList);
        dbScan.displayCluster(stringList);

    }
}

