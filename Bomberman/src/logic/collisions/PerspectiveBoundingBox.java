package logic.collisions;

import logic.Sprite;

public class PerspectiveBoundingBox extends BoundingBox {
	
	public static PerspectiveBoundingBox createBoundingBox(Sprite s){
		if(s == null){
			return null;
		}
		
		int minX = -s.getWidth()/2 + s.getWidth() / 8;
		int minY = s.getHeight() / 10;
		Point2D min = new Point2D(minX, minY);
		
		int maxX = s.getWidth()/2 - s.getWidth() / 8;
		int maxY = s.getHeight()/2 - s.getHeight() / 10;
		Point2D max = new Point2D(maxX, maxY);
		
		return new PerspectiveBoundingBox(min, max);
	}
	
	public PerspectiveBoundingBox(Point2D min, Point2D max){
		super(min, max);
	}
}
