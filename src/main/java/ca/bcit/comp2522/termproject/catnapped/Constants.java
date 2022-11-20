package ca.bcit.comp2522.termproject.catnapped;

public class Constants {

    public static class PlayerAttributes {

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int TAKE_DAMAGE = 3;
        public static final int ATTACK = 4;
        public static final int DEATH = 5;


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
                case ATTACK:
                    return 8;
                case DEATH:
                    return 5;
                default:
                    return 1;

            }

        }
    }
}
