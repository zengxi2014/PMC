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
    private static List<String> commSeq;    //用于保存共同路径片段
    private static List<Session> transRecords= new LinkedList<Session>();    //用于保存数据集
    private static DecimalFormat df3  = new DecimalFormat("###.000");     //用于保留小数点后三位
    public static void init(){
        ActivityRouteInfoBasicService activityRouteInfoBasicService = new ActivityRouteInfoBasicService();
        transRecords = activityRouteInfoBasicService.getSessions("85d4a553-ee8d-4136-80ab-2469adcae44d");


        //模拟第一个会话
       /* List<Page> sessionAList= new ArrayList<Page>();
        sessionAList.add(new Page("P1",6.5));
        sessionAList.add(new Page("P2",8.5));
        sessionAList.add(new Page("P3",5));
        sessionAList.add(new Page("P5",3));
        sessionAList.add(new Page("P4",4.5));
        Session sessionA=new Session("1", sessionAList);

        //模拟第二个会话
        List<Page> sessionBList= new ArrayList<Page>();
        sessionBList.add(new Page("P1",5.5));
        sessionBList.add(new Page("P2",7.5));
        sessionBList.add(new Page("P3",4.5));
        sessionBList.add(new Page("P4",10.5));
        sessionBList.add(new Page("P6",3));
        Session sessionB=new Session("2", sessionBList);*/
        double [][]simMatrix = new double[transRecords.size()][transRecords.size()];     //相似度矩阵
        //用于计算相似度矩阵
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
     * 计算两个会话的距离
     * @param sessionA
     * @param sessionB
     * @return
     */
    public static double similarity(Session sessionA,Session sessionB){
        double timeA=getSessionTime(sessionA);
        double timeB=getSessionTime(sessionB);
        commSeq=new ArrayList<String>();    //用于保存相似片段
        double sim=0.0;         //相似度

        double comTimeA=0.0;     //A会话中相似片段的总时间
        double comTimeB=0.0;     //B会话中相似片段的总时间

        List<Page> sessionAList=sessionA.getSessionList();    //获取会话A的页面访问序列
        List<Page> sessionBList=sessionB.getSessionList();
        String []pageSeqA=getPageSeq(sessionA);
        String []pageSeqB=getPageSeq(sessionB);
        //动态规划法求相似片段
        int[][] b = getLength(pageSeqA, pageSeqB);
        Display(b, pageSeqA, pageSeqA.length-1, pageSeqB.length-1);
        //用于记录相似片段在会话A,B中的每一个页面的位置
        int [] posA= new int[commSeq.size()];
        int [] posB= new int[commSeq.size()];
        //打印相似片段
        for (int i = 0; i < commSeq.size(); i++) {
            System.out.print(commSeq.get(i)+" ");
        }
        System.out.println();
        //计算相似片段中的每个页面在A会话序列中的位置
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
        //计算相似片段中的每个页面在B会话序列中的位置
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
        //计算相似度
        for (int i = 0; i < commSeq.size(); i++) {
            sim+=Math.sqrt((Math.min(sessionAList.get(posA[i]).getViewTime(), sessionBList.get(posB[i]).getViewTime())/Math.max(sessionAList.get(posA[i]).getViewTime(), sessionBList.get(posB[i]).getViewTime())));
            comTimeA+=sessionAList.get(posA[i]).getViewTime();
            comTimeB+=sessionBList.get(posB[i]).getViewTime();
        }
        //如果相似片段的页面数小于2，设置相似度为0
        if(commSeq.size()>2)
            sim=Math.sqrt(sim/commSeq.size());
        else
            sim=0;
        System.out.println("TimeA = "+timeA+" ,TimeB = " +timeB );
        System.out.println("comTimeA = "+comTimeA+" ,comTimeB = " +comTimeB );
        //多次开根号以降低时间对相似度的影响权重
        sim=sim*Math.sqrt(Math.sqrt(Math.sqrt(comTimeA/timeA)*Math.sqrt(comTimeB/timeB)));
        sim=Double.parseDouble(df3.format(sim));
        System.out.println("sim = "+sim);
        //	System.out.println("========"+(4.5/10.5+5.5/6.5+7.5/8.5+0.9)*Math.sqrt(comTimeA/timeA)*Math.sqrt(comTimeB/timeB)/4);
        return sim;
    }

    //获取一个会话的访问总时间
    public static double getSessionTime(Session session){
        double time= 0.0;
        List<Page> sessionList= session.getSessionList();
        for (int i = 0; i < sessionList.size(); i++) {
            time+=sessionList.get(i).getViewTime();
        }
        return time;
    }

    /**
     * 将一个会话中的访问记录放入一个数组中
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
     * @return 返回一个记录决定搜索的方向的数组
     */
    public static int[][] getLength(String[] x, String[] y)
    {
        int[][] b = new int[x.length][y.length];
        int[][] c = new int[x.length][y.length];

        for(int i=1; i<x.length; i++)
        {
            for(int j=1; j<y.length; j++)
            {
                //对应第一个性质
                if( x[i].toString().equals(y[j].toString()))
                {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = 1;
                }
                //对应第二或者第三个性质
                else if(c[i-1][j] >= c[i][j-1])
                {
                    c[i][j] = c[i-1][j];
                    b[i][j] = 0;
                }
                //对应第二或者第三个性质
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
    //回溯的基本实现，采取递归的方式
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
