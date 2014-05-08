package codeSprinters.refactoring;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import codeSprinters.refactoring.dao.PictureDao;
import codeSprinters.refactoring.domain.Picture;
import codeSprinters.refactoring.domain.User;

public class PictureRetrieverTest {

	private PictureRetriever pictureRetriever = new PictureRetriever();
	private User userWithImages, userLogin, userUnrelated;
	private Picture picture = new Picture();

	@Before
	public void setUp() {
		userWithImages = new User();
		PictureDao.addPicturesForUser(userWithImages, Arrays.asList(picture));
		userLogin = new User();
	}

	@Test(expected = NoLoggedUserException.class)
	public void failureWhenNoUserIsLoggedIn() throws NoLoggedUserException {
		userLogin = null;
		pictureRetriever.getPicturesFor(userWithImages, userLogin);
	}

	@Test
	public void firendShouldHaveAccessToPictures() {

		userLogin.addFriend(userWithImages);
		PictureDao.addPicturesForUser(userWithImages, Arrays.asList(picture));

		List<Picture> retrievedImages = pictureRetriever.getPicturesFor(
				userWithImages, userLogin);

		assertThat(retrievedImages, Matchers.containsInAnyOrder(picture));
	}

	@Test
	public void noAccessToGaleryForUnfirendedUsers() {

		List<Picture> list = pictureRetriever.getPicturesFor(userWithImages,
				userLogin);
		assertThat(list, is(nullValue()));
	}

	@Test
	public void noAccessToGaleryForUnrelatedUsers() {
		userUnrelated = new User();
		userLogin.addFriend(userUnrelated);
		List<Picture> list = pictureRetriever.getPicturesFor(userWithImages,
				userLogin);
		assertThat(list, is(nullValue()));
	}
}
