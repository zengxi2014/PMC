package scut.zengxi.Clustering;

import com.pmc.Activityroute.service.ActivityRouteInfoBasicService;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by zengxi on 2015/12/7.
 */
public class KMeans {
    private List<Session> transRecords= new LinkedList<Session>();   //�洢���ݿ�����
    private DecimalFormat df3  = new DecimalFormat("###.000");     //���ڱ���С�������λ
    private Set userSet;
    private ArrayList<DataObject> bestObjects = new ArrayList<>();
    private double bestSatisfy=Double.MAX_VALUE;
    private int k;  //ָ�����ֵĴ���
    private double mu=0.0000000000000000000001;  //������ֹ�����������������������������ƫ����С��muʱ��ֹ����
    private double [][]centers;  //������һ�θ������ĵ�λ��
    private int len; //���ݵ�ά��

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public double getMu() {
        return mu;
    }

    public void setMu(double mu) {
        this.mu = mu;
    }
    //    public KMeans(){}

    /*public KMeans(int k,int len){
        super();
        this.k=k;
        centers= new double [k][];
        for(int i=0;i<k;i++)
            centers[i]=new double[len];
    }*/

    /**
     * ��ʼ��������
     */
    public void init(){
        centers= new double [k][];
        for(int i=0;i<k;i++)
            centers[i]=new double[len];
    }

    /**
     * ����appId��������
     * @param appId
     */
    private void loadData(String appId,int numK){
        ActivityRouteInfoBasicService activityRouteInfoBasicService = new ActivityRouteInfoBasicService();
        //��ȡ���ݿ������
        transRecords = activityRouteInfoBasicService.getSessions(appId);

        ArrayList<DataPoint> dpoints = new ArrayList<DataPoint>();
        //�����ݿ������ת��ΪDataPoint��ʽ
        for(int i=0;i<transRecords.size();i++){
            dpoints.add(new DataPoint(transRecords.get(i),i+"",false));
        }

        DBScan dbScan=new DBScan();
        //ִ�е�һ����࣬���ƶ���ֵΪ0.6�������С��ĿΪ3
        List<Cluster> clusterList=dbScan.doDbscanAnalysis(dpoints, 0.6, 3);
        //ת��Ϊ���û�id�ַ�����ɵ�����б�
        List<List<String>> stringList=dbScan.cluster2StringList(clusterList);
        //dbScan.displayCluster(stringList);
        //��ȡ���е��û�id
        List<String> userList=getUserSet(stringList);
        //��������ڵڶ���������������
        double [][]dataMatrix = getDataMatrix(userList,stringList);
        //ע�⣺�˴���userList.size()==dataMatrix.length
        ArrayList<DataObject> dataObjects = genDataObjects(userList,dataMatrix);
        int len =0;
        if(dataObjects!=null) len = dataObjects.get(0).getVector().length;
        for(int i=0;i<dataObjects.size();i++){
            for(int j=0;j<len;j++){
                System.out.print(dataObjects.get(i).getVector()[j] + " ");
            }
            System.out.println();
        }
        KMeans kMeans = new KMeans();
        kMeans.setK(numK);
        kMeans.setLen(len);
        kMeans.init();
        //������õĴ���������������û�����һ�����ã���ʵ���ǹ����ˣ�
        if(kMeans.getK()>userList.size()/2)kMeans.setK(userList.size()/2);
        //����kmeans����
        kMeans.run(dataObjects, len);
//        System.out.println("----------------------------"+getSatisfy(dataObjects));
        kMeans.printResult(dataObjects, kMeans.getK());
    }

    /**
     * ��ȡ������DBScan����е������û�����
     * @param stringList   dbscan�Ľ��
     * @return
     */
    private List getUserSet(List<List<String>> stringList){
        userSet=new HashSet<String>();
        for(List<String> list :stringList){
            for(String userId: list){
                userSet.add(userId);
            }
        }
        List<String> userList= new ArrayList<>();
        System.out.println("================");
        //��set�е��û�����List�У���Ϊlist�������
        for(Object userId:userSet){
            userList.add(userId.toString());
        }
        for(String userId:userList){
            System.out.println(userId);
        }
        return userList;
    }

