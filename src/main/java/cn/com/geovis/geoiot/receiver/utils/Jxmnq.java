package cn.com.geovis.geoiot.receiver.utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Jxmnq implements Jx{

    @Override
    public Object receive(String... args) throws Exception {
        //System.out.println(udp());
        String id = UUID.randomUUID().toString();
        Map stl = JSON.parseObject(udp(), Map.class);
        Map lowMap  = new HashMap();

       // System.out.println("上报时间a"+getStringToDate("2018-08-22T23:33:24.593"));;
        for(Object key:stl.keySet()){
            String k = (String)key;
            //System.out.println(key+"=="+stl.get(key));

            if(key.equals("SBSJ")){
              /*String o = (String) stl.get(key);
                String sjq = o.substring(0, 10);
                String sjh = o.substring(11);
                long sj = getStringToDate(sjq + " " + sjh);
                System.out.println(sj);
                lowMap.put("sj",sj);*/
                long sj = getStringToDate();
                lowMap.put("sj",sj);

            } else if (key.equals("JXH")&&stl.get("JXH")!=null){
                lowMap.put("id",stl.get(key));

            }else if (key.equals("JXH")&&stl.get("JXH")==null){
                String LBDW = (String) stl.get("LBDW");
                String  MBBH  = (String) stl.get("MBBH");
                lowMap.put("id",(LBDW+MBBH));
            }

            lowMap.put(k.toLowerCase(),stl.get(key));

        }
//        if (lowMap.get("JXH")!=null){
//            lowMap.put("id",stl.get("JXH"));
//        }else if(lowMap.get("JXH")==null&&lowMap.get("LBDW")!=null&&lowMap.get("MBBH")!=null) {
//            String LBDW = (String) stl.get("LBDW");
//            String  MBBH  = (String) stl.get("MBBH");
//            lowMap.put("id",(LBDW+MBBH));
//        }
        return lowMap;
    }

    public static String udp() throws IOException {
        //创建接收端socket对象
        DatagramSocket Socket = new DatagramSocket(8888);
        //创建包对象
        byte[] buf = new byte[1024];
        DatagramPacket Packet = new DatagramPacket(buf, buf.length);
        //String s = new String(Packet.getData(),0, Packet.getLength());
        byte[] data = Packet.getData();
        int length = Packet.getLength();
        //接收数据
        Socket.receive(Packet);
        String s = new String(data);
        int a = s.lastIndexOf("{");
        int b = s.lastIndexOf("}");
        String substring = s.substring(a,b+1);
        System.out.println(substring);
        //释放资源
        Socket.close();
        return substring;
    }
    public static long getStringToDate(){
    /*    SimpleDateFormat sf =null;
              sf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
             date = sf.parse(tiem);
        }catch(Exception e){
            e.printStackTrace();
        }

          return date.getTime();
          */

        long l = System.currentTimeMillis();
        //System.out.println("时间:"+l);
        return l;
    }


}
