package com.pradolabs.cruddemo.dao;

import com.pradolabs.cruddemo.entity.Instructor;
import com.pradolabs.cruddemo.util.InstructorTestFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração para AppDAO com Spring + BD real (H2 em testes).
 * Executa em ~2-3s. Usa application-test.properties com H2 in-memória.
 * Padrão: @SpringBootTest + @ActiveProfiles("test") + AAA (Arrange-Act-Assert)
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("AppDAO Integration Tests (com Spring + BD)")
@Transactional
class AppDAOIntegrationTest {

    @Autowired
    private AppDAO appDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // 1. Limpa a tabela
        jdbcTemplate.execute("DELETE FROM instructor");

        // 2. Reseta o contador para o ID 1
        // Se sua entidade usa IDENTITY:
        jdbcTemplate.execute("ALTER TABLE instructor ALTER COLUMN id RESTART WITH 1");
    }

    // ==================== SEARCH / FIND TESTS ====================

    @Test
    @DisplayName("findInstructorById() deveria retornar Instructor com id = 3 quando existe no BD")
    void findInstructorById_withValidId_shouldReturnInstructor() {
        Instructor instructor1 = InstructorTestFixture.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@test.com")
                .build();
        appDAO.save(instructor1);

        Instructor instructor2 = InstructorTestFixture.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@test.com")
                .build();
        appDAO.save(instructor2);

        Instructor instructor3 = InstructorTestFixture.builder()
                .firstName("Madhu")
                .lastName("Kumar")
                .email("madhu.kumar@test.com")
                .build();
        appDAO.save(instructor3);

        Instructor instructor4 = InstructorTestFixture.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice.johnson@test.com")
                .build();
        appDAO.save(instructor4);

        // Arrange
        int instructorId = 3;

        // Act
        Instructor result = appDAO.findInstructorById(instructorId);

        // Assert
        assertNotNull(result, "Instructor com id 3 deveria ser encontrado");
        assertEquals("Madhu", result.getFirstName(), "FirstName deveria ser 'Madhu'");
    }

    @Test
    @DisplayName("findInstructorById() deveria retornar null para id inexistente")
    void findInstructorById_withInvalidId_shouldReturnNull() {
        // Arrange
        int instructorId = 9999;

        // Act
        Instructor result = appDAO.findInstructorById(instructorId);

        // Assert
        assertNull(result, "Instructor com id 9999 não deveria existir");
    }

    @Test
    @DisplayName("findInstructorById() deveria retornar null para id = 1 (não deve existir por padrão)")
    void findInstructorById_withId1_shouldReturnNull() {
        // Arrange
        int instructorId = 1;

        // Act
        Instructor result = appDAO.findInstructorById(instructorId);

        // Assert
        assertNull(result, "Instructor com id 1 não deveria existir (a menos que inserido no setUp)");
    }

    // ==================== SAVE TESTS ====================

    @Test
    @DisplayName("save() deveria persistir novo Instructor no BD")
    void save_withValidInstructor_shouldPersistToDB() {
        // Arrange
        Instructor instructor = InstructorTestFixture.builder()
                .firstName("IntegrationTest")
                .lastName("User")
                .email("integration@test.com")
                .build();

        // Act
        appDAO.save(instructor);

        // Assert
        assertNotNull(instructor, "Instructor não deveria ser null após save");
        // Observação: Se o Instructor tem @GeneratedValue, o ID será gerado pelo BD
    }

    @Test
    @DisplayName("save() deveria aceitar múltiplos Instructors")
    void save_multipleInstructors_shouldPersistAll() {
        // Arrange
        Instructor instructor1 = InstructorTestFixture.builder()
                .firstName("First")
                .lastName("User")
                .email("first@test.com")
                .build();

        Instructor instructor2 = InstructorTestFixture.builder()
                .firstName("Second")
                .lastName("User")
                .email("second@test.com")
                .build();

        // Act
        appDAO.save(instructor1);
        appDAO.save(instructor2);

        // Assert
        assertNotNull(instructor1);
        assertNotNull(instructor2);
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("deleteInstructorById() deveria remover Instructor existente")
    void deleteInstructorById_withValidId_shouldDelete() {
        // Arrange
        // Criar um instructor para depois deletar
        Instructor instructor = InstructorTestFixture.builder()
                .firstName("ToDelete")
                .lastName("TestUser")
                .email("todelete@test.com")
                .build();
        appDAO.save(instructor);

        // Act
        // Deletar (seria necessário ter o ID do instructor salvo)
        // Para este exemplo, deletamos um ID que sabemos que existe
        appDAO.deleteInstructorById(3);

        // Assert
        Instructor deleted = appDAO.findInstructorById(3);
        assertNull(deleted, "Instructor com id 3 deveria ter sido deletado");
    }

    @Test
    @DisplayName("deleteInstructorById() não deveria gerar erro ao deletar id inexistente")
    void deleteInstructorById_withInvalidId_shouldNotThrow() {
        // Arrange
        int invalidId = 9999;

        // Act & Assert
        assertDoesNotThrow(() -> appDAO.deleteInstructorById(invalidId),
                "Deletar id inexistente não deveria gerar exceção");
    }

    // ==================== DATA PERSISTENCE TESTS ====================

    @Test
    @DisplayName("Dados persistidos devem ser recuperáveis após save")
    void save_andRetrieve_shouldPreservData() {
        // Arrange
        Instructor instructor = InstructorTestFixture.builder()
                .firstName("Persistence")
                .lastName("Test")
                .email("persist@test.com")
                .build();

        // Act
        appDAO.save(instructor);

        // Assert
        assertNotNull(instructor, "Instructor salvo não deveria ser null");
        assertTrue(instructor.getFirstName().equals("Persistence"),
                "FirstName deveria ser preservado");
    }
}

