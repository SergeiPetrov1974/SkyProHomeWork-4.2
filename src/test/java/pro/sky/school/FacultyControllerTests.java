package pro.sky.school;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.school.controller.FacultyController;
import pro.sky.school.model.Faculty;
import pro.sky.school.repository.FacultyRepository;
import pro.sky.school.service.AvatarService;
import pro.sky.school.service.FacultyService;
import pro.sky.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @MockBean
    private AvatarService avatarService;
    @MockBean
    private StudentService studentService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void saveFacultyTest() throws Exception {
        Long id = 1L;
        String color = "RED";
        String name = "PEPPER";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of((faculty)));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
}

