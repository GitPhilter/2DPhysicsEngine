package main;

import enginespawns.airhockey.AirHockey;
import logger.Logger;

public class AirHockeyMain {

    public static void main(String[] args){
        Logger.print("AirHockey.");
        AirHockey airHockey = new AirHockey();
        airHockey.start(17);
    }
}
