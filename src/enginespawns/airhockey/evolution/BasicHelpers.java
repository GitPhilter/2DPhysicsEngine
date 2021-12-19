package enginespawns.airhockey.evolution;

import enginespawns.airhockey.evaluation.EvaluationInfo;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;

import javax.swing.*;
import java.awt.*;

public final class BasicHelpers {

    public static PlayerStickDisc[] getDiscsFromEvaluationInfos(EvaluationInfo[] infos){
        PlayerStickDisc[] result = new PlayerStickDisc[infos.length];
        for(int i = 0; i < result.length; ++i){
            result[i] = infos[i].getDisc();
        }
        return result;
    }

    public static NeuralNetworkPlayerStickDisc getStandardRandomNeuralNetworkDisc(){
        return new NeuralNetworkPlayerStickDisc("RandomNeuralNetworkDisc", null, 20, Color.BLUE, null);
    }

    public static RandomMovementPlayerStickDisc getStandardRandomMovementNetworkDisc(){
        return new RandomMovementPlayerStickDisc("RandomMovementDisc", null, 20, Color.RED);
    }

    public static Integer[] getIntegerInput(String title, String[] parameters){
        JTextField[] textFields = new JTextField[parameters.length];
        Integer[] resultIntegers = new Integer[textFields.length];
        JPanel myPanel = new JPanel();
        for(int i = 0; i < textFields.length; ++i){
            textFields[i] = new JTextField(5);
            myPanel.add(textFields[i]);
            myPanel.add(new JLabel(parameters[i]));
        }
        //
        boolean validInput = false;
        while(!validInput){
            validInput = true;
            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    title, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                for(int i = 0; i < textFields.length; ++i){
                    System.out.println(parameters[i] + " -> " + textFields[i].getText());
                    try{
                        resultIntegers[i] = Integer.parseInt(textFields[i].getText());
                    } catch(Exception e){
                        System.out.println("Parameter no." + i + " is not an integer!");
                        validInput = false;
                    }
                }
            }
        }
        return resultIntegers;
    }
}
