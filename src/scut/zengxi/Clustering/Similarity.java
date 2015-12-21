package scut.zengxi.Clustering;

/**
 * Created by zengxi on 2015/12/1.
 */
import com.pmc.Activityroute.service.ActivityRouteInfoBasicService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.text.DecimalFormat;

public class Similarity {
    private static List<String> commSeq;    //���ڱ��湲ͬ·��Ƭ��
    private static List<Session> transRecords= new LinkedList<Session>();    //���ڱ������ݼ�
    private static DecimalFormat df3  = new DecimalFormat("###.000");     //���ڱ���С�������λ
    public static void init(){
        ActivityRouteInfoBasicService activityRouteInfoBasicService = new ActivityRouteInfoBasicService();
        transRecords = activityRouteInfoBasicService.getSessions("85d4a553-ee8d-4136-80ab-2469adcae44d");


        //ģ���һ���Ự
       /* List<Page> sessionAList= new ArrayList<Page>();
        sessionAList.add(new Page("P1",6.5));
        sessionAList.add(new Page("P2",8.5));
        sessionAList.add(new Page("P3",5));
        sessionAList.add(new Page("P5",3));
        sessionAList.add(new Page("P4",4.5));
        Session sessionA=new Session("1", sessionAList);

        //ģ��ڶ����Ự
        List<Page> sessionBList= new ArrayList<Page>();
        sessionBList.add(new Page("P1",5.5));
        sessionBList.add(new Page("P2",7.5));
        sessionBList.add(new Page("P3",4.5));
        sessionBList.add(new Page("P4",10.5));
        sessionBList.add(new Page("P6",3));
        Session sessionB=new Session("2", sessionBList);*/
        double [][]simMatrix = new double[transRecords.size()][transRecords.size()];     //���ƶȾ���
        //���ڼ������ƶȾ���
        for(int i=0;i<transRecords.size();i++){
            for(int j=0;j<transRecords.size();j++){
                System.out.println("======"+i+"---"+j+"=======");
                if(i==j){
                    simMatrix[i][j]=Double.parseDouble(df3.format(0.0));
                }else{
                    simMatrix[i][j]=similarity(transRecords.get(i),transRecords.get(j));
                }

            }
        }
    }

    /**
     * ���������Ự�ľ���
     * @param sessionA
     * @param sessionB
     * @return
     */
    public static double similarity(Session sessionA,Session sessionB){
        double timeA=getSessionTime(sessionA);
        double timeB=getSessionTime(sessionB);
        commSeq=new ArrayList<String>();    //���ڱ�������Ƭ��
        double sim=0.0;         //���ƶ�

        double comTimeA=0.0;     //A�Ự������Ƭ�ε���ʱ��
        double comTimeB=0.0;     //B�Ự������Ƭ�ε���ʱ��

        List<Page> sessionAList=sessionA.getSessionList();    //��ȡ�ỰA��ҳ���������
        List<Page> sessionBList=sessionB.getSessionList();
        String []pageSeqA=getPageSeq(sessionA);
        String []pageSeqB=getPageSeq(sessionB);
        //��̬�滮��������Ƭ��
        int[][] b = getLength(pageSeqA, pageSeqB);
        Display(b, pageSeqA, pageSeqA.length-1, pageSeqB.length-1);
        //���ڼ�¼����Ƭ���ڻỰA,B�е�ÿһ��ҳ���λ��
        int [] posA= new int[commSeq.size()];
        int [] posB= new int[commSeq.size()];
        //��ӡ����Ƭ��
        for (int i = 0; i < commSeq.size(); i++) {
            System.out.print(commSeq.get(i)+" ");
        }
        System.out.println();
        //��������Ƭ���е�ÿ��ҳ����A�Ự�����е�λ��
        for(int i=0,j=0;i<sessionAList.size() && j<commSeq.size();){
            if(sessionAList.get(i).getPageName().equals(commSeq.get(j))){
                posA[j]=i;
                i++;
                j++;
            }else i++;
        }

        for (int i = 0; i < posA.length; i++) {
            System.out.print(posA[i]+" ");
        }
        System.out.println();
        //��������Ƭ���е�ÿ��ҳ����B�Ự�����е�λ��
        for(int i=0,j=0;i<sessionBList.size() && j<commSeq.size();){
            if(sessionBList.get(i).getPageName().equals(commSeq.get(j))){
                posB[j]=i;
                i++;
                j++;
            }else i++;
        }
        for (int i = 0; i < posB.length; i++) {
            System.out.print(posB[i]+" ");
        }
        System.out.println();
        //�������ƶ�
        for (int i = 0; i < commSeq.size(); i++) {
            sim+=Math.sqrt((Math.min(sessionAList.get(posA[i]).getViewTime(), sessionBList.get(posB[i]).getViewTime())/Math.max(sessionAList.get(posA[i]).getViewTime(), sessionBList.get(posB[i]).getViewTime())));
            comTimeA+=sessionAList.get(posA[i]).getViewTime();
            comTimeB+=sessionBList.get(posB[i]).getViewTime();
        }
        //�������Ƭ�ε�ҳ����С��2���������ƶ�Ϊ0
        if(commSeq.size()>2)
            sim=Math.sqrt(sim/commSeq.size());
        else
            sim=0;
        System.out.println("TimeA = "+timeA+" ,TimeB = " +timeB );
        System.out.println("comTimeA = "+comTimeA+" ,comTimeB = " +comTimeB );
        //��ο������Խ���ʱ������ƶȵ�Ӱ��Ȩ��
        sim=sim*Math.sqrt(Math.sqrt(Math.sqrt(comTimeA/timeA)*Math.sqrt(comTimeB/timeB)));
        sim=Double.parseDouble(df3.format(sim));
        System.out.println("sim = "+sim);
        //	System.out.println("========"+(4.5/10.5+5.5/6.5+7.5/8.5+0.9)*Math.sqrt(comTimeA/timeA)*Math.sqrt(comTimeB/timeB)/4);
        return sim;
    }

