package enginespawns.airhockey.evaluation;

public final class EvaluationInfoSort {

    public static EvaluationInfo[] sort(EvaluationInfo[] infos){
        for(int a = 1; a < infos.length - 1; ++a){
            for(int b = 0; b < infos.length - a; ++b){
                if(infos[b].getEvaluationValue() < infos[b + 1].getEvaluationValue()){
                    EvaluationInfo tempInfo = new EvaluationInfo(infos[b].getDisc(), infos[b].getEvaluationValue());
                    infos[b] = infos[b + 1];
                    infos[b + 1] = tempInfo;
                }
            }
        }
        return infos;
    }
}
