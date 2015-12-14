package scut.zengxi.Clustering;

import java.text.DecimalFormat;

/**
 * Created by zengxi on 2015/12/8.
 */
public class Global {
  //  private static DecimalFormat df3  = new DecimalFormat("###.000");     //���ڱ���С�������λ
    /**
     * ���������û������ƶȾ��ࣺ�˴���ŷʽ������Ϊ���ƶ�
     * @param data1  ��һ������û�1����
     * @param data2  ��һ������û�2����
     * @return  �û����ƶ�
     */
    public static double calEuraDist(double[] data1,double[] data2){
        double d = 0.0;
        for(int i=0;i<data1.length;i++){
            d+=Math.pow((data1[i] - data2[i]),2);
        }
        d=Math.sqrt(d);
        return d;
    }
}
