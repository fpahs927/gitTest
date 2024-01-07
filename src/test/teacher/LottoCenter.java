package test.teacher;

import java.util.ArrayList;

//currentCommand = exchange money 금액정산하고, 로또살 때 돈 드는것, 그리고 npc까지

public class LottoCenter implements Building {
    private ArrayList<Lotto> currentRegistedLottoList = new ArrayList<>(0);

    public void registAllLottos(Lotto... lottos) { //한개도 받을 수 있고 여러개도 받을 수 있다
        long registerdTime = System.currentTimeMillis();
        for (Lotto l : lottos) {
            if (!l.isCompleted())
                continue;
            currentRegistedLottoList.add(l);

            l.setRegistratedTime(registerdTime);
        }
    }

    public void open() {
    }

    public void close() {
    }

    @Override
    public boolean checkAddress(String address) {
        if (address.equals("lottocenter"))
            return true;
        return false;
    }

    @Override
    public <T extends Building> T toResult() {
        return (T) this;
    }

    @Override
    public String toString() {
        return "LottoCenter[" + currentRegistedLottoList.size() + " item(s) registerd]";
    }

    private class ReportLotto {
        private Lotto currentWinnerLotto;
        private int bonusNumber;

        public void make(SimpleMonitor monitor) {
            int tempBonusNumber;
            Lotto newLotto = new Lotto();
            newLotto.setRandom(monitor);

            do {
                tempBonusNumber = LottoCenter.makeLottoNumber();
            } while (newLotto.checkNumbercontains(tempBonusNumber));
            monitor.log("보너스 번호는->" +tempBonusNumber);
            bonusNumber = tempBonusNumber;
            currentWinnerLotto = newLotto;

        }
        @Override
        public String toString(){
            return currentWinnerLotto + "/보너스" + bonusNumber;
        }

        public void commitReport(ArrayList<Lotto> currentRegistedLottoList) {
            Result tempResult;
            for(Lotto l : currentRegistedLottoList){
                 checkWinner(l,currentWinnerLotto, bonusNumber);
            }
            //TODO 결과처리 ->상금 계산

        }

        private Result checkWinner(Lotto current, Lotto winner, int bonusNumber) {
            return current.checkWinner(winner, bonusNumber);

        }
    }
    static public class Result{
        private int winnerGrade;

        public static Result Empty = new Result(-1, null);
        public static Result Fails = new Result(-2, null);

        public Result(int winnerGrade, Lotto lotto) {
            this.winnerGrade=winnerGrade;
        }

        public boolean isEmpty() {
            return winnerGrade ==-1;
        }

        @Override
        public String toString() {
            if(winnerGrade==-2)
                return "쓰레기";
            return winnerGrade + "등 당첨";
        }
    }

    static public int makeLottoNumber() {
        return (int) (Math.random() * 45 + 1);
    }

    private ReportLotto currentReportLotto =null;

    public void makeReport(SimpleMonitor monitor, boolean autoCommit) {
        ReportLotto newReportLotto = new ReportLotto();
        newReportLotto.make(monitor);
        currentReportLotto = newReportLotto;
        monitor.log(currentReportLotto.toString());
        if(autoCommit) {
            commitReport();
        }
    }

    public void commitReport() {
        if(currentReportLotto !=null){
            currentReportLotto.commitReport(currentRegistedLottoList);
            currentRegistedLottoList.clear();
        }
    }

}
