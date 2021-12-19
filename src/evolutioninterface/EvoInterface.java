package evolutioninterface;

import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.evolution.BasicHelpers;
import enginespawns.airhockey.evolution.EvolutionFromOneParent;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.RandomMovementPlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;
import neuralnetwork.NeuralNetworkChooser;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvoInterface extends JFrame implements ActionListener{
    private EvoPanel panel;
    int width = 800;
    int height = 600;
    //
    JButton confirmButton;
    JComboBox<String> selectBox;
    // initial option strings
    String evoFrom1 = "Evolution from 1 Parent";
    String evoFromRnd = "Evolution from random pool";
    public EvoInterface(){
        super();
        //
        panel = new EvoPanel(width, height);
        add(this.panel);
        // set choice box
        String[] choices = { evoFrom1, evoFromRnd};
        selectBox = new JComboBox<String>(choices);
        selectBox.setVisible(true);
        panel.add(selectBox);
        // some button
        confirmButton = new JButton("Let's go!");
        confirmButton.setVisible(true);
        confirmButton.addActionListener(this);
        add(confirmButton);
        panel.add(confirmButton);
        //
        setTitle("Evolution Interface");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirmButton){
            System.out.println("Button geklickt!");
            String choice = (String)selectBox.getSelectedItem();
            System.out.println("Choice: " + choice);
            if(choice != null && choice.equals(evoFrom1)){
                String infoText = "Choose parent for spawning!";
                evoFromOneChoice(infoText, "Choose wisely");
            } else if(choice != null && choice.equals(evoFromRnd)){
                System.out.println("'Evolution from random pool' chosen.");
            }
        }
    }

    public void evoFromOneChoice(String infoMessage, String titleBar) {
        int input = JOptionPane.showOptionDialog(null, infoMessage,titleBar,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Use saved parent", "Create new parent"}, "B");
        System.out.println("input: " + input);
        NeuralNetworkPlayerStickDisc disc = null;
        if(input == 0){
            System.out.println("'Use saved parent' chosen.");
            disc = NeuralNetworkChooser.getDiscFromFile();
            System.out.println("Disc loaded: " + disc.getName());
        } else if(input == 1){
            System.out.println("'Create new parent' chosen.");
            disc = BasicHelpers.getStandardRandomNeuralNetworkDisc();
        }
        startEvoFromOne(disc);
    }

    public void startEvoFromOne(NeuralNetworkPlayerStickDisc disc){
        String[] parameterStrings = new String[2];
        parameterStrings[0] = "Number of cycles:";
        parameterStrings[1] = "Number of children:";
        Integer[] parameters = BasicHelpers.getIntegerInput("enter parameters", parameterStrings);
        NeuralNetworkPlayerStickDisc bestDisc = EvolutionFromOneParent.simpleCycle(disc, parameters[0], parameters[1]);
        System.out.println("bestDisc: " + bestDisc.getName());
        // show new best disc
        showcaseDisc(bestDisc);
    }

    public void showcaseDisc(NeuralNetworkPlayerStickDisc disc){
        new Thread(() -> {
            System.out.println("Thread Running");
            disc.setTeamEnum(TeamEnum.HOME);
            disc.setPosition(new Position(100,200));
            RandomMovementPlayerStickDisc disc_2 = BasicHelpers.getStandardRandomMovementNetworkDisc();
            disc_2.setTeamEnum(TeamEnum.AWAY);
            disc_2.setPosition(new Position(700,200));
            AirHockey airHockey = new AirHockey(disc, disc_2, true);
            disc.setEngine(airHockey);
            airHockey.playSingleMatch();
        }).start();
    }


}
