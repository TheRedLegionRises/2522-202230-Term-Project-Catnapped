package ca.bcit.comp2522.termproject.catnapped;

public class Constants {

    public static class MenuUI {

        public static class Buttons {
            public static final int BUTTON_WIDTH = 140;

            public static final int BUTTON_HEIGHT= 56;
        }
        public static class PauseButtons {
            public static final int SOUND_BUTTON_SIZE = 42;
        }

        public static class UtilButtons {
            public static final int UTIL_BUTTON_SIZE = 56;
        }

        public static class VolumeButtons {
            public static final int VOLUME_BUTTON_WIDTH = 28;
            public static final int VOLUME_BUTTON_HEIGHT = 44;
            public static final int SLIDER_WIDTH = 215;

        }
    }


    public static class PlayerAttributes {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int TAKE_DAMAGE = 3;
        public static final int ATTACK = 4;
        public static final int DEATH = 5;
        public static final int FALLING = 6;

        public static int GetPlayerAttribute(int player_action) {

            switch (player_action) {
                case RUNNING:
                    return 8;
                case IDLE:
                    return 6;
                case JUMPING:
                    return 12;
                case TAKE_DAMAGE:
                    return 3;
                case DEATH:
                    return 5;
                default:
                    return 1;

            }

        }

    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
}
