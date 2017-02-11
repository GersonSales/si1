package br.com.project.repository;

import br.com.project.model.task.RealTask;
import br.com.project.model.task.TaskBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gersonsales on 06/02/17.
 */
@Repository
public interface TaskBankRepository extends JpaRepository<TaskBank, Long>{
    public TaskBank findByName(String bankName);

    void save(RealTask realTask);
}
