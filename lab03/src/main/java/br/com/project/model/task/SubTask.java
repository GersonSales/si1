package br.com.project.model.task;

import javax.persistence.Entity;

/**
 * Created by gersonsales on 04/02/17.
 */
@Entity
public class SubTask extends Task{
    public SubTask(String title) {
        super(title);
    }

    public SubTask() {
    }

    @Override
    public String toString() {
        return "";
    }
}
