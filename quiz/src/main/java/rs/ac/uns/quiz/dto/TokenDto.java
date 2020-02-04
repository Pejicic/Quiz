package rs.ac.uns.quiz.dto;

public class TokenDto {

    private String token;
    private String role;

    public TokenDto() {
    }

    public TokenDto(String token, String role) {
        this();
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
