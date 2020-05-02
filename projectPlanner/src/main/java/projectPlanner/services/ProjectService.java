package projectPlanner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectPlanner.domin.Project;
import projectPlanner.exceptions.ProjectIdException;
import projectPlanner.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    //Save and update project
    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }
    //Find project by projectIdentifier
    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
        }
        return project;
    }
    //Find all project
    public Iterable<Project> findAllProject(){
        return projectRepository.findAll();
    }
    //Delete project
    public void deleteProjectByIdentifier(String projectid) {
        Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Cannot Project with ID '" + projectid + "'. This project does not exist");
        }
        projectRepository.delete(project);
    }

}