    /**
     * �����ݶ���������䵽k�����в���ʼ��k�����ģ�ÿ��������lenά������
     * @param objects
     */
    public void initCenter(ArrayList<DataObject> objects,int len){
        Random random = new Random(System.currentTimeMillis());
        int []count=new int[k];  //��¼ÿ�������ж��ٸ�Ԫ��
        int num =0;
        Iterator<DataObject> iter = objects.iterator();
        while(iter.hasNext()){
            DataObject object = iter.next();
           // int id= random.nextInt(10000)%k;      //��������ݹ���k���أ��˷������ܱ�֤ÿ�����о����㷨�Ľ��һ������Ϊ��ʼ���ĵ��趨��Ӱ�����Ľ��
            int id = num++%k;   //��������ݹ���K���ص���һ���������˷������Ա�֤ÿ�����о����㷨�Ľ��һ��
            object.setCid(id);
            count[id]++;
            for(int i=0;i<len;i++){
                centers[id][i]+=object.getVector()[i];
            }
        }
        for(int i=0;i<k;i++){
            for(int j=0;j<len;j++){
                centers[i][j]/=count[i];    //ͨ����ƽ��ֵ�õ�����ĳ�ʼ��������
            }
        }
    }

    /**
     * Ϊÿ�����ݶ������»��ֵ�����������Ǹ�����
     * @param objects
     */
    public void classify(ArrayList<DataObject> objects){
        Iterator<DataObject> iter= objects.iterator();
        while(iter.hasNext()){
            DataObject object=iter.next();
            double [] vector = object.getVector();
            int index =0;
            double neardist = Double.MAX_VALUE;
            for(int i=0;i<k;i++){
                double dist = Global.calEuraDist(vector,centers[i]);   //����ÿ��������ʹ������ĵ�ŷʽ����
                if(dist<neardist){
                    neardist = dist;
                    index = i;
                }
            }
            object.setCid(index);
        }
    }

    /**
     * ���¼���ÿ���ص����ģ����ж���ֹ�����Ƿ����㣬�����������������¸��ص�����,�������ͷ���true
     * @param objects  ���ݼ�
     * @return
     */
    public boolean calNewCenter(ArrayList<DataObject> objects,int len){
        Random random = new Random(System.currentTimeMillis());
        boolean flag=true;
        int [] count = new int[k];   //���ڱ���ÿ�������������������
        double [][]sum= new double[k][];
        for(int i=0;i<k;i++){
            sum[i] = new double[len];
        }
        Iterator<DataObject> iter= objects.iterator();
        while(iter.hasNext()){
            DataObject object = iter.next();
            int id = object.getCid();
            count[id]++;
            for(int i=0;i<len;i++){
                sum[id][i]+=object.getVector()[i];
            }
        }
        //����ÿ������ļ�Ȩƽ��ֵ
        for(int i=0;i<k;i++){
            if(count[i]!=0){
                for(int j=0;j<len;j++){
                    sum[i][j]/=count[i];
                }
            }
            // ���в������κε�,��ʱ�������ģ�������Ŀ����Ϊ�˱�֤ÿ�������ж��������ݵ㣬Ҳ���Բ��������������������ܳ��ֲ��������ݵ�Ĵ���
          /*  else{
                System.out.println("----------equal zero--------------");
                int a=(i+random.nextInt(10000))%k;
                int b=(i+random.nextInt(10000))%k;
                int c = (i+random.nextInt(10000))%k;
                for(int j=0;j<len;j++){
                    centers[i][j]=(centers[a][j]+centers[b][j]+centers[c][j])/3;
                }
            }*/
        }

        for(int i=0;i<k;i++){
            // ֻҪ��һ��������Ҫ�ƶ��ľ��볬����mu����˵���������Ļ����ȶ�
            if(Global.calEuraDist(sum[i],centers[i])>=mu){
                flag=false;
                break;
            }

        }
        //������������Ѿ��ȶ����򽫴������ĸ���Ϊ��Ȩƽ��ֵ
        if(!flag){
            for(int i=0;i<k;i++){
                for(int j=0;j<len;j++){
                    centers[i][j]=sum[i][j];
                }
            }
        }

        return flag;
    }

    /**
     * ������������Ч������ָ������ʼ���ĵĲ�ͬ����ͬ���ڳ�ʼ������ʹ�õ�һ�׷����ǿ������ϣ�Ŀǰʹ�õڶ��׷���������Ч��ֵ���ᷢ���䶯
     * @param objects
     * @return
     */
    public double getSatisfy(ArrayList<DataObject> objects){
        int len =0;
        if(objects!=null)
            len= objects.get(0).getVector().length;
        double satisfy = 0.0;
        int []count = new int[k];
        double [] ss = new double[k];
        Iterator<DataObject> iter = objects.iterator();
        //����ÿ�������ڲ��ļ�Ȩƽ�����ֵ
        while(iter.hasNext()){
            DataObject object = iter.next();
            int id = object.getCid();
            count[id]++;
            for(int i=0;i<len;i++){
                ss[id]+=Math.pow(object.getVector()[i]-centers[id][i],2.0);
            }
        }
        //������������ֵ
        for(int i=0;i<k;i++){
            satisfy+=count[i]*ss[i];
        }
       // System.out.println("satisfy="+satisfy);
        return satisfy;
    }

