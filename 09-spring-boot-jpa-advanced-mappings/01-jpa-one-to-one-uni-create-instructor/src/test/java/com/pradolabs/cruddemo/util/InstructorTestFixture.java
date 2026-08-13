package com.pradolabs.cruddemo.util;

import com.pradolabs.cruddemo.entity.Instructor;

/**
 * Test fixture para criar objetos Instructor reutilizáveis em testes.
 * Padrão Builder para melhor legibilidade e flexibilidade.
 */
public class InstructorTestFixture {

    /**
     * Cria um Instructor com valores padrão.
     */
    public static Instructor createDefaultInstructor() {
        return new Instructor("John", "Doe", "john@example.com");
    }

    /**
     * Cria um Instructor com valores específicos.
     */
    public static Instructor createInstructor(String firstName, String lastName, String email) {
        return new Instructor(firstName, lastName, email);
    }

    /**
     * Retorna um builder para criar Instructor com valores customizados.
     * Exemplo: InstructorTestFixture.builder().firstName("Alice").email("alice@test.com").build()
     */
    public static InstructorBuilder builder() {
        return new InstructorBuilder();
    }

    /**
     * Builder pattern para construção flexível de Instructor.
     */
    public static class InstructorBuilder {
        private String firstName = "DefaultFirst";
        private String lastName = "DefaultLast";
        private String email = "default@example.com";

        public InstructorBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public InstructorBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public InstructorBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Instructor build() {
            return new Instructor(firstName, lastName, email);
        }
    }
}

