package com.mowitnow;

import com.mowitnow.objects.Field;
import com.mowitnow.objects.Mower;
import com.mowitnow.data.ActionEnum;
import com.mowitnow.util.FileReader;
import com.mowitnow.data.OrientationEnum;

import java.util.ArrayList;
import java.util.List;

public class MowItNowApp {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        String filename = "mower_2.txt";
        List<String> lines = fileReader.loadFile(filename);

        // Field creation
        String[] fieldSize = lines.get(0).split(" ");
        Field field = new Field(Integer.parseInt(fieldSize[0]), Integer.parseInt(fieldSize[1]));

        // Mower creation
        String[] mowerPosition = lines.get(1).split(" ");
        List<ActionEnum> actions = parse(lines.get(2));
        Mower mower = new Mower(Integer.parseInt(mowerPosition[0]),
                Integer.parseInt(mowerPosition[1]),
                OrientationEnum.parse(mowerPosition[2]));

        MowerAction mowerAction = new MowerAction(mower, field);

        for(ActionEnum action : actions) {
            System.out.println(mower);
            mowerAction.analyseAction(action);
        }

        System.out.println(mower);
    }


    public static List<ActionEnum> parse(String linePosition) {
        char[] positions = linePosition.toCharArray();
        List<ActionEnum> positionsEnum = new ArrayList<>();
        ActionEnum action;
        for (char position : positions) {
            action = ActionEnum.parse(position);
            if (action == null) {
                // throw error
            }
            positionsEnum.add(action);
        }
        return positionsEnum;
    }

}
