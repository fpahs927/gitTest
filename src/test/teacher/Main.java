package test.teacher;

public class Main {
    public static void main(String[] args) {
        User user = new User("nami",3000);

        //user가 interface에서 monitor을 쓰는걸 알리기 위한 소스코드
        UserInterface userInterface = new UserInterface();
        SimpleMonitor mon = userInterface.getMonitor();

        user.initialize(mon); //시작됐다
        userInterface.begin();;
        String currentCommand;

        LottoCenter lottoCenter = new LottoCenter();
        lottoCenter.open();

        City city = new City();
        city.addBuilding(lottoCenter);
        user.setCity(city); //어디 사냐

        while(user.onPlay(mon)){
            currentCommand=userInterface.waitFor();
            currentCommand=currentCommand.trim();
            if(currentCommand.equals("#q"))break;
            if(currentCommand.equals("#lc")) userInterface.getMonitor().log(lottoCenter.toString());
            if(user.onCommand(mon, currentCommand)){
                continue;
            }
            if(currentCommand.equals("#lc.mr"))
                lottoCenter.makeReport(userInterface.getMonitor(),true);

            userInterface.getMonitor().log("아무일도하지않았습니다");
        }
        city.clearAll();
        lottoCenter.close();
        user.dispose(mon); //죽었다
        userInterface.end();
    }
}