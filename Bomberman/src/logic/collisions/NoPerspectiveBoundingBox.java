package logic.collisions;

import logic.Sprite;

public class NoPerspectiveBoundingBox extends BoundingBox {
	
	public static NoPerspectiveBoundingBox createBoundingBox(Sprite s){
		if(s == null){
			return null;
		}
		
		int minX = -s.getWidth()/2;
		int minY = -s.getHeight()/2;
		Point2D min = new Point2D(minX, minY);
		
		int maxX = s.getWidth()/2;
		int maxY = s.getHeight()/2;
		Point2D max = new Point2D(maxX, maxY);
		
		return new NoPerspectiveBoundingBox(min, max);
	}
	
	public NoPerspectiveBoundingBox(Point2D min, Point2D max){
		super(min, max);
	}
}
