package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 * ��Ӧ��һ��ҳ�����
 */
public class Page {
    private String pageName;    //ҳ������
    private double viewTime;    //����ʱ��

    public Page(){}
    public Page(String pageName, double viewTime) {
        super();
        this.pageName = pageName;
        this.viewTime = viewTime;
    }
    public String getPageName() {
        return pageName;
    }
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
    public double getViewTime() {
        return viewTime;
    }
    public void setViewTime(double viewTime) {
        this.viewTime = viewTime;
    }
}
