package br.com.project.model.task;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gersonsales on 04/02/17.
 */
@Entity
public abstract class Task implements Comparable<Task>{
    @Id
    @GeneratedValue(generator="STORE_SEQ")
    @SequenceGenerator(name="STORE_SEQ",sequenceName="STORE_SEQ", allocationSize=1)
    private Long id;
    private String title;
    private boolean checked;
    private Date creationDate;
    private Date checkDate;

    public Task(String title) {
        this();
        this.title = title;
    }

    public Task() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.creationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checkDate = new Date();
        this.checked = checked;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public int compareTo(Task otherTask) {
        return title.compareTo(otherTask.getTitle());
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", checked=" + checked +
                '}';
    }

}
