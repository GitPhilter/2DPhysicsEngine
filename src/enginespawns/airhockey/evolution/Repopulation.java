package enginespawns.airhockey.evolution;

import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;

import java.awt.*;

public final class Repopulation {

    /**
     * "repopulates" an array. all the parents end up in the resulting family array.
     * each parent spawns children as determined by the given variable. if there is still
     * space left in the family array, it will be filled up with random discs.
     * @param parents the original discs, most likely the result of evolutionary processes.
     * @param familySize the desired size of the resulting family array.
     * @param numberOfChildrenPerParent the number of children each parent is ordered to spawn.
     * @return the resulting family array, the repopulated disc population, so to speak.
     */
    public static PlayerStickDisc[] repopulate(PlayerStickDisc[] parents, int familySize,
                                               int numberOfChildrenPerParent){
        if(numberOfChildrenPerParent * parents.length > familySize) {
            System.out.println("Repopulation.repopulate(): Error! too many children for the desired family size!!");
            return null;
        }
        PlayerStickDisc[] family = new PlayerStickDisc[familySize];
        // add parents & children
        int totalCounter = 0;
        int parentCounter = 0;
        while(parentCounter < parents.length){
            family[totalCounter] = parents[parentCounter];
            ++totalCounter;
            for(int c = 0; c < numberOfChildrenPerParent; ++c){
                family[totalCounter] = Evolution.getRandomChild((NeuralNetworkPlayerStickDisc) parents[parentCounter]);
                ++totalCounter;
            }
            ++parentCounter;
        }
        // fill up with random
        for(int i = totalCounter; i < family.length; ++i){
            family[i] = new NeuralNetworkPlayerStickDisc("RandomNeuralDisc_" + i, null, 20, Color.BLUE, null);
        }
        return family;
    }
}
