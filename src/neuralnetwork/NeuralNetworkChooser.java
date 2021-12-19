package neuralnetwork;

import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;

import javax.swing.*;
import java.awt.*;

public final class NeuralNetworkChooser {

    public static NeuralNetwork getNeuralNetworkFromFile(){
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser("res/bestneuralnetworks/");
        // Dialog zum Oeffnen von Dateien anzeigen
        int choice = chooser.showOpenDialog(null);
        // Abfrage, ob auf "Öffnen" geklickt wurde
        if(choice == JFileChooser.APPROVE_OPTION){
            System.out.println("Chosen file: " + chooser.getSelectedFile().getName());
            NeuralNetwork network = NeuralNetworkLoader.loadFile("res/bestneuralnetworks/"+ chooser.getSelectedFile().getName());
            network.setName(chooser.getSelectedFile().getName());
        }
        return null;
    }

    public static NeuralNetworkPlayerStickDisc getDiscFromFile(){
        NeuralNetwork network = getNeuralNetworkFromFile();
        NeuralNetworkPlayerStickDisc disc = new NeuralNetworkPlayerStickDisc(network.getName()+ "-Disc",
                null, 20, Color.BLUE, null , network);
        return disc;
    }
}
