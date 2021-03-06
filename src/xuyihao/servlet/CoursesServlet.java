package xuyihao.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import xuyihao.common.ThreadLocalContext;
import xuyihao.entity.CommentCrs;
import xuyihao.entity.Courses;
import xuyihao.entity.LikeCrs;
import xuyihao.service.CoursesService;
import xuyihao.tools.utils.RespondUtils;

/**
 * 
 * @Author Xuyh created at 2016年8月26日 下午1:17:17
 */
@MultipartConfig
public class CoursesServlet extends HttpServlet {

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5851930391567841977L;

	@Autowired
	private CoursesService coursesService;

	private HttpSession session = null;

	public void setCoursesService(CoursesService coursesService) {
		this.coursesService = coursesService;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext context = ThreadLocalContext.setContextAndRet(this.getServletContext());
		this.coursesService = (CoursesService) context.getBean("CoursesService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置会话信息
		this.session = request.getSession();
		this.coursesService.setSessionInfo(session);
		String action = request.getParameter("action");
		if (action == null || action.equals("")) {
			return;
		}
		action = action.trim();
		switch (action) {
		case "addCrs":
			this.addCourse(request, response);
			break;
		case "deleteCrs":
			this.deleteCourse(request, response);
			break;
		case "changeCrsInfo":
			this.changeCourseInformation(request, response);
			break;
		case "getCrsInfo":
			this.getCourseInformation(request, response);
			break;
		case "shareCrs":
			this.shareCourse(request, response);
			break;
		case "commCrs":
			this.addCommentCourse(request, response);
			break;
		case "deleteCommCrs":
			this.deleteCommentCourse(request, response);
			break;
		case "getCommCrsInfo":
			this.getCommentCourseInformation(request, response);
			break;
		case "likeCrs":
			this.addLikeCourse(request, response);
			break;
		case "getLikeCrsInfo":
			this.getLikeCourseInformation(request, response);
			break;
		case "getCrsFileInfo":
			this.getCourseFileInformation(request, response);
			break;
		case "getCrsVedioId":
			this.getCourseVedioId(request, response);
			break;
		case "getCrsPhotoId":
			this.getCoursePhotoId(request, response);
			break;
		case "getVedioById":
			this.getCourseVedioById(request, response);
			break;
		case "getPhotoById":
			this.getCoursePhotoById(request, response);
			break;
		case "getThumbnailPhotoById":
			this.getCourseThumbnailPhotoById(request, response);
			break;
		case "getCachedCoursesList":
			this.getCachedCoursesList(request, response);
			break;
		case "getLatestCoursesList":
			this.getLatestCoursesList(request, response);
			break;
		}
	}

	public void addCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 需要完成的是Crs_route 的设计，因为涉及到上传视频，需要上传之后生成route并保存
		String Acc_ID = session.getAttribute("Acc_ID").toString();
		if (Acc_ID == null || Acc_ID.equals("")) {
			return;
		}
		Courses course = new Courses();
		course.setCrs_name(request.getParameter("Crs_name"));
		course.setAcc_ID(Acc_ID);
		course.setAuthor_ID(Acc_ID);
		String message = this.coursesService.addCourse(course, request);
		RespondUtils.PrintString(response, message);
	}

