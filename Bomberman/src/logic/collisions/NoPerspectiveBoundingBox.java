package logic.collisions;

import logic.Sprite;

public class NoPerspectiveBoundingBox extends BoundingBox {
	
	public static NoPerspectiveBoundingBox createBoundingBox(Sprite s){
		if(s == null){
			return null;
		}
		
		int minX = 0;
		int minY = 0;
		Point2D min = new Point2D(minX, minY);
		
		int maxX = s.getWidth();
		int maxY = s.getHeight();
		Point2D max = new Point2D(maxX, maxY);
		
		return new NoPerspectiveBoundingBox(min, max);
	}
	
	public NoPerspectiveBoundingBox(Point2D min, Point2D max){
		super(min, max);
	}
}
