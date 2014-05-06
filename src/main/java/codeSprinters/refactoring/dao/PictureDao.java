package codeSprinters.refactoring.dao;

import codeSprinters.refactoring.domain.Picture;
import codeSprinters.refactoring.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureDao {
    private static Map<User, List<Picture>> database = new HashMap<User, List<Picture>>();

    public static List<Picture> getPicturesForUser(User user) {
        return database.get(user);
    }

    public static void addPicturesForUser(User user, List<Picture> pictures) {
        database.put(user, pictures);
    }
}
