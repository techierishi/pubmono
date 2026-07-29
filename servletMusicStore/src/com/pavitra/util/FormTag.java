package com.pavitra.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class FormTag extends TagSupport {
	private String method;
	private String action;
	private String id;
	private String css_class;
	private String isToken;

	public void setMethod(String method) {
		this.method = method;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCss_class(String css_class) {
		this.css_class = css_class;
	}


	public void setIsToken(String isToken) {
		this.isToken = isToken;
	}

	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print("<form class=\"" + css_class + "\" method=\"" + method + "\" action=\"" + action + "\" id=\"" + id
					+ "\" >");
			if (null != isToken && !isToken.isEmpty()) {
				if (Boolean.parseBoolean(isToken)) {
					out.print("\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"form_token\" value=\"" + StringUtil.getRandomStr()
							+ "\" >");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print("</form>");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return SKIP_BODY;

	}
}
