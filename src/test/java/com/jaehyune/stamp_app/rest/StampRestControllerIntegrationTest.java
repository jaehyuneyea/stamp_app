package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.service.StampService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * An integration test for StampRestController.
 * TODO: Add more exception testing
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// RESETS THE DB ON EVERY TEST METHOD
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestExecutionListeners({DirtiesContextTestExecutionListener.class})
class StampRestControllerIntegrationTest {

    // Since we are doing integration tests, we are using actual layer components instead of mocks.
    private StampService stampService;

    private StampDTO stampDTO;

    @Autowired
    public StampRestControllerIntegrationTest(StampService stampService) {
        this.stampService = stampService;
    }

    @BeforeEach
    public void setup() {
        stampDTO = new StampDTO();
        stampDTO.setId(0);
        stampDTO.setDescription("test railway");
        stampDTO.setRailway("test lines");
        stampDTO.setRating(4);
        List<CommentReadDTO> listDTO = new ArrayList<>();
        stampDTO.setComments(listDTO);
        stampDTO.setPhoto(null);
    }

    @Test
    @Order(1)
    void add_stamp_and_then_find_stamp_by_id() {
        Stamp stamp = stampService.save(stampDTO);

        Assertions.assertEquals(1, stamp.getId());
        Assertions.assertEquals(new ArrayList<>(), stamp.getComments());

        StampDTO dto_from_db = stampService.findById(stamp.getId());
        Assertions.assertEquals(stampDTO.getDescription(), dto_from_db.getDescription());
        Assertions.assertEquals(stampDTO.getComments(), dto_from_db.getComments());
        Assertions.assertEquals(stampDTO.getRailway(), dto_from_db.getRailway());
        Assertions.assertEquals(stampDTO.getRating(), dto_from_db.getRating());
        Assertions.assertEquals(stampDTO.getPhoto(), dto_from_db.getPhoto());
    }

    @Test
    @Order(2)
    void save_stamp_and_then_update() {
        Stamp stamp = stampService.save(stampDTO);
        Assertions.assertEquals(1, stamp.getId());

        StampDTO dto_from_db = stampService.findById(stamp.getId());
        Assertions.assertEquals(stampDTO.getDescription(), dto_from_db.getDescription());
        Assertions.assertEquals(stampDTO.getComments(), dto_from_db.getComments());
        Assertions.assertEquals(stampDTO.getRailway(), dto_from_db.getRailway());
        Assertions.assertEquals(stampDTO.getRating(), dto_from_db.getRating());
        Assertions.assertEquals(stampDTO.getPhoto(), dto_from_db.getPhoto());

        StampDTO stampDTO = new StampDTO();
        stampDTO.setId(2);
        stampDTO.setDescription("updated railway");
        stampDTO.setRailway("test lines");
        stampDTO.setRating(4);
        List<CommentReadDTO> listDTO = new ArrayList<>();
        stampDTO.setComments(listDTO);
        stampDTO.setPhoto(null);

        stampService.save(stampDTO);

        StampDTO updated_dto_from_db = stampService.findById(2);
        Assertions.assertEquals("updated railway", updated_dto_from_db.getDescription());
        Assertions.assertNotEquals("updated railway", dto_from_db.getDescription());
    }

    @Test
    @Order(3)
    void add_three_stamps_then_findAll() {
        Stamp stamp1 = stampService.save(stampDTO);
        Stamp stamp2 = stampService.save(stampDTO);
        Stamp stamp3 = stampService.save(stampDTO);
        Assertions.assertEquals(1, stamp1.getId());
        Assertions.assertEquals(2, stamp2.getId());
        Assertions.assertEquals(3, stamp3.getId());

        List<StampDTO> dtos = stampService.findAll();
        Assertions.assertEquals(3, dtos.size());
        for(StampDTO dto: dtos) {
            Assertions.assertEquals(stampDTO.getDescription(), dto.getDescription());
            Assertions.assertEquals(stampDTO.getComments(), dto.getComments());
            Assertions.assertEquals(stampDTO.getRailway(), dto.getRailway());
            Assertions.assertEquals(stampDTO.getRating(), dto.getRating());
            Assertions.assertEquals(stampDTO.getPhoto(), dto.getPhoto());
        }
    }

    @Test
    void save_stamp_and_then_delete() {
        Stamp stamp = stampService.save(stampDTO);
        Assertions.assertEquals(1, stamp.getId());

        stampService.delete(1);
        Assertions.assertThrows(RuntimeException.class, () -> stampService.findById(1));
    }
}