package ge.okoridze.battleshipgame.dto;

import ge.okoridze.battleshipgame.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserDTO {

        private Long id;

        @NotEmpty(message = "Please enter valid name.")
        private String name;

        @NotEmpty(message = "Please enter valid email.")
        @Email
        private String email;

        @NotEmpty(message = "Please enter valid password.")
        private String password;
        public UserDTO() {
        }

        public UserDTO(User user) {
                this.id = user.getId();
                this.name = user.getName();
                this.email = user.getEmail();
        }

        public UserDTO(Long id, String name, String email, String password) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.password = password;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}
