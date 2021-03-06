package com.mowitnow.service;

import com.mowitnow.data.ActionEnum;
import com.mowitnow.exception.MowItNowException;
import com.mowitnow.objects.Field;
import com.mowitnow.objects.Mower;
import com.mowitnow.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MowerLauncher {
    private static final Logger LOG = LogManager.getLogger(MowerLauncher.class);
    private static final String SPACE = " ";
    private Field field;

    private FileReader fileReader = new FileReader();

    /**
     * Core function : initialize the field creation and read through the lines of the file.
     * @param filename the file
     * @throws MowItNowException
     */
    public void launch(String filename) throws MowItNowException {
        List<String> lines = fileReader.loadFile(filename);
        fieldInitialization(lines.get(0));
        LOG.info("Field max coordinates : {}", field);
        int cpt = 1;

        for (int i = 1; i < lines.size(); i = i + 2) {
            // Mower creation
            try {
                LOG.info("==========================");
                LOG.info("Mower {} launched", cpt);
                mowerInitialization(lines.get(i), lines.get(i + 1));
            } catch (MowItNowException e) {
                LOG.error("Interruption of mower movements, skipping to next one", e);
            } finally {
                cpt++;
            }
        }
    }

    /**
     * Initialize the mower with the mower information
     * @param mowerInformation the line with the mower information
     * @param lineAction the line with the actions
     * @throws MowItNowException
     */
    protected void mowerInitialization(String mowerInformation, String lineAction) throws MowItNowException {
        String[] mowerPosition = mowerInformation.split(SPACE);
        if (mowerPosition.length != 3) {
            throw new MowItNowException("Too much or too little input for mower initialization");
        }

        List<ActionEnum> actions = parse(lineAction);

        if (!field.isInFieldLength(Integer.parseInt(mowerPosition[0])) || !field.isInFieldWidth(Integer.parseInt(mowerPosition[1]))) {
            throw new MowItNowException("Mower cannot be placed in the field : incorrect coordinates");
        }

        Mower mower = new Mower(mowerPosition[0], mowerPosition[1], mowerPosition[2]);

        MowerAction mowerAction = new MowerAction(mower, field);
        mowerAction.launchMower(actions);
    }

    /**
     * Initialize the field with the line that has the field max coordinates
     * @param fieldMaxCoord max coordinates of the field
     * @throws MowItNowException
     */
    protected void fieldInitialization(String fieldMaxCoord) throws MowItNowException {
        // Field creation
        String[] fieldSize = fieldMaxCoord.split(SPACE);
        if (fieldSize.length != 2) {
            throw new MowItNowException("Too much or too little input for field dimensions");
        }

        field = new Field(Integer.parseInt(fieldSize[0]), Integer.parseInt(fieldSize[1]));
    }

    /**
     * Parse the line that has the actions needed by the mower
     * @param lineAction the line with actions
     * @return the list of the actions
     * @throws MowItNowException
     */
    protected List<ActionEnum> parse(String lineAction) throws MowItNowException {
        char[] actions = lineAction.toCharArray();
        List<ActionEnum> positionsEnum = new ArrayList<>();
        ActionEnum action;
        for (char c : actions) {
            action = ActionEnum.parse(c);
            positionsEnum.add(action);
        }
        return positionsEnum;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public FileReader getFileReader() {
        return fileReader;
    }
}
