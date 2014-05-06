package codeSprinters.refactoring.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<User> friends = new ArrayList<User>();

    public List<User> getFriends() {
        return friends;
    }

    public void addFriend(User newFriend) {
        friends.add(newFriend);
    }
}
