package model;

public abstract class AbstractInvokeAPIServiceResult {
	protected String nextToken;
	public String getNextToken() {
		return nextToken;
	}
	public void setNextToken(String nextToken) {
		this.nextToken = nextToken;
	}
}