    //��ȡһ���Ự�ķ�����ʱ��
    public static double getSessionTime(Session session){
        double time= 0.0;
        List<Page> sessionList= session.getSessionList();
        for (int i = 0; i < sessionList.size(); i++) {
            time+=sessionList.get(i).getViewTime();
        }
        return time;
    }

    /**
     * ��һ���Ự�еķ��ʼ�¼����һ��������
     * @param session
     * @return
     */
    public static String[] getPageSeq(Session session){
        List<Page> sessionList= session.getSessionList();
        int length=sessionList.size()+1;
        String [] pageSeq=new String[length];
        pageSeq[0]="";
        for (int i = 0; i < sessionList.size(); i++) {
            pageSeq[i+1]=sessionList.get(i).getPageName();
        }
        return pageSeq;
    }

    /**
     * @param x
     * @param y
     * @return ����һ����¼���������ķ��������
     */
    public static int[][] getLength(String[] x, String[] y)
    {
        int[][] b = new int[x.length][y.length];
        int[][] c = new int[x.length][y.length];

        for(int i=1; i<x.length; i++)
        {
            for(int j=1; j<y.length; j++)
            {
                //��Ӧ��һ������
                if( x[i].toString().equals(y[j].toString()))
                {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = 1;
                }
                //��Ӧ�ڶ����ߵ���������
                else if(c[i-1][j] >= c[i][j-1])
                {
                    c[i][j] = c[i-1][j];
                    b[i][j] = 0;
                }
                //��Ӧ�ڶ����ߵ���������
                else
                {
                    c[i][j] = c[i][j-1];
                    b[i][j] = -1;
                }
            }
        }
//	    System.out.println(c[x.length-1][y.length-1]);
        return b;
    }
    //���ݵĻ���ʵ�֣���ȡ�ݹ�ķ�ʽ
    public static void Display(int[][] b, String[] x, int i, int j)
    {
        if(i == 0 || j == 0)
            return;

        if(b[i][j] == 1)
        {
            Display(b, x, i-1, j-1);
//	      System.out.print(x[i] + " ");
            commSeq.add(x[i]);
        }
        else if(b[i][j] == 0)
        {
            Display(b, x, i-1, j);
        }
        else if(b[i][j] == -1)
        {
            Display(b, x, i, j-1);
        }
    }

    public static void main(String[] args) {
        init();
    }
}
