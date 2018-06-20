package model;

public class Ship {

	private int postID;
	private String Content;
	private int userID;
	private String datePosted;
	
	public int getPostID() {
		return postID;
	}
	
	public void setPostID(int postID) {
		 this.postID = postID;
	}

	/*public void setHullId(String hullId) {
		this.hullId = hullId;
	}*/

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID)
	{
		this.userID = userID;
	}
	public String getdatePosted() {
		return datePosted;
	}

	public void setdatePosted(String datePosted) {
		this.datePosted = datePosted;
	}

	/*public int getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}*/
}