    /**
     * ����kmeans�����㷨�ĺ���
     * @param objects  ������
     * @param len  ������������
     */
    public void run(ArrayList<DataObject> objects,int len){
            initCenter(objects,len);    //��ʼ����������
            classify(objects);          //�����ƶȽ�ÿ�������㻮�ֵ������������Ŀ
            int iterNum=1;              //��¼��������
            while(!calNewCenter(objects,len)){    //����������Ĳ��ȶ�����������
                iterNum++;
                System.out.println("=======current iternum:"+iterNum+"============");
                classify(objects);
                if(iterNum>1000)break;

            }
/*            if(getSatisfy(objects)<bestSatisfy){
                bestObjects=null;
                bestObjects=copyDataObject(objects);
                bestSatisfy=getSatisfy(objects);
            }*/
            System.out.println("errors:"+getSatisfy(objects));
            System.out.println("iterator number:"+iterNum);
            System.out.println("*************************************************");


      //  System.out.println("--------bestSatisfy="+bestSatisfy+":"+getSatisfy(bestObjects)+"--------------");
        //��ӡ���
    }

    /**
     * ������ȸ������ݼ����Ǵ���һ���µ����ݼ���������ֻ��������
     * @param objects
     * @return
     */
    public ArrayList<DataObject> copyDataObject(ArrayList<DataObject> objects){
        ArrayList<DataObject> res = new ArrayList<>();
        for(DataObject object: objects){
            DataObject newObject = new DataObject();
            newObject.setUserId(object.getUserId());
            newObject.setVector(object.getVector());
            newObject.setCid(object.getCid());
            res.add(newObject);
        }
        return res;
    }

    /**
     * ���ڴ�ӡ������
     * @param objects
     * @param k
     */
    public void printResult(ArrayList<DataObject> objects,int k){
        List<UserCluster> userClusters = new ArrayList<>();
        //���k������
        for(int i=0;i<k;i++){
            UserCluster userCluster = new UserCluster();
            userCluster.setName(i+"");
            userClusters.add(userCluster);
        }
        for(DataObject object:objects){
            //���մ���id����������ӽ���Ӧ�������
            userClusters.get(object.getCid()).add(object.getUserId());
        }
        //������಻Ϊ��(��������һ��������),���ӡ������Ϣ
        for(int i=0;i<k;i++){
            if(!userClusters.get(i).isEmpty())userClusters.get(i).printCluster();
        }
    }
    /**
     * ��ȡ�ڶ����������ݵ㣺ÿ���û���Ϊ�ڸ���Ϊ����еı���
     * @param userList
     * @param stringList
     * @return
     */
    private double[][] getDataMatrix(List<String> userList,List<List<String>> stringList){
        double [][]dataMatrix = new double[userSet.size()][stringList.size()];
        for(int i=0;i<userList.size();i++){
            for(int j=0;j<stringList.size();j++){
                int count=0;
                for(int k=0;k<stringList.get(j).size();k++){
                    if(userList.get(i).equals(stringList.get(j).get(k)))count++;
                }
                dataMatrix[i][j]=count;
            }
        }

        for(int i=0;i<dataMatrix.length;i++){
            double sum=0;
            for(int j=0;j<dataMatrix[i].length;j++){
                sum+=dataMatrix[i][j];
            }
            for(int j=0;j<dataMatrix[i].length;j++){
                dataMatrix[i][j]=Double.parseDouble(df3.format(dataMatrix[i][j]*1.0/sum));
            }
        }
        return dataMatrix;
    }

    /**
     * ����DataObject����
     * @param list  �û�ID�б�
     * @param dataMatrix   �û�����
     * @return
     */
    private ArrayList<DataObject> genDataObjects(List<String> list, double[][] dataMatrix){
        ArrayList<DataObject> dataObjects = new ArrayList<DataObject>();
        for(int i=0;i<list.size();i++){
            DataObject dataObject = new DataObject();
            dataObject.setUserId(list.get(i).toString());
            dataObject.setVector(dataMatrix[i]);
            dataObjects.add(dataObject);
        }
        return dataObjects;
    }



    @Test
    public void testLoadData(){
        loadData("85d4a553-ee8d-4136-80ab-2469adcae44d",4);
    }

}
