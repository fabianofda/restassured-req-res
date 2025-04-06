package com.example.factories;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON serialization
public class UserBuilder {
    private String name;
    private String job;
    private String email;
    private String password;

    // Private constructor to enforce the use of the Builder
    private UserBuilder(Builder builder) {
        this.name = builder.name;
        this.job = builder.job;
        this.email = builder.email;
        this.password = builder.password;
    }

    // Getters only (no setters to preserve immutability when using the builder)
    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Builder Pattern
    public static class Builder {
        private String name;
        private String job;
        private String email;
        private String password;

        public Builder() {}

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withJob(String job) {
            this.job = job;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder build() {
            return new UserBuilder(this);
        }
    }
}
