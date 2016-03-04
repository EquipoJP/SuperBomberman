package logic.collisions;

import logic.Sprite;

public class PerspectiveBoundingBox extends BoundingBox {
	
	public static PerspectiveBoundingBox createBounginBox(Sprite s){
		if(s == null){
			return null;
		}
		
		int minX = s.getCenterX() - s.getWidth()/2;
		int minY = s.getCenterY();
		Point2D min = new Point2D(minX, minY);
		
		int maxX = s.getCenterX() + s.getWidth()/2;
		int maxY = s.getCenterY() + s.getHeight()/2;
		Point2D max = new Point2D(maxX, maxY);
		
		return new PerspectiveBoundingBox(min, max);
	}
	
	public PerspectiveBoundingBox(Point2D min, Point2D max){
		super(min, max);
	}
}
