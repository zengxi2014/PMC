package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 * ������
 */
public class DataPoint {
    private String dataPointName;  //��������
    public String getDataPointName() {
        return dataPointName;
    }

    public void setDataPointName(String dataPointName) {
        this.dataPointName = dataPointName;
    }

    private Session session;    //�����������ֵ��ʵ������ҳ��ķ���˳���¼
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private boolean isKey;         //�Ƿ��Ǻ��Ķ���

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



