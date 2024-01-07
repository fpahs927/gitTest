package test.teacher;

import java.util.ArrayList;
import java.util.Comparator;

public class Lotto {
    private ArrayList<Integer> list = new ArrayList<Integer>(6); //미리 공간을 만들어놓는다
    private LottoCenter.Result result = LottoCenter.Result.Empty;

    //memory를 아끼기 위해서 empty와 fails를 static을 사용한다

    public LottoCenter.Result checkWinner(Lotto winner, int bonusNumber) {
        int count = 0;
        boolean hasbonusNumber = false;
        for (Integer i : list) {
            if (winner.list.contains(i)) {
                count++;
            }
        }
        result = LottoCenter.Result.Fails;

        if (count == 5) {
            if (list.contains(bonusNumber))
                hasbonusNumber = true;
        }
        if(count==3)result = new LottoCenter.Result(5,this);
        if(count==4)result = new LottoCenter.Result(4,this);
        if(count==5){
            if(hasbonusNumber)
                result = new LottoCenter.Result(2,this);
            else
                result = new LottoCenter.Result(3,this);
        }
        if(count==6)result = new LottoCenter.Result(1,this);

        return result;
    }
    //int의 class 형태는 integer
    public boolean addNumber(int number) {
        if (list.size() == 6) return false;
        if (list.contains(number))
            return false;
        if (0 < number && number < 46) {
            list.add(number);
            return true;
        } else {
            return false;
        }
    }

    public boolean isCompleted() {
        return list.size() == 6;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer i : list) {
            if (sb.length() > 0) {
                sb.append(' ');  //두번째부터 값을 넣겠다
            }
            sb.append(String.format("%02d", i)); //슷자 배열을 예쁘게 하기 위해서
        }
        if(!result.isEmpty()){
            return "lotto["+sb.toString()+" :["+result + "]";
        }
        if (registerdTime != -1) {
            return "lotto[" + sb.toString() + "등록됨]";
        }
        if (isCompleted())
            return "lotto[" + sb.toString() + "]";
        else {
            return "미완성 lotto[" + sb.toString() + "]";

        }
    }

    public void sortNumber() {
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);  //o1 o2 순서 바꾸면 정렬 숫자가 바뀐다
            }
        });
    }

    public void setRandom() {
        setRandom(null);
    }

    public void setRandom(SimpleMonitor monitor) {
        int tempNumber;
        while (!this.isCompleted()) {
            tempNumber = LottoCenter.makeLottoNumber();
            if (this.addNumber(tempNumber)) {
                if(monitor !=null)
                    monitor.log(list.size() + "번째 숫자는 " + tempNumber + "]");
            }
            this.sortNumber();
        }
    }

    public void setLottoFromScript(String[] tokens) {
        int index = 0;
        while (index < tokens.length && !this.isCompleted())
            try {
                this.addNumber(Integer.parseInt(tokens[index++].trim())); //string에서 inte 바꾸고 범위 넣게 하지 않기 위해서
                //trim은  앞뒤로 비어있는걸 없애는거다
            } catch (Exception ex) {

            }
        this.sortNumber();
    }

    private long registerdTime = -1;

    public void setRegistratedTime(long registerdTime) {
        this.registerdTime = registerdTime;
    }

    public boolean checkNumbercontains(int number) {
        for (Integer i : list) {
            if (((int) i) == number) {
                return true;
            }
        }
        return false;
    }
}
