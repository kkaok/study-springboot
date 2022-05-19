package eblo.study.springboot.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@Builder
@NoArgsConstructor
public class User {

    private String id;
    private String name;
    private Integer age;

    public User(String id, String name, Integer age) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private Integer age;
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder age(Integer age) {
            this.age = age;
            return this;
        }
        public User build() {
            return new User(id, name, age);
        }
    }
    
    public static void main(String[] args) {
        User user = User.builder().id("id").name("테스트").age(22).build();
        log.debug(user.toString());
    }

}
