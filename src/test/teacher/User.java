package test.teacher;

import test.teacher.*;

public class User {
    private String name;
    private long lifetime;
    private long birthtime; //생성시간, 살아있는 시간
    private long currentLifeTimeout=30;
    public User(String name, long lifetime) {

        this.name= name;
        birthtime=System.currentTimeMillis();
        this.currentLifeTimeout=lifetime;
        touch();
    }
    public void touch(){
        lifetime=System.currentTimeMillis()+1000*currentLifeTimeout; //1000이 1초다
        //현재시점에서 30초 동안 살아있게 만드는 소스코드
    }
    public void initialize(SimpleMonitor mon) {
        mon.log("초기화되었습니다" + name);
    }

    public boolean onPlay(SimpleMonitor mon) {
        long timeOut=lifetime-System.currentTimeMillis();
        //30초가 지나면 -가 된다

        if(timeOut>0){
            mon.log("노는 중 "+name +"남은 시간:" + timeOut+")");
            return true;
        }
        return false;
    }

    public void dispose(SimpleMonitor mon) {
        mon.log("나감"+ name);
    }

    private Bag bag = new Bag();
    public boolean onCommand(SimpleMonitor mon, String currentCommand) {
        if(currentCommand.equals("eat")){
            mon.log("식사를 하셨습니다");
            this.touch(); //touch를 불러와서 30초가 다시 생긴다
            return true;
        }
        if(currentCommand.equals("show bag") || currentCommand.equals("sb")){
            mon.log(bag.toString());
            return true;
        }
        if(currentCommand.equals("regist lotto")|| currentCommand.equals("rl")){

            if(city==null){
                mon.log("사는 곳이 없어요");
                return true;
            }
            LottoCenter currentCenter = city.getBuilding("lottocenter");

            if(currentCenter==null){
                mon.log("lottocenter가 없어요");
                return true;
            }

            currentCenter.registAllLottos(bag.getItemAllCompleted());
            mon.log("로또를 등록했어요" +bag);
            return true;
        }
        if(currentCommand.startsWith("write lotto")||currentCommand.startsWith("wl")) {
            this.touch();
            Lotto lotto = bag.getItemIsNotCompleted(Lotto.class);
            if(lotto==null){
                mon.log("사용할 수 있는 로또가 없습니다");
                return true;
            }
            if(currentCommand.contains("-r")) { //-r이 들어가면 숫자를 삼
                lotto.setRandom();
                mon.log("숫자를 넣었습니다 (" + lotto + ") -> 랜덤으로넣었습니다");
                return true;
            }

            String []tokens = currentCommand.split("[=]");

            if(tokens.length>1){
                 tokens = tokens[1].split("[,]");
                 lotto.setLottoFromScript(tokens);
                 mon.log("숫자를 넣었습니다 " + lotto + ")");
                 return true;
             }
             mon.log("잘못입력했습니다, 숫자를 넣으세요");
            return false;
        }
        if(currentCommand.startsWith("buy lotto")||currentCommand.startsWith("bl")){
            this.touch();
            mon.log("로또를 구매하셨습니다");
            Lotto lotto = new Lotto();

            int[] lotto_default_number = {54,12,54,23,65,99,22,12,13,63,98,87,65};

            if(currentCommand.contains("-r")) { //-r이 들어가면 숫자를 삼
                lotto.setRandom();
                mon.log("숫자를 넣었습니다 (" + lotto + ") -> 랜덤으로넣었습니다");
            }
            else{
                mon.log("숫자를 아직넣지않았습니다");
            }
            bag.addItem(lotto);
            mon.log("로또를 가방에 넣었습니다 [" + bag+"]");
            return true;
        }
        return false;
    }
    private City city;
    public void setCity(City city) {
        this.city=city;
    }
}
