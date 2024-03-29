package in.ac.skcet.event_manager.services;

import in.ac.skcet.event_manager.models.Event;
import in.ac.skcet.event_manager.models.Student;
import in.ac.skcet.event_manager.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class StudentService {

    StudentRepository studentRepository;
    EventService eventService;

    public List<Event> getPendingEvents(String studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null)
            return new ArrayList<>();
        log.info(student.toString());
        return eventService.findAll().stream().filter(event -> !student.getEvents().contains(event)).collect(Collectors.toList());
    }


    public void updateEvent(String studentId, String eventId){

        Event event = eventService.findById(Integer.valueOf(eventId)).orElse(null);
        Student stu = studentRepository.findById(studentId).orElse(null);
        if(stu == null || event == null)
            return;

        log.info(event.toString());
        stu.addEvent(event);
        studentRepository.save(stu);
    }

    public Set<Student> findByClassCode(String classCode){
        return studentRepository.findByClassCode(classCode);
    }
}
