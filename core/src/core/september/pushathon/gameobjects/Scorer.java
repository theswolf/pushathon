package core.september.pushathon.gameobjects;

public class Scorer extends Counter{

	public int score;
	
	public Scorer(float x, float y, float width, float height, int posDivider) {
		super(x, y, width, height, posDivider);
	}
	
	public void update (float deltaTime) {
		chooseSelected(score / posDivider % 10);
	}
}
