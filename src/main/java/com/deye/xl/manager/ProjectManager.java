package com.deye.xl.manager;

import com.deye.xl.entity.Project;
import com.deye.xl.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectManager {

    @Autowired
    ProjectRepository projectRepository;

    public Project getProjectById(String id) {
        return projectRepository.getProjectById(id);
    }

    public String getProjectInfo(String id) {
        return projectRepository.getProjectInfo(id);
    }

}
