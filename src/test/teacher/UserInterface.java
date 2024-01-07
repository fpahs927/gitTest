package test.teacher;

import test.teacher.SimpleMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface {
    private SimpleMonitor _mon = new SimpleMonitor(){
        @Override
        public void log(String message) {
            System.out.println(message);
        }
    };
    private BufferedReader reader=null;
    public void begin(){
        java.io.InputStream inputStream = System.in;
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }
    public void end(){
        if(reader !=null)
            return;
        try{
            reader.close();

        }catch (IOException e){

        }

    }
    private int index = 0;
    public String waitFor() {
        if(reader==null)return "exit";
        getMonitor().log("waitFor(" +index++ +")");
        try {
           // BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String data = reader.readLine(); //System.in.read(); //내부적으로 blocking 함수라서 thread가 정지된다 //내가 뭔가를 입력할 때까지 정지된다
            getMonitor().log(data);
            return data;
        }
        catch (IOException e){}
        return "err";
//            //in 값에 flush가 되어야한다, flush가 되지않으면 무한정 멈춰져있다. multithread를 다음에 배운다 1228
//            getMonitor().log(data);
//            reader.close();
//        } catch (IOException e) {
//        }
//        try {
//            Thread.sleep(1000); //1초  이것또한 blocking 함수라서 thread가 시간을 멈춰준다
//        } catch (InterruptedException e) {
//        }

    }

    public SimpleMonitor getMonitor() {
        return _mon;
    }

}
