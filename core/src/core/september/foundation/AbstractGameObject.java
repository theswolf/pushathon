/*******************************************************************************
 * Copyright 2013 Andreas Oehlke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package core.september.foundation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {

	public Vector2 position;
	public Vector2 dimension;
	//public Vector2 origin;
//	public Vector2 scale;
//	public float rotation;
//	public Vector2 velocity;
//	public Vector2 terminalVelocity;
//	public Vector2 friction;
//	public Vector2 acceleration;
	public Rectangle bounds,scaleFactored;

//	public AbstractGameObject () {
//		position = new Vector2();
//		dimension = new Vector2(1, 1);
//		origin = new Vector2();
//		scale = new Vector2(1, 1);
//		rotation = 0;
//		velocity = new Vector2();
//		terminalVelocity = new Vector2(1, 1);
//		friction = new Vector2();
//		acceleration = new Vector2();
//		bounds = new Rectangle();
//	}
	
	public AbstractGameObject (float x, float y, float width, float height) {
		position = new Vector2(x,y);
		dimension = new Vector2(width, height);
		//origin = new Vector2();
//		scale = new Vector2(1, 1);
//		rotation = 0;
//		velocity = new Vector2();
		//terminalVelocity = new Vector2(1, 1);
		//friction = new Vector2();
		//acceleration = new Vector2();
		bounds = new Rectangle();
	}
	
	public Rectangle getScaled(float scaleFactor) {
		scaleFactored = new Rectangle(
				position.x * scaleFactor,
				position.y * scaleFactor,
				dimension.x * scaleFactor,
				dimension.y * scaleFactor);
		return scaleFactored;
	}

	

}
