public interface Actor {
	public void setProcess(int i, Actor process);

	public void receive(int sender, int round, int vote);

	public boolean isHalted();

	public void broadcast();

	public void coordinate(int coin);

	public boolean hasDecided();

	public int getDecision();
}