	public void deleteCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Acc_ID = session.getAttribute("Acc_ID").toString();
		if (Acc_ID == null || Acc_ID.equals("")) {
			return;
		}
		String courseId = request.getParameter("Crs_ID");
		String message = this.coursesService.deleteCourse(courseId);
		RespondUtils.PrintString(response, message);
	}

	public void changeCourseInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Acc_ID = session.getAttribute("Acc_ID").toString();
		if (Acc_ID == null || Acc_ID.equals("")) {
			return;
		}
		Courses course = new Courses();
		// XXX Crs_ID 必需量
		course.setCrs_ID(request.getParameter("Crs_ID"));
		course.setCrs_name(request.getParameter("Crs_name"));
		String message = this.coursesService.changeCourseInformation(course, request);
		RespondUtils.PrintString(response, message);
	}

	public void getCourseInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseId = request.getParameter("Crs_ID");
		String message = this.coursesService.getCourseInformation(courseId);
		RespondUtils.PrintString(response, message);
	}

	public void shareCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Acc_ID = session.getAttribute("Acc_ID").toString();
		if (Acc_ID == null || Acc_ID.equals("")) {
			return;
		}
		String accountId = Acc_ID;
		String courseId = request.getParameter("Crs_ID");
		String message = this.coursesService.shareCourse(accountId, courseId);
		RespondUtils.PrintString(response, message);
	}

	public void addCommentCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Acc_ID = session.getAttribute("Acc_ID").toString();
		if (Acc_ID == null || Acc_ID.equals("")) {
			return;
		}
		CommentCrs commentCrs = new CommentCrs();
		commentCrs.setComm_desc(request.getParameter("Crs_desc"));
		commentCrs.setAcc_ID(Acc_ID);
		commentCrs.setRep_ID(request.getParameter("Rep_ID"));
		commentCrs.setCrs_ID(request.getParameter("Crs_ID"));
		String message = this.coursesService.addCommentCourse(commentCrs);
		RespondUtils.PrintString(response, message);
	}

	public void deleteCommentCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Acc_ID = session.getAttribute("Acc_ID").toString();
		if (Acc_ID == null || Acc_ID.equals("")) {
			return;
		}
		String commentId = request.getParameter("Comm_ID");
		String message = this.coursesService.deleteCommentCourse(commentId);
		RespondUtils.PrintString(response, message);
	}

	public void getCommentCourseInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commentId = request.getParameter("Comm_ID");
		String message = this.coursesService.getCommentCourseInformation(commentId);
		RespondUtils.PrintString(response, message);
	}

	public void addLikeCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Acc_ID = session.getAttribute("Acc_ID").toString();
		if (Acc_ID == null || Acc_ID.equals("")) {
			return;
		}
		// TODO 这里需要将打赏具体实现方式做详细的设计，比如说容易币系统怎么实现(个人、商户)，跟实际的网上支付怎么对接
		LikeCrs likeCrs = new LikeCrs();
		likeCrs.setAcc_ID(Acc_ID);
		likeCrs.setCrs_ID(request.getParameter("Crs_ID"));
		likeCrs.setRep_ID(request.getParameter("Rep_ID"));
		int ryb = 0;
		try {
			ryb = Integer.parseInt(request.getParameter("Like_ryb"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		likeCrs.setLike_ryb(ryb);
		String message = this.coursesService.addLikeCourse(likeCrs);
		RespondUtils.PrintString(response, message);
	}

	public void getLikeCourseInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String likeId = request.getParameter("Like_ID");
		String message = this.coursesService.getLikeCourseInformation(likeId);
		RespondUtils.PrintString(response, message);
	}

	public void getCourseFileInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Crs_ID = request.getParameter("Crs_ID");
		String message = this.coursesService.getCoursesVedioAndPhotoIds(Crs_ID);
		RespondUtils.PrintString(response, message);
	}

	public void getCourseVedioId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Crs_ID = request.getParameter("Crs_ID");
		String message = this.coursesService.getCoursesVedioId(Crs_ID);
		RespondUtils.PrintString(response, message);
	}

	public void getCoursePhotoId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Vedio_ID = request.getParameter("Vedio_ID");
		String message = this.coursesService.getFirstPhotoIdByVedioId(Vedio_ID);
		RespondUtils.PrintString(response, message);
	}

	public void getCourseVedioById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String vedioId = request.getParameter("Vedio_ID");
		String filePathName = this.coursesService.getVedioByVedioId(vedioId);
		RespondUtils.printFile(response, filePathName);
	}

	public void getCoursePhotoById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Photo_ID = request.getParameter("Photo_ID");
		String filePathName = this.coursesService.getPhotoByPhotoId(Photo_ID);
		RespondUtils.printFile(response, filePathName);
	}

	public void getCourseThumbnailPhotoById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Photo_ID = request.getParameter("Photo_ID");
		String filePathName = this.coursesService.getThumbnailPhotoByPhotoId(Photo_ID);
		RespondUtils.printFile(response, filePathName);
	}

	public void getCachedCoursesList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = this.coursesService.getCachedPublishingCourses();
		RespondUtils.PrintString(response, message);
	}

	public void getLatestCoursesList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.valueOf(request.getParameter("page"));
		int size = Integer.valueOf(request.getParameter("size"));
		String message = this.coursesService.getLatestCourses(page, size);
		RespondUtils.PrintString(response, message);
	}
}