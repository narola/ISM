package com.ism.teacher.multiautocomplete;

public class ChipsItem {

	private String title;
	private int imageid;
	public ChipsItem(){
		
	}
	public ChipsItem(String title,int imageId){
		this.title = title;
		this.imageid = imageId;
	}public ChipsItem(String title){
		this.title = title;

	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImageid() {
		return imageid;
	}
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}
	
	@Override
	public String toString() {
		return getTitle();
	}
}
