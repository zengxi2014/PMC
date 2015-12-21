package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 * 样本点
 */
public class DataPoint {
    private String dataPointName;  //样本名称
    public String getDataPointName() {
        return dataPointName;
    }

    public void setDataPointName(String dataPointName) {
        this.dataPointName = dataPointName;
    }

    private Session session;    //样本点的数据值，实质上是页面的访问顺序记录
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private boolean isKey;         //是否是核心对象

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }

    public DataPoint(){}

    public DataPoint(Session session,String dataPointName,boolean isKey){
        this.dataPointName=dataPointName;
        this.session=session;
        this.isKey=isKey;
    }
}



