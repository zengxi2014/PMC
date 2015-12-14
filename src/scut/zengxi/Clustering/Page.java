package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 * 对应于一个页面对象
 */
public class Page {
    private String pageName;    //页面名称
    private double viewTime;    //访问时间

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
