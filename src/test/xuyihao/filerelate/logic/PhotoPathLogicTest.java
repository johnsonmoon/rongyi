package test.xuyihao.filerelate.logic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;
import test.xuyihao.CommonTest;
import xuyihao.filerelate.logic.PhotoPathLogic;
import xuyihao.tools.utils.RandomUtils;

public class PhotoPathLogicTest extends CommonTest {
	@Autowired
	private PhotoPathLogic photoPathLogic;

	public void setPhotoPathLogic(PhotoPathLogic photoPathLogic) {
		this.photoPathLogic = photoPathLogic;
	}

	@Test
	public void test() {
		for(int i = 0; i <10; i ++){
			String id = this.photoPathLogic.savePhotoPath(RandomUtils.getRandomString(15) + "Photo",
					RandomUtils.getRandomString(15) + "Photo");
			Assert.assertEquals(true, this.photoPathLogic.changePhotoPathInfo(id, "PPP", "KKK"));
			System.out.println(this.photoPathLogic.getPhotoPathInfo(id).toJSINString());
			Assert.assertEquals(true, this.photoPathLogic.deletePhotoPath(id));
		}
	}
}