package chazen.ekanban.service.impl;

import chazen.ekanban.entity.Card;
import chazen.ekanban.entity.Sprint;
import chazen.ekanban.mapper.SprintMapper;
import chazen.ekanban.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    SprintMapper sprintMapper;

    @Override
    public List<Sprint> getSprintUnderProject(int projectId) {
        return sprintMapper.getSprintUnderProject(projectId);
    }

    @Override
    public int createSprint(Sprint sprint) {
        return sprintMapper.createSprint(sprint);
    }

    @Override
    public List<Card> getCardUnderSprint(int sprintId) {
        return sprintMapper.getCardUnderSprint(sprintId);
    }

    @Override
    public int updateSprint(Sprint sprint) {
        return sprintMapper.updateSprint(sprint);
    }
}
