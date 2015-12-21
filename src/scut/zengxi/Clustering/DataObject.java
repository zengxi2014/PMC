package scut.zengxi.Clustering;

/**
 * 样本数据
 * Created by zengxi on 2015/12/8.
 */
public class DataObject {
    private String userId;   //用户id
    private int cid=-1;     //簇类id，算法开始前，数据不属于任何簇类，所以设置为-1
    private double[] vector;  //用户向量

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }

}
