package br.com.project.model.task;

/**
 * Created by gersonsales on 04/02/17.
 */
public enum Priority {
    NONE, LOW, MIDDLE, TOP;

    @Override
    public String toString() {
        switch (this) {
            case NONE: return "No Priority";
            case LOW: return "Low";
            case MIDDLE: return "Middle";
            case TOP: return "Top";
        }

        return super.toString();

    }
}
