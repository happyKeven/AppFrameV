变种Builder模式的自动化生成示例:
public class User {
    private final String mFirstName; // 必选

    private User(Builder builder) {
        mFirstName = builder.mFirstName;
    }

    public static final Builder {
        private String mFirstName;

        public Builder() {}

        public Builder mFirstName(String val) {
            mFirstName = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
