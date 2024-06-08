package utils;

public class Constants {
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class BomberConstants {
		public static final int IDLE = 0;
		public static final int RUN = 1;
		public static final int JUMP = 2;
		public static final int FALL = 3;
		public static final int GROUND = 4;
		public static final int HIT = 5;
		public static final int JUMP_ANTICIPATION = 6;
		public static final int DEAD_HIT = 7;
		public static final int DEAD_GROUND = 8;
		public static final int DOOR_IN = 9;
		public static final int DOOR_OUT = 10;
		public static final int BOMB_OFF = 11;
		public static final int BOMB_ON = 12;
		public static final int EXPLOTION = 13;
		
		public static final String[] ANIMATION_DIR = {
			    "Idle",              // IDLE
			    "Run",               // RUN
			    "Jump",              // JUMP
			    "Fall",              // FALL
			    "Ground",            // GROUND
			    "Hit",               // HIT
			    "Jump Anticipation", // JUMP_ANTICIPATION
			    "Dead Hit",          // DEAD_HIT
			    "Dead Ground",       // DEAD_GROUND
			    "Door In",           // DOOR_IN
			    "Door Out"           // DOOR_OUT
			};

		public static int GetSpriteAmount(int bomber_action) {
			switch (bomber_action) {
			case IDLE:
				return 26;
			case RUN:
				return 14;
			case JUMP:
				return 4;
			case FALL:
				return 2;
			case GROUND:
				return 3;
			case HIT:
				return 8;
			case JUMP_ANTICIPATION:
				return 1;
			case DEAD_HIT:
				return 6;
			case DEAD_GROUND:
				return 4;
			case DOOR_IN:
				return 16;
			case DOOR_OUT:
				return 16;
			case BOMB_OFF:
				return 1;
			case BOMB_ON:
				return 10;
			case EXPLOTION:
				return 9;
			default:
				return 1;
			}
		}
	}
}
