package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 *
 * �������.
 */
import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private List<DataPoint> dataPoints= new ArrayList<DataPoint>();     //����е�������
    private String clusterName;     //����

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
