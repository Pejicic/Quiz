package rs.ac.uns.quiz.dto;

public class ScoreDto {

    String username;

    double points;

    int bonus;

    double pointsPlusBonus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public double getPointsPlusBonus() {
        return pointsPlusBonus;
    }

    public void setPointsPlusBonus(double pointsPlusBonus) {
        this.pointsPlusBonus = pointsPlusBonus;
    }
}
