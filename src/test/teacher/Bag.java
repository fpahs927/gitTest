package test.teacher;

import java.util.ArrayList;


public class Bag {
    private ArrayList<Object> list = new ArrayList<Object>(6); //미리 공간을 만들어놓는다

    public void addItem(Object lotto) {
        if(list.contains(lotto))
            return;
        list.add(lotto);
    }
    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        for(Object i : list){
            if(sb.length()>0){
                sb.append(',');  //두번째부터 값을 넣겠다
            }
            sb.append(i); //슷자 배열을 예쁘게 하기 위해서
        }
        return "[" + sb.toString() +"]";
    }

    public Lotto getItemIsNotCompleted(Class<Lotto> lottoClass) {
        for(Object i :list){
            if(i instanceof Lotto){
                if(((Lotto)i).isCompleted()){
                    continue;
                }
                return (Lotto) i;
            }
        }return null;
    }

    public Lotto[] getItemAllCompleted() {
        ArrayList<Lotto> _lotto = new ArrayList<>(0);
        for(Object i :list){
            if(i instanceof Lotto){
                if(((Lotto)i).isCompleted()){
                    _lotto.add((Lotto) i);
                }
            }
        }
        Lotto[] result = _lotto.toArray(new Lotto[_lotto.size()]);
        _lotto.clear();
        return result;
    }
}
