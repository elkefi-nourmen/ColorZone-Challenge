public class player {
    private String name;
    private int score;

    public player(String name,int score)
    {
        this.name=name;
        this.score=score;
    }

    public String getName()
    {return this.name;}
    public void setScore(int score)
    { this.score=score;}
    public int getScore()
    { return this.score;}
}
