package io.riddles.golad.game.move;

import java.awt.Point;
import java.util.ArrayList;

import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.javainterface.serialize.Deserializer;

/**
 * io.riddles.golad.game.move.GoladMoveDeserializer - Created on 21-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladMoveDeserializer implements Deserializer<GoladMove> {

    @Override
    public GoladMove traverse(String string) {
        try {
            return visitMove(string);
        } catch (InvalidInputException ex) {
            return new GoladMove(ex);
        } catch (Exception ex) {
            return new GoladMove(new InvalidInputException("Failed to parse move"));
        }
    }

    private GoladMove visitMove(String input) throws InvalidInputException {
        String[] split = input.split(" ");
        MoveType moveType = MoveType.fromString(split[0]);

        switch (moveType) {
            case KILL:
                if (split.length != 2) {
                    throw new InvalidInputException("Kill move doesn't split into 2 parts");
                }
                return visitKillMove(split[1]);
            case BIRTH:
                if (split.length != 4) {
                    throw new InvalidInputException("Birth move doesn't split into 4 parts");
                }
                return visitBirthMove(split[1], split[2], split[3]);
            default:
                throw new InvalidInputException(
                        String.format("Move type %s not recognized", split[0]));
        }
    }

    private GoladMove visitKillMove(String coordinate) throws InvalidInputException {
        return new GoladKillMove(visitCoordinate(coordinate));
    }

    private GoladMove visitBirthMove(
            String coordinate,
            String sacrificeCoordinate1,
            String sacrificeCoordinate2) throws InvalidInputException {
        ArrayList<Point> sacrificeCoordinates = new ArrayList<>();
        sacrificeCoordinates.add(visitCoordinate(sacrificeCoordinate1));
        sacrificeCoordinates.add(visitCoordinate(sacrificeCoordinate2));

        return new GoladBirthMove(visitCoordinate(coordinate), sacrificeCoordinates);
    }

    private Point visitCoordinate(String coordinate) throws InvalidInputException {
        try {
            String[] split = coordinate.split(",");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);

            return new Point(x, y);
        } catch (Exception ex) {
            throw new InvalidInputException(
                    String.format("Failed to parse coordinate %s", coordinate));
        }
    }
}
