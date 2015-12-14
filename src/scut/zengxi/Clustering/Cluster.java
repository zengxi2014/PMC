package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 *
 * 簇类对象.
 */
import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private List<DataPoint> dataPoints= new ArrayList<DataPoint>();     //类簇中的样本点
    private String clusterName;     //簇名

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public List<DataPoint> getDataPoints(){
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints){
        this.dataPoints=dataPoints;
    }


}
