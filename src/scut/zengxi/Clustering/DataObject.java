package scut.zengxi.Clustering;

/**
 * ��������
 * Created by zengxi on 2015/12/8.
 */
public class DataObject {
    private String userId;   //�û�id
    private int cid=-1;     //����id���㷨��ʼǰ�����ݲ������κδ��࣬��������Ϊ-1
    private double[] vector;  //�û�����

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
