package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 *
 * ��Ӧ��һ���Ự����
 */
import java.util.List;

public class Session {
    private String userId;    //�û�id
    private List<Page> sessionList;    //����ҳ��ļ�¼
    public Session(){}

    public Session(String userId, List<Page> sessionList) {
        super();
        this.userId = userId;
        this.sessionList = sessionList;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public List<Page> getSessionList() {
        return sessionList;
    }
    public void setSessionList(List<Page> sessionList) {
        this.sessionList = sessionList;
    }


    public void tostring(){
        System.out.println();
        System.out.println("=====================");
        System.out.println("userId="+this.userId);
        System.out.println("session length="+this.sessionList.size());
        for(Page page:sessionList){
            System.out.print(page.getPageName() + " ");
        }
    }

}
